package com.example.proiect1.service;
import com.example.proiect1.Models.Locatie;
import com.example.proiect1.Repo.LocatieRepo;
import com.example.proiect1.dto.InchiriereDTO;
import com.example.proiect1.Models.Inchiriere;
import com.example.proiect1.Models.Masina;
import com.example.proiect1.Models.User;
import com.example.proiect1.Repo.InchiriereRepo;
import com.example.proiect1.Repo.MasinaRepo;
import com.example.proiect1.Repo.UserRepo;
import com.example.proiect1.Models.Log;
import com.example.proiect1.Repo.LogRepo;
import com.example.proiect1.Models.RentalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InchiriereService {

    private final InchiriereRepo inchiriereRepository;
    private final UserRepo userRepository;
    private final MasinaRepo masinaRepository;
    private final LocatieRepo locatieRepository;
    private final LogRepo logRepo;
    private final JavaMailSender mailSender;

    public List<InchiriereDTO> getByUserId(Long userId) {
        return inchiriereRepository.findAllByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private InchiriereDTO convertToDTO(Inchiriere inchiriere) {
        return InchiriereDTO.builder()
                .id(inchiriere.getId())
                .userId(inchiriere.getUser().getId())
                .masinaId(inchiriere.getMasina().getId())
                .locatieId(inchiriere.getLocatie().getId())
                .dataInceput(inchiriere.getDataInceput())
                .dataSfarsit(inchiriere.getDataSfarsit())
                .code(inchiriere.getCode())
                .status(inchiriere.getStatus())
                .build();
    }

    public boolean deleteInchiriereById(Long id) {
        return inchiriereRepository.findById(id).map(inchiriere -> {
            Masina masina = inchiriere.getMasina();
            User user = inchiriere.getUser();
            
            masina.setCantitate(masina.getCantitate() + 1);
            if (masina.getCantitate() > 0) {
                masina.setDisponibil(true);
            }
            masinaRepository.save(masina);
            
            logRepo.save(Log.builder()
                    .masinaId(masina.getId())
                    .userId(user.getId())
                    .action("DELETED")
                    .timestamp(LocalDateTime.now())
                    .carInfo(masina.getMarca() + " " + masina.getModel())
                    .userInfo(user.getName() + " " + user.getLastName())
                    .build());

            inchiriereRepository.deleteById(id);
            return true;
        }).orElse(false);
    }



    public InchiriereDTO create(InchiriereDTO dto) {
        User user = userRepository.findById(dto.userId()).orElseThrow();
        Masina masina = masinaRepository.findById(dto.masinaId()).orElseThrow();
        Locatie locatie = locatieRepository.findById(dto.locatieId()).orElseThrow();

        if (!masina.isDisponibil() || masina.getCantitate() <= 0) {
            throw new RuntimeException("Masina nu este disponibila pentru inchiriere.");
        }

        masina.setCantitate(masina.getCantitate() - 1);
        if (masina.getCantitate() == 0) {
            masina.setDisponibil(false);
        }
        masinaRepository.save(masina);

        String rentalCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Inchiriere inchiriere = Inchiriere.builder()
                .user(user)
                .masina(masina)
                .locatie(locatie)
                .dataInceput(dto.dataInceput())
                .dataSfarsit(dto.dataSfarsit())
                .code(rentalCode)
                .status(RentalStatus.PENDING)
                .build();

        inchiriere = inchiriereRepository.save(inchiriere);

        sendRentalCodeEmail(user.getEmail(), rentalCode);

        logRepo.save(Log.builder()
                .masinaId(masina.getId())
                .userId(user.getId())
                .action("RENTED")
                .timestamp(LocalDateTime.now())
                .carInfo(masina.getMarca() + " " + masina.getModel())
                .userInfo(user.getName() + " " + user.getLastName())
                .build());

        return convertToDTO(inchiriere);
    }

    private void sendRentalCodeEmail(String to, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Your Rental Code");
            message.setText("Thank you for your rental! Your unique code is: " + code + 
                            "\nPlease provide this code to the administrator when picking up the car.");
            mailSender.send(message);
        } catch (Exception e) {
            // Log the error but don't fail the rental process
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    public InchiriereDTO updateStatusByCode(String code, RentalStatus newStatus) {
        Inchiriere inchiriere = inchiriereRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Inchiriere not found for code: " + code));
        
        inchiriere.setStatus(newStatus);

        // If returned or canceled, we might want to release the car, but the logic in delete already does some of this.
        // For now, let's just update the status.
        if (newStatus == RentalStatus.RETURNED || newStatus == RentalStatus.CANCELED) {
            Masina masina = inchiriere.getMasina();
            masina.setCantitate(masina.getCantitate() + 1);
            if (masina.getCantitate() > 0) {
                masina.setDisponibil(true);
            }
            masinaRepository.save(masina);
        }

        inchiriere = inchiriereRepository.save(inchiriere);
        return convertToDTO(inchiriere);
    }


    public List<InchiriereDTO> findAll() {
        return inchiriereRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}


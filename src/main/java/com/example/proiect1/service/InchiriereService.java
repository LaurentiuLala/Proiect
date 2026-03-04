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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InchiriereService {

    private final InchiriereRepo inchiriereRepository;
    private final UserRepo userRepository;
    private final MasinaRepo masinaRepository;
    private final LocatieRepo locatieRepository;
    private final LogRepo logRepo;

    public List<InchiriereDTO> getByUserId(Long userId) {
        return inchiriereRepository.findAllByUserId(userId).stream()
                .map(inchiriere -> InchiriereDTO.builder()
                        .id(inchiriere.getId())
                        .userId(inchiriere.getUser().getId())
                        .masinaId(inchiriere.getMasina().getId())
                        .locatieId(inchiriere.getLocatie().getId())
                        .dataInceput(inchiriere.getDataInceput())
                        .dataSfarsit(inchiriere.getDataSfarsit())
                        .build())
                .collect(Collectors.toList());
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

        Inchiriere inchiriere = Inchiriere.builder()
                .user(user)
                .masina(masina)
                .locatie(locatie)
                .dataInceput(dto.dataInceput())
                .dataSfarsit(dto.dataSfarsit())
                .build();

        inchiriere = inchiriereRepository.save(inchiriere);

        logRepo.save(Log.builder()
                .masinaId(masina.getId())
                .userId(user.getId())
                .action("RENTED")
                .timestamp(LocalDateTime.now())
                .carInfo(masina.getMarca() + " " + masina.getModel())
                .userInfo(user.getName() + " " + user.getLastName())
                .build());

        return InchiriereDTO.builder()
                .id(inchiriere.getId())
                .userId(inchiriere.getUser().getId())
                .masinaId(inchiriere.getMasina().getId())
                .locatieId(inchiriere.getLocatie().getId())
                .dataInceput(inchiriere.getDataInceput())
                .dataSfarsit(inchiriere.getDataSfarsit())
                .build();
    }


    public List<InchiriereDTO> findAll() {
        return inchiriereRepository.findAll().stream()
                .map(inchiriere -> InchiriereDTO.builder()
                        .id(inchiriere.getId())
                        .userId(inchiriere.getUser().getId())
                        .masinaId(inchiriere.getMasina().getId())
                        .locatieId(inchiriere.getLocatie().getId())
                        .dataInceput(inchiriere.getDataInceput())
                        .dataSfarsit(inchiriere.getDataSfarsit())
                        .build())
                .collect(Collectors.toList());
    }

}


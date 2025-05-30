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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InchiriereService {

    private final InchiriereRepo inchiriereRepository;
    private final UserRepo userRepository;
    private final MasinaRepo masinaRepository;
    private final LocatieRepo locatieRepository;

    public List<InchiriereDTO> getByUserId(Long userId) {
        return inchiriereRepository.findAllByUserId(userId).stream().map(inchiriere -> {
            InchiriereDTO dto = new InchiriereDTO();
            dto.setId(inchiriere.getId());
            dto.setUserId(inchiriere.getUser().getId());
            dto.setMasinaId(inchiriere.getMasina().getId());
            dto.setLocatieId(inchiriere.getLocatie().getId()); // dacă e nevoie
            dto.setDataInceput(inchiriere.getDataInceput());
            dto.setDataSfarsit(inchiriere.getDataSfarsit());
            return dto;
        }).collect(Collectors.toList());
    }

    public boolean deleteInchiriereById(Long id) {
        if (inchiriereRepository.existsById(id)) {
            inchiriereRepository.deleteById(id);
            return true;
        }
        return false;
    }



    public InchiriereDTO create(InchiriereDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Masina masina = masinaRepository.findById(dto.getMasinaId()).orElseThrow();
        Locatie locatie = locatieRepository.findById(dto.getLocatieId()).orElseThrow();

        Inchiriere inchiriere = Inchiriere.builder()
                .user(user)
                .masina(masina)
                .locatie(locatie)
                .dataInceput(dto.getDataInceput())
                .dataSfarsit(dto.getDataSfarsit())
                .build();

        inchiriere = inchiriereRepository.save(inchiriere);
        dto.setId(inchiriere.getId());
        return dto;
    }


    public List<InchiriereDTO> findAll() {
        return inchiriereRepository.findAll().stream().map(inchiriere -> {
            InchiriereDTO dto = new InchiriereDTO();
            dto.setId(inchiriere.getId());
            dto.setUserId(inchiriere.getUser().getId());
            dto.setMasinaId(inchiriere.getMasina().getId());
            dto.setDataInceput(inchiriere.getDataInceput());
            dto.setDataSfarsit(inchiriere.getDataSfarsit());
            return dto;
        }).collect(Collectors.toList());
    }

}


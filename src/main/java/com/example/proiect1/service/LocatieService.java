package com.example.proiect1.service;

import com.example.proiect1.dto.LocatieDTO;
import com.example.proiect1.Models.Locatie;
    import com.example.proiect1.Repo.LocatieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocatieService {

    private final LocatieRepo locatieRepository;

    public LocatieDTO create(LocatieDTO dto) {
        Locatie locatie = Locatie.builder()
                .oras(dto.getOras())
                .strada(dto.getStrada())
                .numar(dto.getNumar())
                .build();
        locatie = locatieRepository.save(locatie);
        dto.setId(locatie.getId());
        return dto;
    }

    public List<LocatieDTO> findAll() {
        return locatieRepository.findAll().stream().map(locatie -> {
            LocatieDTO dto = new LocatieDTO();
            dto.setId(locatie.getId());
            dto.setOras(locatie.getOras());
            dto.setStrada(locatie.getStrada());
            dto.setNumar(locatie.getNumar());
            return dto;
        }).collect(Collectors.toList());
    }
}


package com.example.proiect1.service;

import com.example.proiect1.dto.MasinaDTO;
import com.example.proiect1.Models.Masina;
import com.example.proiect1.Repo.MasinaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MasinaService {

    private final MasinaRepo masinaRepository;

    public MasinaDTO create(MasinaDTO dto) {
        Masina masina = Masina.builder()
                .marca(dto.getMarca())
                .model(dto.getModel())
                .anFabricatie(dto.getAnFabricatie())
                .pretPeZi(dto.getPretPeZi())
                .disponibil(dto.isDisponibil())
                .build();

        return toDto(masinaRepository.save(masina));
    }

    public List<MasinaDTO> findAll() {
        return masinaRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MasinaDTO toDto(Masina m) {
        return MasinaDTO.builder()
                .id(m.getId())
                .marca(m.getMarca())
                .model(m.getModel())
                .anFabricatie(m.getAnFabricatie())
                .pretPeZi(m.getPretPeZi())
                .disponibil(m.isDisponibil())
                .build();
    }
}

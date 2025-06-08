package com.example.proiect1.service;
import com.example.proiect1.Models.Locatie;
import com.example.proiect1.Repo.LocatieRepo;
import com.example.proiect1.exception.CarNotFound;
import com.example.proiect1.dto.MasinaDTO;
import com.example.proiect1.Models.Masina;
import com.example.proiect1.Repo.MasinaRepo;
import com.example.proiect1.exception.LocationNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MasinaService {

    private final MasinaRepo masinaRepository;
    private final LocatieRepo locatieRepository;

    public MasinaDTO create(MasinaDTO dto) {
        Locatie locatie = locatieRepository.findById(dto.locatieId())
                .orElseThrow(() -> new LocationNotFound("Locatie not found with id: " + dto.locatieId()));

        Masina masina = Masina.builder()
                .marca(dto.marca())
                .model(dto.model())
                .anFabricatie(dto.anFabricatie())
                .pretPeZi(dto.pretPeZi())
                .disponibil(true)
                .locatie(locatie)
                .build();

        return convertToDto(masinaRepository.save(masina));
    }

    public List<MasinaDTO> findAll() {
        return masinaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MasinaDTO findById(Long id) {
        Masina masina = masinaRepository.findById(id)
                .orElseThrow(() -> new CarNotFound("Masina not found with id: " + id));
        return convertToDto(masina);
    }

    public List<MasinaDTO> findByLocatieId(Long locatieId) {
        List<Masina> masini = masinaRepository.findByLocatieId(locatieId);
        return masini.stream().map(this::convertToDto).toList();
    }

    private MasinaDTO convertToDto(Masina m) {
        String descriereLocatie = "";
        Long locatieId = null;

        if (m.getLocatie() != null) {
            locatieId = m.getLocatie().getId();
            descriereLocatie = m.getLocatie().getOras() + ", " + m.getLocatie().getStrada() + " nr. " + m.getLocatie().getNumar();
        }

        return MasinaDTO.builder()
                .id(m.getId())
                .marca(m.getMarca())
                .model(m.getModel())
                .anFabricatie(m.getAnFabricatie())
                .pretPeZi(m.getPretPeZi())
                .disponibil(m.isDisponibil())
                .locatieId(locatieId)
                .locatieDescriere(descriereLocatie)
                .build();
    }

    public void deleteMasina(Long id) {
        if (!masinaRepository.existsById(id)) {
            throw new RuntimeException("Mașina nu a fost găsită");
        }
        masinaRepository.deleteById(id);
    }

}


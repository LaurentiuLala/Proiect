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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MasinaService {

    private final MasinaRepo masinaRepository;
    private final LocatieRepo locatieRepository;
    private final String uploadDir = "uploads/cars/";

    public MasinaDTO create(MasinaDTO dto) {
        Locatie locatie = locatieRepository.findById(dto.locatieId())
                .orElseThrow(() -> new LocationNotFound("Locatie not found with id: " + dto.locatieId()));

        Masina masina = Masina.builder()
                .marca(dto.marca())
                .model(dto.model())
                .anFabricatie(dto.anFabricatie())
                .pretPeZi(dto.pretPeZi())
                .cantitate(dto.cantitate())
                .disponibil(dto.cantitate() > 0)
                .locatie(locatie)
                .images(new ArrayList<>())
                .build();

        return convertToDto(masinaRepository.save(masina));
    }

    public void uploadImages(Long masinaId, List<MultipartFile> files) throws IOException {
        Masina masina = masinaRepository.findById(masinaId)
                .orElseThrow(() -> new CarNotFound("Masina not found with id: " + masinaId));

        Path carUploadPath = Paths.get(uploadDir + masinaId);
        if (!Files.exists(carUploadPath)) {
            Files.createDirectories(carUploadPath);
        }

        List<String> imagePaths = masina.getImages();
        if (imagePaths == null) imagePaths = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = carUploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            imagePaths.add("/api/images/cars/" + masinaId + "/" + fileName);
        }

        masina.setImages(imagePaths);
        masinaRepository.save(masina);
    }

    public void deleteAllImages(Long masinaId) throws IOException {
        Masina masina = masinaRepository.findById(masinaId)
                .orElseThrow(() -> new CarNotFound("Masina not found with id: " + masinaId));
        
        Path carUploadPath = Paths.get(uploadDir + masinaId);
        if (Files.exists(carUploadPath)) {
            Files.walk(carUploadPath)
                .sorted((a, b) -> b.compareTo(a)) // Delete files before directory
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }
        
        masina.getImages().clear();
        masinaRepository.save(masina);
    }

    public MasinaDTO updateMasina(Long id, MasinaDTO dto) {
        Masina masina = masinaRepository.findById(id)
                .orElseThrow(() -> new CarNotFound("Masina not found with id: " + id));

        Locatie locatie = locatieRepository.findById(dto.locatieId())
                .orElseThrow(() -> new LocationNotFound("Locatie not found with id: " + dto.locatieId()));

        masina.setMarca(dto.marca());
        masina.setModel(dto.model());
        masina.setAnFabricatie(dto.anFabricatie());
        masina.setPretPeZi(dto.pretPeZi());
        masina.setCantitate(dto.cantitate());
        masina.setDisponibil(dto.cantitate() > 0);
        masina.setLocatie(locatie);

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
                .cantitate(m.getCantitate())
                .disponibil(m.isDisponibil())
                .locatieId(locatieId)
                .locatieDescriere(descriereLocatie)
                .images(m.getImages())
                .build();
    }

    public void deleteMasina(Long id) {
        if (!masinaRepository.existsById(id)) {
            throw new RuntimeException("Mașina nu a fost găsită");
        }
        masinaRepository.deleteById(id);
    }

}


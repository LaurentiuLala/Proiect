package com.example.proiect1.Repo;
import com.example.proiect1.Models.Masina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasinaRepo extends JpaRepository<Masina, Long> {
    List<Masina> findByLocatieId(Long locatieId);
}

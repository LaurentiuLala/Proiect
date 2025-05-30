package com.example.proiect1.Repo;

import com.example.proiect1.Models.Inchiriere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InchiriereRepo extends JpaRepository<Inchiriere, Long> {
    List<Inchiriere> findAllByUserId(Long userId);

}

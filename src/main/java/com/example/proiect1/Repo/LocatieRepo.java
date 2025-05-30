package com.example.proiect1.Repo;

import com.example.proiect1.Models.Locatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocatieRepo extends JpaRepository<Locatie, Long> {
}


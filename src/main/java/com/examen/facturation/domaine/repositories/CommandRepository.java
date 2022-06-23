package com.examen.facturation.domaine.repositories;

import com.examen.facturation.domaine.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CommandRepository extends JpaRepository<Command, Integer> {
    Optional<Command> findByClientIdAndAndDate(Integer clientId, LocalDate date);
}

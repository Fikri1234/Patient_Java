package com.example.patient.repository;

import com.example.patient.model.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 13:06 03/11/2024, 2024
 */

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(String firstName, String lastName);

    @Query(value = "SELECT p FROM Patient p WHERE (?1 IS NULL OR ?1 = p.id) " +
            "AND (?2 = '' OR ?2 IS NULL OR lower(p.firstname) LIKE CONCAT('%',lower(?2),'%')) " +
            "AND (?3 = '' OR ?3 IS NULL OR lower(p.lastname) LIKE CONCAT('%',lower(?3),'%'))")
    Page<Patient> findByIdOrFirstnameOrLastname(Long id, String firstName, String lastName, Pageable pageable);
}

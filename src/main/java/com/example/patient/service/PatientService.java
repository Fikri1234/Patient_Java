package com.example.patient.service;

import com.example.patient.model.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by user on 13:02 03/11/2024, 2024
 */

public interface PatientService {
    Optional<Patient> findById(Long id);
    Patient save(Patient patient);
    Patient update(Patient patient);
    List<Patient> findAll();
    void deleteById(Long id);
    void deleteAll();
    Page<Patient> findByIdOrFirstNameContainingIgnoreCaseOrLastnameContainginIgnoreCase(Long id, String firstName, String lastName, Pageable pageable);
}

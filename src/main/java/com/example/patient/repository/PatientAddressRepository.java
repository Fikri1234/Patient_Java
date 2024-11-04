package com.example.patient.repository;

import com.example.patient.model.entity.PatientAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by user on 13:07 03/11/2024, 2024
 */

@Repository
public interface PatientAddressRepository extends JpaRepository<PatientAddress, Long> {
}

package com.example.patient.service.impl;

import com.example.patient.model.entity.Patient;
import com.example.patient.model.entity.PatientAddress;
import com.example.patient.repository.PatientAddressRepository;
import com.example.patient.repository.PatientRepository;
import com.example.patient.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by user on 13:07 03/11/2024, 2024
 */

@Service
@Slf4j
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    private PatientAddressRepository patientAddressRepository;

    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient save(Patient patient) {
        log.info("Saving patient");
        if (patient.getPatientAddress() != null) {
            PatientAddress patientAddress = patientAddressRepository.save(patient.getPatientAddress());
            patient.setPatientAddress(patientAddress);
        }
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        log.info("Update patient");
        Optional<Patient> optional = patientRepository.findById(patient.getId());
        if (optional.isPresent()) {
            Patient existingPatient = optional.get();
            existingPatient.setFirstname(patient.getFirstname());
            existingPatient.setLastname(patient.getLastname());
            existingPatient.setDateOfBirth(patient.getDateOfBirth());
            existingPatient.setGender(patient.getGender());
            existingPatient.setPhone(patient.getPhone());
            if (patient.getPatientAddress() != null) {
                existingPatient.setPatientAddress(patientAddressRepository.save(patient.getPatientAddress()));
            }
            return patientRepository.save(existingPatient);
        }
        return null;
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Patient> optional = patientRepository.findById(id);
        if (optional.isPresent()) {
            PatientAddress patientAddress = optional.get().getPatientAddress();
            if (patientAddress!= null) {
                patientAddressRepository.deleteById(patientAddress.getId());
            }
        }
        patientRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        patientRepository.deleteAll();
    }

    @Override
    public Page<Patient> findByIdOrFirstNameContainingIgnoreCaseOrLastnameContainginIgnoreCase(Long id, String firstName, String lastName, Pageable pageable) {
        return patientRepository.findByIdOrFirstnameOrLastname(id, firstName, lastName, pageable);
    }
}

package com.example.patient.controller;

import com.example.patient.model.dto.PatientDTO;
import com.example.patient.model.dto.ResponseApisDTO;
import com.example.patient.model.entity.Patient;
import com.example.patient.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by user on 13:09 03/11/2024, 2024
 */

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
@Slf4j
public class PatientController extends BaseController {

    private PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApisDTO> getPatientById(@PathVariable Long id) {
        log.info("Get patient by id: {}", id);
        PatientDTO dto = new PatientDTO();
        Optional<Patient> optional = patientService.findById(id);
        if (optional.isPresent()) {
            dto = new PatientDTO(optional.get());
            return new ResponseEntity<>(responseApi(dto), HttpStatus.OK);
        }
        return new ResponseEntity<>(responseApi(dto), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseApisDTO> getPatientAll() {
        log.info("Get patient All");
        List<Patient> patients = patientService.findAll();
        return new ResponseEntity<>(responseApi(patients), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseApisDTO> save(@RequestBody PatientDTO patientDTO) {
        log.info("Save patient: {}", patientDTO);
        Patient patient = new Patient(patientDTO);
        patient = patientService.save(patient);
        log.info("Successfully save patient: {}", patient);
        return new ResponseEntity<>(responseApi(patient), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<ResponseApisDTO> update(@RequestBody PatientDTO patientDTO) {
        log.info("Update patient: {}", patientDTO);
        Patient patient = new Patient(patientDTO);
        patient = patientService.update(patient);
        log.info("Successfully update patient: {}", patient);
        return new ResponseEntity<>(responseApi(patient), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApisDTO> deleteById(@PathVariable Long id) {
        log.info("Delete patient by id: {}", id);
        patientService.deleteById(id);
        return new ResponseEntity<>(responseApi(true), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseApisDTO> deleteAll() {
        log.info("Delete all patients");
        patientService.deleteAll();
        return new ResponseEntity<>(responseApi(true), HttpStatus.OK);
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<Object> findPagination(PatientDTO dto, @PathVariable int page, @PathVariable int size) {
        log.info("Find patient by page: {}, size: {}", page, size);
        List<PatientDTO> dtoList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<Patient> patients = patientService.findByIdOrFirstNameContainingIgnoreCaseOrLastnameContainginIgnoreCase(dto.getId(), dto.getFirstname(), dto.getLastname(), pageable);
        if (patients.hasContent()) {
            Page<PatientDTO> dtos = patients.map(PatientDTO::new);
            return new ResponseEntity<>(responseApiPaged(dtos), HttpStatus.OK);
        }
        return new ResponseEntity<>(responseApi(dtoList), HttpStatus.OK);
    }
}

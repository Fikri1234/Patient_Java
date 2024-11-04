package com.example.patient;

import com.example.patient.model.entity.Patient;
import com.example.patient.model.enumeration.Gender;
import com.example.patient.repository.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

/**
 * Created by user on 13:10 03/11/2024, 2024
 */

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;


    @Test
    @DisplayName("Test 1:Save Patient Test")
    @Order(1)
    @Rollback(value = false)
    void savePatientTest() {

        //Action
        Patient patient = new Patient(1L, "John", "Cena", new GregorianCalendar(1990, Calendar.FEBRUARY, 11).getTime(), Gender.MALE, "555-555-5555");

        patientRepository.save(patient);

        //Verify
        System.out.println(patient);
        Assertions.assertThat(patient.getId()).isPositive();
    }

    @Test
    @Order(2)
    void getPatientTest() {

        Patient patientSave = new Patient(1L, "John", "Cena", new GregorianCalendar(1990, Calendar.FEBRUARY, 11).getTime(), Gender.MALE, "555-555-5555");

        patientRepository.save(patientSave);

        //Action
        Patient patient = patientRepository.findById(1L).get();
        //Verify
        System.out.println(patient);
        Assertions.assertThat(patient.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    void getListOfPatientsTest() {
        Patient patientSave1 = new Patient(1L, "John", "Cena", new GregorianCalendar(1990, Calendar.FEBRUARY, 11).getTime(), Gender.MALE, "555-555-5555");

        Patient patientSave2 = new Patient(2L, "Harry", "Asmass", new GregorianCalendar(1990, Calendar.FEBRUARY, 11).getTime(), Gender.MALE, "555-555-5555");

        List<Patient> patientList = Arrays.asList(patientSave1, patientSave2);
        patientRepository.saveAll(patientList);

        //Action
        List<Patient> patients = patientRepository.findAll();
        //Verify
        System.out.println(patients);
        Assertions.assertThat(patients.size()).isPositive();

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    void updatePatientTest() {

        Patient patientSave = new Patient(1L, "John", "Cena", new GregorianCalendar(1990, Calendar.FEBRUARY, 11).getTime(), Gender.MALE, "555-555-5555");

        patientRepository.save(patientSave);

        //Action
        Patient patient = patientRepository.findById(1L).get();
        patient.setFirstname("Second john");
        Patient patientUpdated = patientRepository.save(patient);

        //Verify
        System.out.println(patientUpdated);
        Assertions.assertThat(patientUpdated.getFirstname()).isEqualTo("Second john");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    void deletePatientTest() {
        //Action
        patientRepository.deleteById(1L);
        Optional<Patient> patientOptional = patientRepository.findById(1L);

        //Verify
        Assertions.assertThat(patientOptional).isEmpty();
    }

}

package com.example.patient;

import com.example.patient.model.dto.PatientAddressDTO;
import com.example.patient.model.dto.PatientDTO;
import com.example.patient.model.entity.Patient;
import com.example.patient.model.enumeration.Gender;
import com.example.patient.repository.PatientAddressRepository;
import com.example.patient.repository.PatientRepository;
import com.example.patient.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by user on 13:10 03/11/2024, 2024
 */

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientServiceUnitTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientAddressRepository patientAddressRepository;

    @InjectMocks
    private PatientServiceImpl patientserviceImpl;

    private PatientDTO patientDTO;

    private PatientAddressDTO patientAddressDTO;

    @BeforeEach
    public void setup() {

        patientAddressDTO = new PatientAddressDTO("Bogor", "Western", "", "412412");

        patientDTO = new PatientDTO(11L, "John", "Cena", new GregorianCalendar(1990, Calendar.FEBRUARY, 11).getTime(), Gender.MALE, patientAddressDTO, "555-555-5555");
    }

    @Test
    @DisplayName("Test post creation")
    @Order(1)
    void savePatientTest() {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);

        // precondition
        given(patientRepository.save(any(Patient.class))).willReturn(patient);

        //action
        Patient savedPatient = patientserviceImpl.save(patient);

        // verify the output
        System.out.println(savedPatient);
        assertThat(savedPatient).isNotNull();
    }

    @Test
    @DisplayName("Test get by ID")
    @Order(2)
    void getPatientById() {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);

        // precondition
        given(patientRepository.findById(patient.getId())).willReturn(Optional.of(patient));

        // action
        Patient existingPatient = patientserviceImpl.findById(patient.getId()).get();

        // verify
        System.out.println(existingPatient);
        assertThat(existingPatient).isNotNull();

    }

    @Test
    @DisplayName("Test get all data")
    @Order(3)
    void getAllPatient() {

        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);

        Patient patient1 = new Patient();
        patientDTO.setId(12L);
        patientDTO.setFirstname("luke");
        BeanUtils.copyProperties(patientDTO, patient1);

        patientserviceImpl.save(patient1);

        // precondition
        given(patientRepository.findAll()).willReturn(List.of(patient, patient1));

        // action
        List<Patient> PatientList = patientserviceImpl.findAll();

        // verify
        System.out.println(PatientList);
        assertThat(PatientList).isNotNull();
        assertThat(PatientList.size()).isGreaterThan(1);
    }

    @Test
    @DisplayName("Test put data")
    @Order(4)
    void updatePatient() {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        // precondition
        patient.setFirstname("Max");
        patient.setLastname("Elber");
        given(patientRepository.save(any(Patient.class))).willReturn(patient);
        given(patientRepository.findById(patient.getId())).willReturn(Optional.of(patient));

        // action
        Patient updatedPatient = patientserviceImpl.update(patient);

        // verify
        System.out.println(updatedPatient);
        assertThat(updatedPatient.getFirstname()).isEqualTo("Max");
        assertThat(updatedPatient.getLastname()).isEqualTo("Elber");
    }

    @Test
    @DisplayName("Test delete by ID")
    @Order(5)
    void deletePatient() {

        // precondition
        willDoNothing().given(patientRepository).deleteById(patientDTO.getId());

        // action
        patientserviceImpl.deleteById(patientDTO.getId());

        // verify
        verify(patientRepository, times(1)).deleteById(patientDTO.getId());
    }
}

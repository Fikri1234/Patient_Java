package com.example.patient;

import com.example.patient.controller.PatientController;
import com.example.patient.model.dto.PatientAddressDTO;
import com.example.patient.model.dto.PatientDTO;
import com.example.patient.model.dto.ResponseApisDTO;
import com.example.patient.model.entity.Patient;
import com.example.patient.model.entity.PatientAddress;
import com.example.patient.model.enumeration.Gender;
import com.example.patient.repository.PatientRepository;
import com.example.patient.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by user on 13:10 03/11/2024, 2024
 */
@WebMvcTest(PatientController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PatientRepository patientRepository;

    @MockBean
    PatientService patientService;
    @Autowired
    private ObjectMapper objectMapper;
    private PatientDTO patientDTO;

    private Patient patient;

    private PatientAddressDTO patientAddressDTO;

    private PatientAddress patientAddress;

    @BeforeEach
    public void setup() {

        patientAddressDTO = new PatientAddressDTO("Bogor", "Western", "", "412412");

        patientDTO = new PatientDTO(1L, "John", "Cena", new GregorianCalendar(1990, Calendar.FEBRUARY, 11).getTime(), Gender.MALE, patientAddressDTO, "555-555-5555");

        patientAddress = new PatientAddress("Bogor", "Western", "West Java", "412412");

        patient = new Patient(1L, "John", "Cena", new GregorianCalendar(1990, Calendar.FEBRUARY, 11).getTime(), Gender.MALE, patientAddress, "555-555-5555");
    }

    //Post Controller
    @Test
    @Order(1)
    void savePatientTest() throws Exception {
        // precondition
        given(patientService.save(any(Patient.class))).willReturn(patient);

        // action
        ResultActions response = mockMvc.perform(post("/patients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientDTO)));

        // verify
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.firstname",
                        is(patientDTO.getFirstname())))
                .andExpect(jsonPath("$.data.lastname",
                        is(patientDTO.getLastname())))
                .andExpect(jsonPath("$.data.phone",
                        is(patientDTO.getPhone())));
    }

    //Get Controller
    @Test
    @Order(2)
    void getPatientTest() throws Exception {
        // precondition
        List<Patient> patientList = new ArrayList<>();
        Patient patient1 = new Patient();
        BeanUtils.copyProperties(patientDTO, patient1);
        patient1.setId(12L);
        patient1.setFirstname("Sally");
        patient1.setLastname("Clay");
        patientList.add(patient);
        patientList.add(patient1);
        given(patientService.findAll()).willReturn(patientList);

        // action
        MvcResult result = mockMvc.perform(get("/patients/")).andExpect(status().isOk()).andReturn();

        ResponseApisDTO actual = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseApisDTO.class);

        List<PatientDTO> patientDTOs = (List<PatientDTO>) actual.getData();
        // verify the output
        Assertions.assertThat(patientList.size()).isEqualTo(patientDTOs.size());
        log.info("PatientDTOs: " + patientDTOs);

    }

    //Update Patient
    @Test
    @Order(4)
    void updatePatientTest() throws Exception {
        // precondition
        patient.setFirstname("Max");
        patient.setLastname("Elber");
        given(patientService.update(any(Patient.class))).willReturn(patient);

        patientDTO.setFirstname("Max");
        patientDTO.setLastname("Gregorie");

        // action
        ResultActions response = mockMvc.perform(put("/patients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientDTO)));

        // verify
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstname",
                        is(patient.getFirstname())))
                .andExpect(jsonPath("$.data.lastname",
                        is(patient.getLastname())))
                .andExpect(jsonPath("$.data.phone",
                        is(patient.getPhone())));
    }


    // delete Patient
    @Test
    void deletePatientTest() throws Exception {
        // precondition
        willDoNothing().given(patientService).deleteById(patient.getId());

        // action
        ResultActions response = mockMvc.perform(delete("/patients/{id}", patient.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}

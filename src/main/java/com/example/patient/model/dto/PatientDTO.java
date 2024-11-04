package com.example.patient.model.dto;

import com.example.patient.model.entity.Patient;
import com.example.patient.model.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * Created by user on 13:00 03/11/2024, 2024
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientDTO {

    Long id;
    String firstname;
    String lastname;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    Date dateOfBirth;
    Gender gender;
    PatientAddressDTO patientAddressDTO;
    String phone;

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.firstname = patient.getFirstname();
        this.lastname = patient.getLastname();
        this.dateOfBirth = patient.getDateOfBirth();
        this.gender = patient.getGender();
        this.phone = patient.getPhone();
        this.patientAddressDTO = new PatientAddressDTO(patient.getPatientAddress());
    }
}

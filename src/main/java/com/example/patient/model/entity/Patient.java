package com.example.patient.model.entity;

import com.example.patient.model.dto.PatientDTO;
import com.example.patient.model.enumeration.Gender;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 12:58 03/11/2024, 2024
 */

@Data
@ToString
@Entity
@Table(name = "patient")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements Serializable {

    @Serial
    private static final long serialVersionUID = -5929218001061827592L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    Long id;

    String firstname;
    String lastname;
    Date dateOfBirth;

    @Basic
    @Enumerated(EnumType.STRING)
//    @Column(name = "gender", nullable = false,
//            columnDefinition = "ENUM('MALE','FEMALE') NOT NULL")
    Gender gender;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "patient_address_id")
    PatientAddress patientAddress;
    String phone;

    public Patient(Long id, String firstname, String lastname, Date dateOfBirth, Gender gender, String phone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
    }

    public Patient(PatientDTO patientDTO) {
        this.id = patientDTO.getId();
        this.firstname = patientDTO.getFirstname();
        this.lastname = patientDTO.getLastname();
        this.dateOfBirth = patientDTO.getDateOfBirth();
        this.gender = patientDTO.getGender();
        this.phone = patientDTO.getPhone();
        if (patientDTO.getPatientAddressDTO() != null) {
            this.patientAddress = new PatientAddress(patientDTO.getPatientAddressDTO());
        }
    }
}
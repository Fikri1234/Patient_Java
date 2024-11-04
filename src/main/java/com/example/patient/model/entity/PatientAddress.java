package com.example.patient.model.entity;

import com.example.patient.model.dto.PatientAddressDTO;
import com.example.patient.model.dto.PatientDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

/**
 * Created by user on 12:58 03/11/2024, 2024
 */

@Data
@ToString
@Entity
@Table(name="patient_address")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PatientAddress implements Serializable {

    @Serial
    private static final long serialVersionUID = -6845009346975416237L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    String address;
    String suburb;
    String state;
    String postcode;

    public PatientAddress(String address, String suburb, String state, String postcode) {
        this.address = address;
        this.suburb = suburb;
        this.state = state;
        this.postcode = postcode;
    }

    public PatientAddress(PatientAddressDTO patientAddressDTO) {
        this.id = patientAddressDTO.getId();
        this.address = patientAddressDTO.getAddress();
        this.suburb = patientAddressDTO.getSuburb();
        this.state = patientAddressDTO.getState();
        this.postcode = patientAddressDTO.getPostcode();
    }
}

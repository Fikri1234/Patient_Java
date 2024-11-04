package com.example.patient.model.dto;

import com.example.patient.model.entity.PatientAddress;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Created by user on 13:00 03/11/2024, 2024
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientAddressDTO {

    Long id;
    String address;
    String suburb;
    String state;
    String postcode;

    public PatientAddressDTO(PatientAddress patientAddress) {
        this.id = patientAddress.getId();
        this.address = patientAddress.getAddress();
        this.suburb = patientAddress.getSuburb();
        this.state = patientAddress.getState();
        this.postcode = patientAddress.getPostcode();
    }

    public PatientAddressDTO(String address, String suburb, String state, String postcode) {
        this.address = address;
        this.suburb = suburb;
        this.state = state;
        this.postcode = postcode;
    }

}

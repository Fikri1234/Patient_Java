package com.example.patient.model.dto;

import lombok.Data;

/**
 * Created by user on 13:01 03/11/2024, 2024
 */

@Data
public class ResponseApisDTO {
    private Integer code;
    private String message;
    private Object data;

}
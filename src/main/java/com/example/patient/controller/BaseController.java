package com.example.patient.controller;

import com.example.patient.constant.StatusConstant;
import com.example.patient.model.dto.ResponseApiPagedDTO;
import com.example.patient.model.dto.ResponseApisDTO;
import org.springframework.data.domain.Page;

/**
 * Created by user on 13:08 03/11/2024, 2024
 */

public class BaseController {

    public ResponseApisDTO responseApi(Object data) {

        ResponseApisDTO dto = new ResponseApisDTO();
        dto.setCode(200);
        dto.setMessage(StatusConstant.SUCCESS);
        dto.setData(data);

        return dto;
    }

    public <T> ResponseApiPagedDTO responseApiPaged(Page<T> page) {

        ResponseApiPagedDTO dto = new ResponseApiPagedDTO();
        dto.setCode(200);
        dto.setMessage(StatusConstant.SUCCESS);
        dto.setData(page.getContent());
        dto.setPage(page.getNumber());
        dto.setTotalElement(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());

        return dto;
    }

}

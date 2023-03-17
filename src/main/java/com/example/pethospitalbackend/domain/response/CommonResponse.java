package com.example.pethospitalbackend.domain.response;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class CommonResponse {
    private Object result;
    private String message;
    private Integer code;
}

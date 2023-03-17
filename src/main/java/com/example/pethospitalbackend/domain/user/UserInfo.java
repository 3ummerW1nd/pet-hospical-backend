package com.example.pethospitalbackend.domain.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private Integer id;

    private String name;

    private String phoneNumber;

    private Boolean role;

    private Integer level;
}

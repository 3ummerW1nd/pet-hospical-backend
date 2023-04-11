package com.example.pethospitalbackend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoEntity {
    private Integer id;

    private String name;

    private String phoneNumber;

    private Boolean role;

    private Integer level;
}
package com.example.pethospitalbackend.domain.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRole {
    private Integer id;
    private Boolean role;
}

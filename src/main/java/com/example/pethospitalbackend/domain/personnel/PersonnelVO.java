package com.example.pethospitalbackend.domain.personnel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelVO {
    
    private Integer id;
    
    private String name;

    private String gender;

    private String phoneNumber;

    private String duty;

    private String department;
}
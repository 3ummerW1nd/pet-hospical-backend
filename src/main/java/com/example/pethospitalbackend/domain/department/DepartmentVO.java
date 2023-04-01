package com.example.pethospitalbackend.domain.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentVO {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String functions;
    private Integer directorId;
    private String directorName;
}

package com.example.pethospitalbackend.domain.profile;

import com.example.pethospitalbackend.domain.BasicInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetProfileVO {
    private Integer id;

    private String name;

    private String gender;

    private String type;

    private Long age;

    private Date birthday;

    private String images;

    private String description;

    private Double weight;

    private List<BasicInfo> checkups;

    private List<BasicInfo> medicines;

    private List<BasicInfo> diseases;
}

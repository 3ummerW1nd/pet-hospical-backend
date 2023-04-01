package com.example.pethospitalbackend.domain.profile;

import com.example.pethospitalbackend.domain.BasicInfo;
import com.example.pethospitalbackend.domain.DiseaseType;
import com.example.pethospitalbackend.domain.Medicine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetProfileVO {

    private Integer id;

    private String name;

    private String images;

    private String gender;

    private String type;

    private Date birthday;

    private Double weight; //kilogram

    private String description;

    private Set<BasicInfo> checkups;

    private Set<BasicInfo> medicines;

    private Set<BasicInfo> diseases;
}

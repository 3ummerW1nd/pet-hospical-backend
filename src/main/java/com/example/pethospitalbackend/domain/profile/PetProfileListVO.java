package com.example.pethospitalbackend.domain.profile;

import com.example.pethospitalbackend.domain.Checkup;
import com.example.pethospitalbackend.domain.DiseaseType;
import com.example.pethospitalbackend.domain.Medicine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetProfileListVO {
    private Integer id;

    private String name;

    private String gender;

    private String type;

    private Long age;

    private List<String> diseases;
}

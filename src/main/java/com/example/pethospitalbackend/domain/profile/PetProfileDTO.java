package com.example.pethospitalbackend.domain.profile;

import com.example.pethospitalbackend.domain.BasicInfo;
import io.swagger.models.auth.In;

import java.sql.Date;
import java.util.List;


public interface PetProfileDTO {
    Integer getId();

    String getName();

    String getImages();

    Boolean getGender();

    String getType();

    Date getBirthday();

    Double getWeight();

    String getDescription();

    List<Integer> getCheckupIds();

    List<Integer> getMedicineIds();

    List<Integer> getDiseaseIds();

    List<String> getCheckupNames();

    List<String> getMedicineNames();

    List<String> getDiseaseNames();
}

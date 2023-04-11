package com.example.pethospitalbackend.domain.profile;

import java.util.Date;
import java.util.List;

public interface PetProfileListDTO {
    Integer getId();

    String getName();

    Boolean getGender();

    String getType();

    Date getBirthday();

    List<String> getDiseases();
}
package com.example.pethospitalbackend.domain.profile;

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
}

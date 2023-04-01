package com.example.pethospitalbackend.search.converter;

import com.example.pethospitalbackend.domain.*;
import com.example.pethospitalbackend.domain.department.Department;
import com.example.pethospitalbackend.domain.profile.Pet;
import com.example.pethospitalbackend.domain.user.User;
import com.example.pethospitalbackend.search.entity.Searchable;
import com.example.pethospitalbackend.search.entity.SearchableEntity;
import com.example.pethospitalbackend.search.entity.SearchableEntityWithFK;

import java.util.List;

public class SearchEntityConverter {
    public static SearchableEntity getSearchableEntity(Searchable searchable, Object pk) {
        if (searchable instanceof User) {
            User user = (User) searchable;
            return SearchableEntity.builder()
                    .id("user" + user.getId())
                    .phoneNumber(user.getPhoneNumber())
                    .name(user.getName())
                    .type("user")
                    .build();
        } else if (searchable instanceof Personnel) {
            Personnel personnel = (Personnel) searchable;
            return SearchableEntity.builder()
                    .id("personnel" + personnel.getId())
                    .phoneNumber(personnel.getPhoneNumber())
                    .name(personnel.getName() + ":" + personnel.getDuty())
                    .introduction(personnel.getDepartment())
                    .type("personnel")
                    .build();
        } else if (searchable instanceof Department) {
            Department department = (Department) searchable;
            Personnel personnel = (Personnel) pk;
            SearchableEntity searchableEntityWithFK = SearchableEntityWithFK.builder()
                    .id("department" + department.getId())
                    .phoneNumber(department.getPhoneNumber())
                    .name(department.getName())
                    .introduction(department.getFunctions())
                    .type("department")
                    .build();
        } else if (searchable instanceof Medicine) {

        } else if (searchable instanceof Pet) {
            Pet pet = (Pet) searchable;
            List<DiseaseType> list = (List<DiseaseType>) pk;
            StringBuilder stringBuilder = new StringBuilder();
            list.forEach(diseaseType -> {
                stringBuilder.append(diseaseType.getName() + ",");
            });
            return SearchableEntityWithFK.builder()
                    .id("petProfile" + pet.getId())
                    .name(pet.getName() + ":" + pet.getType())
                    .introduction(pet.getDescription())
                    .type("petProfile")
                    .build();
        } else if (searchable instanceof Checkup) {

        }
        return null;
    }
}

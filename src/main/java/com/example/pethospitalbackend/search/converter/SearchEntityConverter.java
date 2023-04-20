package com.example.pethospitalbackend.search.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.pethospitalbackend.domain.*;
import com.example.pethospitalbackend.domain.department.Department;
import com.example.pethospitalbackend.domain.department.DepartmentVO;
import com.example.pethospitalbackend.domain.department.DepartmentVOEntity;
import com.example.pethospitalbackend.domain.personnel.Personnel;
import com.example.pethospitalbackend.domain.personnel.PersonnelVO;
import com.example.pethospitalbackend.domain.profile.Pet;
import com.example.pethospitalbackend.domain.profile.PetProfileListVO;
import com.example.pethospitalbackend.domain.user.User;
import com.example.pethospitalbackend.domain.user.UserInfoEntity;
import com.example.pethospitalbackend.search.entity.Searchable;
import com.example.pethospitalbackend.search.entity.SearchableEntity;
import com.example.pethospitalbackend.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class SearchEntityConverter {
    public static SearchableEntity getSearchableEntity(Searchable searchable) {
        if (searchable instanceof User) {
            User user = (User) searchable;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("level", user.getLevel() + "级");
            jsonObject.put("role", user.getRole() ? "管理员" : "普通用户");
            String other = jsonObject.toJSONString();
            return SearchableEntity.builder()
                    .id("user_" + user.getId())
                    .phoneNumber(user.getPhoneNumber())
                    .name(user.getName())
                    .type("user")
                    .other(other)
                    .build();
        } else if (searchable instanceof Personnel) {
            Personnel personnel = (Personnel) searchable;
            return SearchableEntity.builder()
                    .id("personnel_" + personnel.getId())
                    .phoneNumber(personnel.getPhoneNumber())
                    .name(personnel.getName() + "_" + personnel.getDuty())
                    .introduction(personnel.getDepartment())
                    .other(personnel.getGender() ? "男" : "女")
                    .type("personnel")
                    .build();
        } else if (searchable instanceof Medicine) {
            Medicine medicine = (Medicine) searchable;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("price", medicine.getPrice());
            jsonObject.put("quantity", medicine.getQuantity());
            String other = jsonObject.toJSONString();
            return SearchableEntity.builder()
                    .id("medicine_" + medicine.getId())
                    .name(medicine.getName())
                    .introduction(medicine.getIntroduction())
                    .type("medicine")
                    .other(other)
                    .build();
        } else if (searchable instanceof Checkup) {
            Checkup checkup = (Checkup) searchable;
            return SearchableEntity.builder()
                    .id("checkup_" + checkup.getId())
                    .name(checkup.getName())
                    .introduction(checkup.getIntroduction())
                    .type("checkup")
                    .other(checkup.getPrice().toString())
                    .build();
        } else if (searchable instanceof Department) {
            Department department = (Department) searchable;
            JSONObject jsonObject = new JSONObject();
            Personnel personnel = department.getDirector();
            jsonObject.put("directorId", personnel.getId());
            jsonObject.put("directorName", personnel.getName());
            String other = jsonObject.toJSONString();
            return SearchableEntity.builder()
                    .id("department_" + department.getId())
                    .name(department.getName())
                    .introduction(department.getFunctions())
                    .phoneNumber(department.getPhoneNumber())
                    .type("department")
                    .other(other)
                    .build();
        } else if (searchable instanceof Pet) {
            Pet pet = (Pet) searchable;
            JSONObject jsonObject = new JSONObject();
            Set<DiseaseType> diseases = pet.getDiseases();
            List<String> names = new ArrayList<>();
            diseases.forEach(diseaseType -> names.add(diseaseType.getName()));
            jsonObject.put("birthday", DateUtil.getDateString(pet.getBirthday()));
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(names);
            jsonObject.put("diseases", jsonArray);
            String other = jsonObject.toJSONString();
            return SearchableEntity.builder()
                    .id("petProfile_" + pet.getId())
                    .name(pet.getName() + "_" + pet.getType() + "_" + (pet.getGender() ? "公" : "母"))
                    .introduction(pet.getDescription())
                    .other(other)
                    .type("pet_profile")
                    .build();
        }
        return null;
    }

    public static List<UserInfoEntity> getUserFromSearchableEntity(List<SearchableEntity> searchableEntity) {
        List<UserInfoEntity> result = new ArrayList<>();
        searchableEntity.forEach(searchable -> {
            JSONObject jsonObject = JSON.parseObject(searchable.getOther());
            Integer id = Integer.valueOf(searchable.getId().split("_")[1]);
            result.add(UserInfoEntity.builder()
            .id(id)
            .level(Integer.valueOf(String.valueOf(jsonObject.get("level").toString().charAt(0))))
            .role("管理员".equals(jsonObject.get("role").toString()))
            .name(searchable.getName())
            .phoneNumber(searchable.getPhoneNumber())
            .build());
        });
        return result;
    }

    public static List<PersonnelVO> getPersonnelFromSearchableEntity(List<SearchableEntity> searchableEntity) {
        List<PersonnelVO> result = new ArrayList<>();
        searchableEntity.forEach(searchable -> {
            Integer id = Integer.valueOf(searchable.getId().split("_")[1]);
            String[] nameAndDuty = searchable.getName().split("_");
            result.add(PersonnelVO.builder()
            .id(id)
            .phoneNumber(searchable.getPhoneNumber())
            .duty(nameAndDuty[1])
            .name(nameAndDuty[0])
            .gender(searchable.getOther())
            .build());
        });
        return result;
    }

    public static List<Medicine> getMedicineFromSearchableEntity(List<SearchableEntity> searchableEntity) {
        List<Medicine> result = new ArrayList<>();
        searchableEntity.forEach(searchable -> {
            JSONObject jsonObject = JSON.parseObject(searchable.getOther());
            Integer id = Integer.valueOf(searchable.getId().split("_")[1]);
            result.add(Medicine.builder()
            .id(id)
            .name(searchable.getName())
            .introduction(searchable.getIntroduction())
            .price(jsonObject.getDouble("price"))
            .quantity(jsonObject.getInteger("quantity"))
            .build());
        });
        return result;
    }

    public static List<Checkup> getCheckupFromSearchableEntity(List<SearchableEntity> searchableEntity) {
        List<Checkup> result = new ArrayList<>();
        searchableEntity.forEach(searchable -> {
            Integer id = Integer.valueOf(searchable.getId().split("_")[1]);
            result.add(Checkup.builder()
            .id(id)
            .name(searchable.getName())
            .introduction(searchable.getIntroduction())
            .price(Double.valueOf(searchable.getOther()))
            .build());
        });
        return result;
    }

    public static List<DepartmentVOEntity> getDepartmentsFromSearchableEntity(List<SearchableEntity> searchableEntity) {
        List<DepartmentVOEntity> result = new ArrayList<>();
        searchableEntity.forEach(searchable -> {
            Integer id = Integer.valueOf(searchable.getId().split("_")[1]);
            JSONObject jsonObject = JSON.parseObject(searchable.getOther());
            result.add(DepartmentVOEntity.builder()
                            .id(id)
                            .name(searchable.getName())
                            .functions(searchable.getIntroduction())
                            .phoneNumber(searchable.getPhoneNumber())
                            .directorId(jsonObject.getInteger("directorId"))
                            .directorName(jsonObject.getString("directorName"))
                    .build());
        });
        return result;
    }

    public static List<PetProfileListVO> getPetProfilesFromSearchableEntity(List<SearchableEntity> searchableEntity) {
        List<PetProfileListVO> result = new ArrayList<>();
        searchableEntity.forEach(searchable -> {
            Integer id = Integer.valueOf(searchable.getId().split("_")[1]);
            String[] nameTypeGender = searchable.getName().split("_");
            JSONObject jsonObject = JSON.parseObject(searchable.getOther());
            Date birthday = DateUtil.getDate(jsonObject.getString("birthday"));
            long birthdayTime = birthday.getTime();
            long now = System.currentTimeMillis();
            long ageInMillis = (long) (365.25 * 24 * 60 * 60 * 1000);
            result.add(PetProfileListVO.builder()
                    .id(id)
                    .name(nameTypeGender[0])
                    .type(nameTypeGender[1])
                    .gender(nameTypeGender[2])
                    .age((now - birthdayTime) / ageInMillis)
                    .diseases(jsonObject.getJSONArray("diseases").toJavaList(String.class))
                    .build());
        });
        return result;
    }

}

package com.example.pethospitalbackend.search.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.pethospitalbackend.domain.*;
import com.example.pethospitalbackend.domain.department.Department;
import com.example.pethospitalbackend.domain.department.DepartmentVO;
import com.example.pethospitalbackend.domain.user.User;
import com.example.pethospitalbackend.domain.user.UserInfo;
import com.example.pethospitalbackend.domain.user.UserInfoEntity;
import com.example.pethospitalbackend.search.entity.Searchable;
import com.example.pethospitalbackend.search.entity.SearchableEntity;

import java.util.ArrayList;
import java.util.List;

public class SearchEntityConverter {
    public static SearchableEntity getSearchableEntity(Searchable searchable, Object pk) {
        if (searchable instanceof User) {
            User user = (User) searchable;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("level", user.getId() + "级");
            jsonObject.put("role", user.getRole() ? "管理员" : "普通用户");
            String other = jsonObject.toJSONString();
            return SearchableEntity.builder()
                    .id("user:" + user.getId())
                    .phoneNumber(user.getPhoneNumber())
                    .name(user.getName())
                    .type("user")
                    .other(other)
                    .build();
        } else if (searchable instanceof Personnel) {
            Personnel personnel = (Personnel) searchable;
            return SearchableEntity.builder()
                    .id("personnel:" + personnel.getId())
                    .phoneNumber(personnel.getPhoneNumber())
                    .name(personnel.getName() + ":" + personnel.getDuty())
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
                    .id("medicine:" + medicine.getId())
                    .name(medicine.getName())
                    .introduction(medicine.getIntroduction())
                    .type("medicine")
                    .other(other)
                    .build();
        } else if (searchable instanceof Checkup) {
            Checkup checkup = (Checkup) searchable;
            return SearchableEntity.builder()
                    .id("checkup:" + checkup.getId())
                    .name(checkup.getName())
                    .introduction(checkup.getIntroduction())
                    .type("checkup")
                    .other(checkup.getPrice().toString())
                    .build();
        }
        return null;
    }

    public static List<UserInfoEntity> getUserFromSearchableEntity(List<SearchableEntity> searchableEntity) {
        List<UserInfoEntity> result = new ArrayList<>();
        searchableEntity.forEach(searchable -> {
            JSONObject jsonObject = JSON.parseObject(searchable.getOther());
            Integer id = Integer.valueOf(searchable.getId().split(":")[1]);
            result.add(UserInfoEntity.builder()
            .id(id)
            .level(Integer.valueOf(String.valueOf(jsonObject.get("level").toString().charAt(0))))
            .role("管理员".equals(jsonObject.get("role").toString()))
            .build());
        });
        return result;
    }

    public static List<Personnel> getPersonnelFromSearchableEntity(List<SearchableEntity> searchableEntity) {
        List<Personnel> result = new ArrayList<>();
        searchableEntity.forEach(searchable -> {
            JSONObject jsonObject = JSON.parseObject(searchable.getOther());
            Integer id = Integer.valueOf(searchable.getId().split(":")[1]);
            String[] nameAndDuty = searchable.getName().split(":");
            result.add(Personnel.builder()
            .id(id)
            .phoneNumber(searchable.getPhoneNumber())
            .duty(nameAndDuty[1])
            .name(nameAndDuty[0])
            .gender()
            .build());
        });
        return result;
    }

}

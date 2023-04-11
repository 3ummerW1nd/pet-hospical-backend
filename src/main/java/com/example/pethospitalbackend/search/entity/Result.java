package com.example.pethospitalbackend.search.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Result {
    private List<SearchableEntity> searchableEntityList;
    private Integer totalCount;
}
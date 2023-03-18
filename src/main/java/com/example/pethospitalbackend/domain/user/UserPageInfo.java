package com.example.pethospitalbackend.domain.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserPageInfo {
    private Integer currentPage;
    private Integer totalPages;
    private List<UserInfo> users;
}
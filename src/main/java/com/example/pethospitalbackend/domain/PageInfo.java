package com.example.pethospitalbackend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageInfo {
    private Integer currentPage;
    private Integer totalPages;
    private Object data;
}

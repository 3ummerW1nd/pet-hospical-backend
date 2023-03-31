package com.example.pethospitalbackend.domain.page;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckupPageInfo {
    private Integer currentPage;
    private Integer totalPages;
    private Object checkups;
}

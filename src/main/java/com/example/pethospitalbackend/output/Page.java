package com.example.pethospitalbackend.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private List<JSONObject> infos;
    private Integer currentPage;
    private Integer totalPages;

    public Page(List<JSONObject> infos, Integer currentPage) {
        if(currentPage == -1)
            this.infos = infos;
        else {
            int i = currentPage -1;
            this.infos = infos.subList(i * 10, i * 10 +10);
        }
        this.currentPage = currentPage;
        this.totalPages = infos.size() / 10 + 1;
    }
}

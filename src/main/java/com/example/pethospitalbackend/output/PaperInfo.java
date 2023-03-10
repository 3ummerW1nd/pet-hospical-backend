package com.example.pethospitalbackend.output;

import com.example.pethospitalbackend.domain.Paper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperInfo {
    private Integer paper_id;
    private Integer question_num;
    private String disease_type_name;
    private Integer score;
    private String name;

    public PaperInfo(Paper p, String disease_type_name) {
        this.paper_id = p.getId();
        this.question_num = p.getQuestion_num();
        this.disease_type_name = disease_type_name;
        this.score = 0;
        String[] pionts = p.getQuestion_points().trim().split(",");
        for(String piont : pionts)
            this.score += Integer.valueOf(piont);
        this.name = p.getName();
    }
}

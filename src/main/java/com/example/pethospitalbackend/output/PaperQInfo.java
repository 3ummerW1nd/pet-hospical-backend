package com.example.pethospitalbackend.output;

import com.example.pethospitalbackend.domain.Question;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperQInfo {
    private Integer question_id;
    private String disease_type_name;
    private String title;
    @JsonProperty("optionA")
    private String optionA;
    @JsonProperty("optionB")
    private String optionB;
    @JsonProperty("optionC")
    private String optionC;
    @JsonProperty("optionD")
    private String optionD;
    private String answer;
    private Integer question_score;

    public PaperQInfo(Question q, String disease_type_name,Integer question_score) {
        this.question_id = q.getId();
        this.disease_type_name = disease_type_name;
        this.title = q.getTitle();
        this.optionA = q.getA();
        this.optionB = q.getB();
        this.optionC = q.getC();
        this.optionD = q.getD();
        this.answer = q.getAnswer();
        this.question_score = question_score;
    }
}

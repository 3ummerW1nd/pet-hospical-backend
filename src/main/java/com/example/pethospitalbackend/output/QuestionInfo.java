package com.example.pethospitalbackend.output;

import com.example.pethospitalbackend.domain.Question;
import com.example.pethospitalbackend.repository.DiseaseTypeRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionInfo {
    private Integer question_id;
    private String disease_type_name;
    private Integer disease_type_id;
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

    public QuestionInfo(Question q, String name) {
        this.question_id = q.getId();
        this.disease_type_name = name;
        this.disease_type_id = q.getDisease_type_id();
        this.title = q.getTitle();
        this.optionA = q.getA();
        this.optionB = q.getB();
        this.optionC = q.getC();
        this.optionD = q.getD();
        this.answer = q.getAnswer();
    }
}

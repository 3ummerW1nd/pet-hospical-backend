package com.example.pethospitalbackend.output;

import com.example.pethospitalbackend.domain.Exam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamInfo {
    private Integer exam_id;
    private String exam_name;
    private String start_time;
    private String end_time;
    private Integer authority;

    public ExamInfo(Exam e) {
        this.exam_id = e.getId();
        this.exam_name = e.getName();
        this.start_time = e.getStart_time();
        this.end_time = e.getEnd_time();
        this.authority = e.getAuthority();
    }
}

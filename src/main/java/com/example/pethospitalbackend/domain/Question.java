package com.example.pethospitalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer disease_type_id;
    @Column(length = 500)
    private String title;
    @Column(length = 200)
    private String a;
    @Column(length = 200)
    private String b;
    @Column(length = 200)
    private String c;
    @Column(length = 200)
    private String d;
    @Column(length = 2)
    private String answer;

    public interface SimpleInfo{
        Integer getId();
        Integer getDisease_type_id();
        String getTitle();
    }
}

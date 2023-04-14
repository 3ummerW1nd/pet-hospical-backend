package com.example.pethospitalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "paper")
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer disease_type_id;
    @Column(length = 100)
    private String name;
    @Column(length = 500)
    private String question_ids;
    private Integer question_num;
    private int point;

}

package com.example.pethospitalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer paper_id;
    @Column(length = 100)
    private String name;
    @Column(length = 30)
    private String start_time;
    @Column(length = 30)
    private String end_time;
    private Integer authority;
}

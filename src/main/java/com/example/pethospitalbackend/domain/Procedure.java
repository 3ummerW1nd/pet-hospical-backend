package com.example.pethospitalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "procedure")
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer amount;
    @Column(length = 1000)
    private String step1;
    @Column(length = 1000)
    private String step2;
    @Column(length = 1000)
    private String step3;
    @Column(length = 1000)
    private String step4;
    @Column(length = 1000)
    private String step5;
    @Column(length = 1000)
    private String step6;

}

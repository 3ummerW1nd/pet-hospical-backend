package com.example.pethospitalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "disease")
public class Disease {
    @Id
    private Integer disease_type_id;
    @Column(length = 500)
    private String symptom;     //症状
    @Column(length = 500)
    private String examination; //检查
    @Column(length = 500)
    private String diagnosis;   //诊断
    @Column(length = 500)
    private String treatment;   //治疗
    private String image_ids;
    private String video_ids;

}

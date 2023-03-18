package com.example.pethospitalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "pet_profiles")
public class PetProfile {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(64)")
    private String name;

    @Column(name = "images")
    private String images;

    @Column(name = "type", columnDefinition = "VARCHAR(64)")
    private String type;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "weight")
    private Double weight; //kilogram

    @Column(name = "medical_history")
    private String medicalHistory;

    @Column(name = "allerfy_history")
    private String allerfyHistory;

    @Column(name = "blood_type", columnDefinition = "VARCHAR(64)")
    private String bloodType;
}

package com.example.pethospitalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(64)")
    private String name;

    @Column(name = "phoneNumber", nullable = false, columnDefinition = "VARCHAR(64)")
    private String phoneNumber;

    @Column(name = "directorId", nullable = false)
    private Integer directorId;

    @Column(name = "functions", nullable = false, columnDefinition = "VARCHAR(2048)")
    private String functions;
}

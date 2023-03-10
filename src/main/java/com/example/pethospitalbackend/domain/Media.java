package com.example.pethospitalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media")
public class Media {
    @Id
    @Column(length = 50)
    private String name;
    @Column(length = 100)
    private String description;
}

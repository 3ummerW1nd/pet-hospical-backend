package com.example.pethospitalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "db_character")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String role;
    private String action;
    private Integer procedure_id;
    private Integer step_num;

    public interface CharacterInfo{
        String getAction();
        Integer getProcedure_id();
    }
}

package com.example.pethospitalbackend.domain;

import com.example.pethospitalbackend.domain.profile.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "disease_type")
public class DiseaseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 20)
    private String type;
    @Column(length = 20)
    private String name;
//    @ManyToMany(mappedBy = "pet_profiles")
//    private Set<Pet> petProfiles;

    public interface DiseaseTypeInfo{
        Integer getId();
        String getName();
    }

    public interface DiseaseNameInfo{
        String getType();
        String getName();
    }
}

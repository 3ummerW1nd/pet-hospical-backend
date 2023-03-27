package com.example.pethospitalbackend.domain.profile;

import com.example.pethospitalbackend.domain.Checkup;
import com.example.pethospitalbackend.domain.Disease;
import com.example.pethospitalbackend.domain.DiseaseType;
import com.example.pethospitalbackend.domain.Medicine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "pet_profiles")
@NamedEntityGraph(
        name = "PetProfile",
        attributeNodes = {
                @NamedAttributeNode(value = "diseases", subgraph = "diseaseBasic"),
                @NamedAttributeNode(value = "checkups", subgraph = "checkupBasic"),
                @NamedAttributeNode(value = "medicines", subgraph = "medicineBasic"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "diseaseBasic",
                        attributeNodes = {
                                @NamedAttributeNode("name"),
                                @NamedAttributeNode("id")
                        }),
                @NamedSubgraph(
                        name = "checkupBasic",
                        attributeNodes = {
                                @NamedAttributeNode("name"),
                                @NamedAttributeNode("id")
                        }),
                @NamedSubgraph(
                        name = "medicineBasic",
                        attributeNodes = {
                                @NamedAttributeNode("name"),
                                @NamedAttributeNode("id")
                        }),
        }
)
public class Pet {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(64)")
    private String name;

    @Column(name = "images")
    private String images;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "type", columnDefinition = "VARCHAR(64)")
    private String type;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "weight")
    private Double weight; //kilogram

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "pet_profile_checkup", joinColumns = {
            @JoinColumn(name = "petId", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "checkupId", referencedColumnName = "id")})
    private Set<Checkup> checkups;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "pet_profile_medicines", joinColumns = {
            @JoinColumn(name = "petId", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "medicineId", referencedColumnName = "id")})
    private Set<Medicine> medicines;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "pet_profile_diseases", joinColumns = {
            @JoinColumn(name = "petId", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "diseaseId", referencedColumnName = "id")})
    private Set<DiseaseType> diseases;
}

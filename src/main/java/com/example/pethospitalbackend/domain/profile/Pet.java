package com.example.pethospitalbackend.domain.profile;

import com.example.pethospitalbackend.domain.Checkup;
import com.example.pethospitalbackend.domain.DiseaseType;
import com.example.pethospitalbackend.domain.Medicine;
import com.example.pethospitalbackend.search.entity.Searchable;
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
public class Pet implements Searchable {
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
    @JoinTable(name = "pet_profile_checkups", joinColumns = {
            @JoinColumn(name = "pet_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "checkup_id", referencedColumnName = "id")})
    private Set<Checkup> checkups;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "pet_profile_medicines", joinColumns = {
            @JoinColumn(name = "pet_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "medicine_id", referencedColumnName = "id")})
    private Set<Medicine> medicines;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "pet_profile_diseases", joinColumns = {
            @JoinColumn(name = "pet_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "disease_id", referencedColumnName = "id")})
    private Set<DiseaseType> diseases;
}

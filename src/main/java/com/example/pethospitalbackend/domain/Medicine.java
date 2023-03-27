package com.example.pethospitalbackend.domain;

import com.azure.search.documents.indexes.SearchableField;
import com.azure.search.documents.indexes.SimpleField;
import com.example.pethospitalbackend.domain.profile.Pet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(value = { "petProfiles" })
@Table(name = "medicines")
public class Medicine {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    @SimpleField(isKey = true)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(64)")
    @SearchableField(analyzerName = "zh-Hans.microsoft")
    @JsonProperty("name")
    private String name;

    @Column(name = "introduction", nullable = false, columnDefinition = "VARCHAR(2048)")
    @SearchableField(analyzerName = "zh-Hans.microsoft")
    @JsonProperty("introduction")
    private String introduction;

    @Column(name = "price", nullable = false, columnDefinition = "Decimal(8, 2)")
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

//    @ManyToMany(mappedBy = "pet_profiles")
//    private Set<Pet> petProfiles;
}

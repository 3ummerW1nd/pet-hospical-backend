package com.example.pethospitalbackend.domain;

import com.azure.search.documents.indexes.SearchableField;
import com.azure.search.documents.indexes.SimpleField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "checkups")
public class Checkup {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    @SimpleField(isKey = true)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(64)")
    @JsonProperty("name")
    @SearchableField(analyzerName = "zh-Hans.microsoft")
    private String name;

    @Column(name = "introduction", nullable = false, columnDefinition = "VARCHAR(2048)")
    @SearchableField(analyzerName = "zh-Hans.microsoft")
    @JsonProperty("introduction")
    private String introduction;

    @Column(name = "price", nullable = false, columnDefinition = "Decimal(8, 2)")
    private Double price;
}

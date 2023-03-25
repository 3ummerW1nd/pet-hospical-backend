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
@Table(name = "departments",
        indexes = {@Index(name = "my_index_name",  columnList="name", unique = true)})
public class Department {
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

    @Column(name = "phoneNumber", nullable = false, columnDefinition = "VARCHAR(64)")
    @SearchableField()
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @Column(name = "directorId", nullable = false)
    private Integer directorId;

    @Column(name = "functions", nullable = false, columnDefinition = "VARCHAR(2048)")
    @SearchableField(analyzerName = "zh-Hans.microsoft")
    @JsonProperty("functions")
    private String functions;
}

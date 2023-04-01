package com.example.pethospitalbackend.domain.department;

import com.azure.search.documents.indexes.SearchableField;
import com.azure.search.documents.indexes.SimpleField;
import com.example.pethospitalbackend.domain.Personnel;
import com.example.pethospitalbackend.search.entity.Searchable;
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
@Table(name = "departments",
        indexes = {@Index(name = "my_index_name",  columnList="name", unique = true)})
public class Department implements Searchable {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(64)")
    private String name;

    @Column(name = "phoneNumber", nullable = false, columnDefinition = "VARCHAR(64)")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "directorId")
    private Personnel director;

    @Column(name = "functions", nullable = false, columnDefinition = "VARCHAR(2048)")
    private String functions;
}

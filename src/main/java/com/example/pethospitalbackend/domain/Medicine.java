package com.example.pethospitalbackend.domain;

import com.azure.search.documents.indexes.SearchableField;
import com.azure.search.documents.indexes.SimpleField;
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
@Table(name = "medicines")
public class Medicine implements Searchable {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(64)")
    private String name;

    @Column(name = "introduction", nullable = false, columnDefinition = "VARCHAR(2048)")
    private String introduction;

    @Column(name = "price", nullable = false, columnDefinition = "Decimal(8, 2)")
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}

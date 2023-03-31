package com.example.pethospitalbackend.domain;

import com.example.pethospitalbackend.domain.converter.JsonNodeConverter;
import com.fasterxml.jackson.databind.JsonNode;
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
@Table(name = "equipments",
        indexes = {@Index(name = "my_index_name",  columnList="name", unique = true)})
public class Equipment {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(64)")
    private String name;

    @Column(name = "functions", nullable = false, columnDefinition = "VARCHAR(2048)")
    private String functions;

    @Column(name = "video", nullable = false)
    private String video;

    @Column(name = "process", columnDefinition = "JSON")
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode process;
}

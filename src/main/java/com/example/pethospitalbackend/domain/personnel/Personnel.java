package com.example.pethospitalbackend.domain.personnel;

import com.example.pethospitalbackend.search.entity.Searchable;
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
@Table(name = "personnels")
public class Personnel implements Searchable {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(64)")
    private String name;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "duty")
    private String duty;

    @Column(name = "department")
    private String department;
}

package com.example.pethospitalbackend.domain.user;

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
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(64)")
    private String name;

    @Column(name = "avatar", columnDefinition = "VARCHAR(64)")
    private String avatar;

    @Column(name = "password", columnDefinition = "VARCHAR(64)")
    private String password;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "role")
    private Boolean role;

    @Column(name = "level")
    private Integer level;
}


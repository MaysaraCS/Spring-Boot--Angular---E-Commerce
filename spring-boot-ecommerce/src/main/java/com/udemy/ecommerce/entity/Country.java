package com.udemy.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="country")
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private short id;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    // set up one to many relationship with state entity
    @OneToMany(mappedBy = "country")
    // to ignore states when we move to country endpoint from country repository
    @JsonIgnore
    private List<State> states;
}

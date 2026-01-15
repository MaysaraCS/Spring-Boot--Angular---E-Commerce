package com.udemy.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="state")
@Data
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private short id;

    @Column(name="name")
    private String name;

    // Many states belong to one country
    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;
}

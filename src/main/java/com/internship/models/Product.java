package com.internship.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int count;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    private Unit unit;

    @Column(nullable = false)
    private boolean busy;

    public Product(String name, int count, Unit unit, boolean busy) {
        this.name = name;
        this.count = count;
        this.unit = unit;
        this.busy = busy;
    }
}

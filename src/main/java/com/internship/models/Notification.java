package com.internship.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;

    @Column(nullable = false)
    private String dateOfReceipt;

    @Column(nullable = false)
    private String dateOfCreation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "checkpoint_id", referencedColumnName = "id")
    private Checkpoint checkpoint;


    @OneToMany(cascade = CascadeType.ALL)
    private Set<Product> product = new HashSet<>();

    //Дополнительная информация
    @Column(nullable = false)
    private String description;

    public Notification(String sender, String receiver, String dateOfReceipt, String dateOfCreation, Status status, Checkpoint checkpoint, Set<Product> product, String description) {
        this.sender = sender;
        this.receiver = receiver;
        this.dateOfReceipt = dateOfReceipt;
        this.dateOfCreation = dateOfCreation;
        this.status = status;
        this.checkpoint = checkpoint;
        this.product = product;
        this.description = description;
    }
}



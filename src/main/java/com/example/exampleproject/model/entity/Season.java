package com.example.exampleproject.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public final class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String year;
    private boolean current;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<League> leagues = new ArrayList<>();

}

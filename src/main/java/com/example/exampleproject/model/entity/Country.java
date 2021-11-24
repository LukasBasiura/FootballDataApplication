package com.example.exampleproject.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public final class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;
    private String code;
    private String flag;

    @OneToMany(mappedBy = "country",cascade = CascadeType.REMOVE)
    private List<League> leagues;

    @OneToMany(mappedBy = "country",cascade = CascadeType.REMOVE)
    private List<Team> teams;

}

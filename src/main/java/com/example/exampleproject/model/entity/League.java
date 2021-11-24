package com.example.exampleproject.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "country_id"})})
public final class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String type;
    private String logo;

    @ManyToOne
    private Country country;

    @ManyToMany(mappedBy = "leagues")
    private List<Season> seasons = new ArrayList<>();

    @OneToMany(mappedBy = "league")
    private List<Match> matches = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        League league = (League) o;
        return id == league.id && name.equals(league.name) && Objects.equals(type, league.type) && Objects.equals(logo, league.logo) && Objects.equals(country, league.country) && Objects.equals(seasons, league.seasons) && Objects.equals(matches, league.matches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, logo, country);
    }
}

package com.example.exampleproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Setter
@Getter
public final class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    private League league;

    @OneToOne
    private Team home;

    @OneToOne
    private Team away;

    @OneToOne
    private Statistic homeStat;

    @OneToOne
    private Statistic awayStat;

    @Embedded
    private FullTimeScore fullTimeScore;

    @Embedded
    private HalfTimeScore halfTimeScore;

    @Embedded
    private ExtraTimeScore extraTimeScore;

    @Embedded
    private Penalty penalty;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FullTimeScore {
        @Min(0) @NotNull Integer home_full;
        @Min(0) @NotNull Integer away_full;
    }

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HalfTimeScore {
        @Min(0) @NotNull Integer home_half;
        @Min(0) @NotNull Integer away_half;
    }

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExtraTimeScore {
        Integer home_extra;
        Integer away_extra;
    }

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Penalty {
        Integer home_pen;
        Integer away_pen;
    }
}

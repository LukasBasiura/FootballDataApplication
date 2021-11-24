package com.example.exampleproject.model.apidto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties
public record MatchDto(Fixture fixture, League league, Teams teams, Goal goals, Score score) {


    public record Fixture(Long id,@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") String date){}
    public record League(@NotNull String name,@NotNull String country, String logo, String flag, int season, String round){}
    public record Teams(@NotNull Team home,@NotNull Team away){}
    public record Team(@NotNull String name, String logo, String winner){}
    public record Goal(@Min(0) @NotNull Integer home,@Min(0) @NotNull Integer away){}
    public record Score(@NotNull HalfTime halftime,@NotNull FullTime fulltime,ExtraTime extratime, Penalty penalty){}
    public record HalfTime(@Min(0) @NotNull Integer home,@Min(0) @NotNull Integer away){}
    public record FullTime(@Min(0) @NotNull Integer home,@Min(0) @NotNull Integer away){}
    public record ExtraTime(Integer home,Integer away){}
    public record Penalty(Integer home,Integer away){}
}

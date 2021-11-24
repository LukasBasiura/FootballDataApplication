package com.example.exampleproject.model.apidto;

import com.example.exampleproject.model.entity.Match;

import java.time.LocalDate;

public record MatchPostDto(Long leagueId,
                           Long homeTeamId,
                           Long awayTeamId,
                           LocalDate date,
                           Match.FullTimeScore fullTimeScore,
                           Match.HalfTimeScore halfTimeScore,
                           Match.ExtraTimeScore extraTimeScore,
                           Match.Penalty penalty) {

}

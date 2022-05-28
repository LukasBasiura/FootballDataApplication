package com.example.exampleproject.controller;

import com.example.exampleproject.model.apidto.MatchDto;
import com.example.exampleproject.model.apidto.MatchPostDto;
import com.example.exampleproject.model.entity.League;
import com.example.exampleproject.model.entity.Match;
import com.example.exampleproject.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    @GetMapping(produces = "application/json")
    public List<MatchDto> getMatches(@Positive @RequestParam(required = false) Long leagueId,
                                     @Positive @RequestParam(required = false) Long homeTeamId,
                                     @Positive @RequestParam(required = false) Long awayTeamId,
                                     @Positive @RequestParam(required = false) Integer season,
                                     @PositiveOrZero @RequestParam @Min(0) int page) {
        if (leagueId == null && homeTeamId == null && awayTeamId == null && season == null) {
            List<Match> all = matchService.findAll(page);
            return convertMatchesToDtoList(all);
        }
        return List.of();
    }

    @PostMapping(consumes = "application/json")
    public MatchDto postMatch(@Valid @NotNull @RequestBody MatchPostDto dto) {
        Match match = matchService.saveMatch(dto.leagueId(), dto.homeTeamId(), dto.awayTeamId(), dto.fullTimeScore(), dto.halfTimeScore(), dto.extraTimeScore(), dto.penalty(), dto.date());
        return convertMatchesToDtoList(List.of(match)).stream().findFirst().orElse(null);
    }


    //Probably should replace this with object mapper
    private List<MatchDto> convertMatchesToDtoList(List<Match> dbMatches) {
        return dbMatches.stream().map(db -> {
                            League league = db.getLeague();
                            Match.HalfTimeScore halfTimeScore = db.getHalfTimeScore();
                            Match.FullTimeScore fullTimeScore = db.getFullTimeScore();
                            Match.ExtraTimeScore extraTimeScore = db.getExtraTimeScore();
                            Match.Penalty penalty = db.getPenalty();
                            return new MatchDto(
                                    new MatchDto.Fixture(db.getId(), db.getDate().toString()),
                                    new MatchDto.League(league.getName(), league.getCountry().getName(), league.getLogo(), null, db.getDate().getYear(), null),
                                    new MatchDto.Teams(new MatchDto.Team(db.getHome().getName(), db.getHome().getLogo(), null),
                                            new MatchDto.Team(db.getAway().getName(), db.getAway().getLogo(), null)),
                                    new MatchDto.Goal(fullTimeScore.getHome_full(), fullTimeScore.getAway_full()),
                                    new MatchDto.Score(new MatchDto.HalfTime(halfTimeScore.getHome_half(), halfTimeScore.getAway_half()),
                                            new MatchDto.FullTime(fullTimeScore.getHome_full(), fullTimeScore.getAway_full()),
                                            new MatchDto.ExtraTime(extraTimeScore.getHome_extra(), extraTimeScore.getAway_extra()),
                                            new MatchDto.Penalty(penalty.getHome_pen(), penalty.getAway_pen()))

                            );
                        }
                )
                .collect(Collectors.toList());
    }
}

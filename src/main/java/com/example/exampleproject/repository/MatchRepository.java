package com.example.exampleproject.repository;

import com.example.exampleproject.model.entity.League;
import com.example.exampleproject.model.entity.Match;
import com.example.exampleproject.model.entity.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long> {

    Match findMatchByLeagueAndHomeAndAwayAndDate(League league, Team home, Team away, LocalDate date);
    Match findMatchByHomeAndAwayAndDate(Team home, Team away, LocalDate date);

    @EntityGraph(attributePaths = {"league","home","away"})
    List<Match> findAllBy(Pageable pageable);

}

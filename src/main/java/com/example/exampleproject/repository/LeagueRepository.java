package com.example.exampleproject.repository;

import com.example.exampleproject.model.entity.Country;
import com.example.exampleproject.model.entity.League;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League,Long> {

    @EntityGraph(attributePaths = {"country"})
    League findByName(String name);

    @EntityGraph(attributePaths = {"country"})
    League findLeagueByNameAndCountry_Id(String name, Long country);

    @EntityGraph(attributePaths = {"country"})
    List<League> findLeaguesByCountry(Country country);

    @EntityGraph(attributePaths = {"country"})
    List<League> findLeaguesByName(String name);

    @EntityGraph(attributePaths = {"country"})
    List<League> findLeaguesByNameAndCountry(String name , Country country);

    @Override
    @EntityGraph(attributePaths = {"country"})
    List<League> findAll();
}

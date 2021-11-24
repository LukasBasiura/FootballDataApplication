package com.example.exampleproject.repository;

import com.example.exampleproject.model.entity.Country;
import com.example.exampleproject.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {


    Team findTeamByName(String name);

    Team findTeamByNameAndCountry(String name, Country country);
}

package com.example.exampleproject.repository;

import com.example.exampleproject.model.entity.Country;
import com.example.exampleproject.model.entity.League;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class LeagueRepositoryTest {

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void testFindByName() {
        List<League> premierLeagues = leagueRepository.findLeaguesByName("Premier League");
        Assertions.assertNotNull(premierLeagues);
        Assertions.assertFalse(premierLeagues.isEmpty());
    }

    @Test
    public void testFindByNameAndCountryId() {
        Country england = countryRepository.findFirstByName("England");
        League premierLeague = leagueRepository.findLeagueByNameAndCountry_Id("Premier League", england.getId());
        Assertions.assertNotNull(premierLeague);
    }

}

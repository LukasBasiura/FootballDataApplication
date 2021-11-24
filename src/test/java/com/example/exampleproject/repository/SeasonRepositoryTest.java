package com.example.exampleproject.repository;

import com.example.exampleproject.model.entity.Season;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SeasonRepositoryTest {

    @Autowired
    private SeasonRepository seasonRepository;

    @Test
    public void testFindByYear() {
        Season seasonByYear = seasonRepository.findByYear("2021");
        Assertions.assertNotNull(seasonByYear);
    }

    @Test
    public void testFindNotExistingByYear() {
        Season seasonByYear = seasonRepository.findByYear("2026");
        Assertions.assertNull(seasonByYear);
    }

    @Test
    public void testSaveSeason(){
        Season season = new Season();
        season.setYear("2100");
        season.setCurrent(false);
        Season save = seasonRepository.save(season);

        Assertions.assertNotNull(save);
        Assertions.assertEquals(season.getYear(),save.getYear());
    }

}

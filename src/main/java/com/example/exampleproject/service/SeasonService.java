package com.example.exampleproject.service;

import com.example.exampleproject.model.apidto.ISeason;
import com.example.exampleproject.model.entity.League;
import com.example.exampleproject.model.entity.Season;
import com.example.exampleproject.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;

    public Season findByYear(String year) {
        return seasonRepository.findByYear(year);
    }

    @Transactional
    public Season saveSeason(ISeason season, League dbLeague) {
        Season byYear = findByYear(season.year());
        if (byYear != null) {
            List<League> leagues = byYear.getLeagues();
            if (!leagues.contains(dbLeague)) {
                leagues.add(dbLeague);
            }
            return byYear;
        }
        Season seasonEntity = new Season();
        seasonEntity.setCurrent(season.current());
        seasonEntity.setYear(season.year());
        seasonEntity.getLeagues().add(dbLeague);
        byYear = seasonRepository.save(seasonEntity);
        dbLeague.getSeasons().add(byYear);
        return byYear;
    }

    public List<Season> addSeasons(List<Season> seasons) {
        return seasonRepository.saveAll(seasons);
    }

}

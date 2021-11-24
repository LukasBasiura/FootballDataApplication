package com.example.exampleproject.repository;

import com.example.exampleproject.model.entity.Season;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<Season,Long> {

    //prevents N+1 problem
    @EntityGraph(attributePaths = {"leagues","leagues.country"})
    Season findByYear(String year);

}

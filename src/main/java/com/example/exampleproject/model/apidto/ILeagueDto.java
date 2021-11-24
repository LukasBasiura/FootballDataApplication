package com.example.exampleproject.model.apidto;

import java.util.List;

public interface ILeagueDto {

    ICountry country();
    List<? extends ISeason> getSeasons();
    ILeague league();

}

package com.example.exampleproject.component;

import com.example.exampleproject.model.apidto.TeamWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FootballApiTest {

    @Autowired
    FootballClient client;

    @Test
    public void testTeams() throws JsonProcessingException {
        List<TeamWrapper> teams = client.getTeams(null, "England", false);

    }

}

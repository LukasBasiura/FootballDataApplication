package com.example.exampleproject.controller;

import com.example.exampleproject.model.apidto.LeagueDto;
import com.example.exampleproject.model.entity.League;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class LeagueControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void testGetPremierLeague() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/leagues/" + 6))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        League postedLeague = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), League.class);

        Assertions.assertNotNull(postedLeague);
        Assertions.assertEquals("Premier League", postedLeague.getName());
        Assertions.assertEquals("League", postedLeague.getType());
    }

    @Test
    @Order(2)
    public void testPostTestLeague() throws Exception {
        LeagueDto dto = new LeagueDto(0L, "TestName", "TestType", "TestLogo", null);
        String countryId = "5"; // England

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/leagues/")
                        .queryParam("countryId", countryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        League postedLeague = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), League.class);

        Assertions.assertNotNull(postedLeague);
        Assertions.assertEquals("TestName", postedLeague.getName());
        Assertions.assertEquals("TestType", postedLeague.getType());

        //clean posted entry
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/leagues/" + postedLeague.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

    }

}

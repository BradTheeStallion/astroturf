package com.astroturf.astroturf.controller;

import com.astroturf.astroturf.dto.TournamentDTO;
import com.astroturf.astroturf.exception.ResourceNotFoundException;
import com.astroturf.astroturf.service.TournamentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TournamentController.class)
public class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TournamentService tournamentService;

    @Autowired
    private ObjectMapper objectMapper;

    private TournamentDTO tournamentDTO;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    public void setup() {
        startDate = LocalDate.of(2023, 6, 1);
        endDate = LocalDate.of(2023, 6, 5);

        tournamentDTO = new TournamentDTO();
        tournamentDTO.setId(1L);
        tournamentDTO.setStartDate(startDate);
        tournamentDTO.setEndDate(endDate);
        tournamentDTO.setLocation("Tennis Club");
        tournamentDTO.setEntryFee(new BigDecimal("50.00"));
        tournamentDTO.setCashPrize(new BigDecimal("500.00"));
        tournamentDTO.setParticipantIds(new HashSet<>());
    }

    @Test
    public void testCreateTournament() throws Exception {
        given(tournamentService.createTournament(any(TournamentDTO.class))).willReturn(tournamentDTO);

        mockMvc.perform(post("/api/tournaments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tournamentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.location", is(tournamentDTO.getLocation())))
                .andExpect(jsonPath("$.entryFee", is(50.00)));

        verify(tournamentService, times(1)).createTournament(any(TournamentDTO.class));
    }

    @Test
    public void testGetTournamentById() throws Exception {
        given(tournamentService.getTournamentById(1L)).willReturn(tournamentDTO);

        mockMvc.perform(get("/api/tournaments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.location", is(tournamentDTO.getLocation())));

        verify(tournamentService, times(1)).getTournamentById(1L);
    }

    @Test
    public void testGetTournamentById_NotFound() throws Exception {
        given(tournamentService.getTournamentById(99L))
                .willThrow(new ResourceNotFoundException("Tournament not found with id: 99"));

        mockMvc.perform(get("/api/tournaments/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(tournamentService, times(1)).getTournamentById(99L);
    }

    @Test
    public void testGetAllTournaments() throws Exception {
        TournamentDTO tournament2 = new TournamentDTO();
        tournament2.setId(2L);
        tournament2.setStartDate(LocalDate.of(2023, 7, 1));
        tournament2.setEndDate(LocalDate.of(2023, 7, 5));
        tournament2.setLocation("Soccer Field");
        tournament2.setEntryFee(new BigDecimal("75.00"));
        tournament2.setCashPrize(new BigDecimal("750.00"));

        List<TournamentDTO> allTournaments = Arrays.asList(tournamentDTO, tournament2);

        given(tournamentService.getAllTournaments()).willReturn(allTournaments);

        mockMvc.perform(get("/api/tournaments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].location", is(tournamentDTO.getLocation())))
                .andExpect(jsonPath("$[1].location", is(tournament2.getLocation())));

        verify(tournamentService, times(1)).getAllTournaments();
    }

    @Test
    public void testUpdateTournament() throws Exception {
        TournamentDTO updatedTournamentDTO = new TournamentDTO();
        updatedTournamentDTO.setId(1L);
        updatedTournamentDTO.setStartDate(LocalDate.of(2023, 8, 1));
        updatedTournamentDTO.setEndDate(LocalDate.of(2023, 8, 5));
        updatedTournamentDTO.setLocation("Updated Location");
        updatedTournamentDTO.setEntryFee(new BigDecimal("100.00"));
        updatedTournamentDTO.setCashPrize(new BigDecimal("1000.00"));

        given(tournamentService.updateTournament(eq(1L), any(TournamentDTO.class)))
                .willReturn(updatedTournamentDTO);

        mockMvc.perform(put("/api/tournaments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTournamentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location", is("Updated Location")))
                .andExpect(jsonPath("$.entryFee", is(100.00)));

        verify(tournamentService, times(1)).updateTournament(eq(1L), any(TournamentDTO.class));
    }

    @Test
    public void testDeleteTournament() throws Exception {
        doNothing().when(tournamentService).deleteTournament(1L);

        mockMvc.perform(delete("/api/tournaments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tournamentService, times(1)).deleteTournament(1L);
    }

    @Test
    public void testAddMemberToTournament() throws Exception {
        given(tournamentService.addMemberToTournament(1L, 1L)).willReturn(tournamentDTO);

        mockMvc.perform(post("/api/tournaments/1/members/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(tournamentService, times(1)).addMemberToTournament(1L, 1L);
    }

    @Test
    public void testRemoveMemberFromTournament() throws Exception {
        given(tournamentService.removeMemberFromTournament(1L, 1L)).willReturn(tournamentDTO);

        mockMvc.perform(delete("/api/tournaments/1/members/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(tournamentService, times(1)).removeMemberFromTournament(1L, 1L);
    }

    @Test
    public void testGetTournamentsByStartDate() throws Exception {
        List<TournamentDTO> tournaments = Collections.singletonList(tournamentDTO);
        given(tournamentService.findTournamentsByStartDate(startDate)).willReturn(tournaments);

        mockMvc.perform(get("/api/tournaments/search/by-start-date")
                        .param("startDate", "2023-06-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].location", is(tournamentDTO.getLocation())));

        verify(tournamentService, times(1)).findTournamentsByStartDate(startDate);
    }

    @Test
    public void testGetTournamentsByLocation() throws Exception {
        List<TournamentDTO> tournaments = Collections.singletonList(tournamentDTO);
        given(tournamentService.findTournamentsByLocation("Tennis")).willReturn(tournaments);

        mockMvc.perform(get("/api/tournaments/search/by-location")
                        .param("location", "Tennis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].location", is(tournamentDTO.getLocation())));

        verify(tournamentService, times(1)).findTournamentsByLocation("Tennis");
    }

    @Test
    public void testGetTournamentsByDateRange() throws Exception {
        LocalDate rangeStart = LocalDate.of(2023, 5, 15);
        LocalDate rangeEnd = LocalDate.of(2023, 6, 15);
        List<TournamentDTO> tournaments = Collections.singletonList(tournamentDTO);
        given(tournamentService.findTournamentsByDateRange(rangeStart, rangeEnd)).willReturn(tournaments);

        mockMvc.perform(get("/api/tournaments/search/by-date-range")
                        .param("startDate", "2023-05-15")
                        .param("endDate", "2023-06-15")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].location", is(tournamentDTO.getLocation())));

        verify(tournamentService, times(1)).findTournamentsByDateRange(rangeStart, rangeEnd);
    }

    @Test
    public void testGetTournamentsByMemberId() throws Exception {
        List<TournamentDTO> tournaments = Collections.singletonList(tournamentDTO);
        given(tournamentService.findTournamentsByMemberId(1L)).willReturn(tournaments);

        mockMvc.perform(get("/api/tournaments/search/by-member")
                        .param("memberId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].location", is(tournamentDTO.getLocation())));

        verify(tournamentService, times(1)).findTournamentsByMemberId(1L);
    }

    @Test
    public void testCreateTournament_ValidationError() throws Exception {
        TournamentDTO invalidTournament = new TournamentDTO();

        mockMvc.perform(post("/api/tournaments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTournament)))
                .andExpect(status().isBadRequest());

        verify(tournamentService, never()).createTournament(any(TournamentDTO.class));
    }
}
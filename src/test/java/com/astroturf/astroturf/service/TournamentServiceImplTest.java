package com.astroturf.astroturf.service;

import com.astroturf.astroturf.dto.TournamentDTO;
import com.astroturf.astroturf.exception.ResourceNotFoundException;
import com.astroturf.astroturf.model.Member;
import com.astroturf.astroturf.model.Tournament;
import com.astroturf.astroturf.repository.MemberRepository;
import com.astroturf.astroturf.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TournamentServiceImplTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    private Tournament tournament;
    private TournamentDTO tournamentDTO;
    private Member member;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    public void setup() {
        startDate = LocalDate.of(2023, 6, 1);
        endDate = LocalDate.of(2023, 6, 5);

        tournament = new Tournament();
        tournament.setId(1L);
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        tournament.setLocation("Tennis Club");
        tournament.setEntryFee(new BigDecimal("50.00"));
        tournament.setCashPrize(new BigDecimal("500.00"));
        tournament.setParticipants(new HashSet<>());

        tournamentDTO = new TournamentDTO();
        tournamentDTO.setId(1L);
        tournamentDTO.setStartDate(startDate);
        tournamentDTO.setEndDate(endDate);
        tournamentDTO.setLocation("Tennis Club");
        tournamentDTO.setEntryFee(new BigDecimal("50.00"));
        tournamentDTO.setCashPrize(new BigDecimal("500.00"));
        tournamentDTO.setParticipantIds(new HashSet<>());

        member = new Member();
        member.setId(1L);
        member.setName("John Doe");
    }

    @Test
    public void testCreateTournament() {
        given(tournamentRepository.save(any(Tournament.class))).willReturn(tournament);

        TournamentDTO savedTournament = tournamentService.createTournament(tournamentDTO);

        assertThat(savedTournament).isNotNull();
        assertThat(savedTournament.getId()).isEqualTo(1L);
        assertThat(savedTournament.getLocation()).isEqualTo("Tennis Club");
        verify(tournamentRepository, times(1)).save(any(Tournament.class));
    }

    @Test
    public void testCreateTournamentWithParticipants() {
        Set<Long> participantIds = new HashSet<>();
        participantIds.add(1L);
        tournamentDTO.setParticipantIds(participantIds);

        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        given(tournamentRepository.save(any(Tournament.class))).willReturn(tournament);

        TournamentDTO savedTournament = tournamentService.createTournament(tournamentDTO);

        assertThat(savedTournament).isNotNull();
        verify(memberRepository, times(1)).findById(1L);
        verify(tournamentRepository, times(1)).save(any(Tournament.class));
    }

    @Test
    public void testGetTournamentById() {
        given(tournamentRepository.findById(1L)).willReturn(Optional.of(tournament));

        TournamentDTO foundTournament = tournamentService.getTournamentById(1L);

        assertThat(foundTournament).isNotNull();
        assertThat(foundTournament.getId()).isEqualTo(1L);
        verify(tournamentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTournamentById_NotFound() {
        given(tournamentRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            tournamentService.getTournamentById(99L);
        });
        verify(tournamentRepository, times(1)).findById(99L);
    }

    @Test
    public void testGetAllTournaments() {
        Tournament tournament2 = new Tournament();
        tournament2.setId(2L);
        tournament2.setStartDate(LocalDate.of(2023, 7, 1));
        tournament2.setEndDate(LocalDate.of(2023, 7, 5));
        tournament2.setLocation("Soccer Field");
        tournament2.setEntryFee(new BigDecimal("75.00"));
        tournament2.setCashPrize(new BigDecimal("750.00"));

        List<Tournament> tournaments = Arrays.asList(tournament, tournament2);

        given(tournamentRepository.findAll()).willReturn(tournaments);

        List<TournamentDTO> allTournaments = tournamentService.getAllTournaments();

        assertThat(allTournaments).isNotNull();
        assertThat(allTournaments.size()).isEqualTo(2);
        verify(tournamentRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateTournament() {
        TournamentDTO updateDTO = new TournamentDTO();
        updateDTO.setId(1L);
        updateDTO.setStartDate(LocalDate.of(2023, 8, 1));
        updateDTO.setEndDate(LocalDate.of(2023, 8, 5));
        updateDTO.setLocation("Updated Location");
        updateDTO.setEntryFee(new BigDecimal("100.00"));
        updateDTO.setCashPrize(new BigDecimal("1000.00"));

        given(tournamentRepository.findById(1L)).willReturn(Optional.of(tournament));

        Tournament updatedTournament = new Tournament();
        updatedTournament.setId(1L);
        updatedTournament.setStartDate(LocalDate.of(2023, 8, 1));
        updatedTournament.setEndDate(LocalDate.of(2023, 8, 5));
        updatedTournament.setLocation("Updated Location");
        updatedTournament.setEntryFee(new BigDecimal("100.00"));
        updatedTournament.setCashPrize(new BigDecimal("1000.00"));

        given(tournamentRepository.save(any(Tournament.class))).willReturn(updatedTournament);

        TournamentDTO result = tournamentService.updateTournament(1L, updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getLocation()).isEqualTo("Updated Location");
        assertThat(result.getEntryFee()).isEqualTo(new BigDecimal("100.00"));
        verify(tournamentRepository, times(1)).findById(1L);
        verify(tournamentRepository, times(1)).save(any(Tournament.class));
    }

    @Test
    public void testUpdateTournamentWithParticipants() {
        TournamentDTO updateDTO = new TournamentDTO();
        updateDTO.setId(1L);
        updateDTO.setStartDate(LocalDate.of(2023, 8, 1));
        updateDTO.setEndDate(LocalDate.of(2023, 8, 5));
        updateDTO.setLocation("Updated Location");
        updateDTO.setEntryFee(new BigDecimal("100.00"));
        updateDTO.setCashPrize(new BigDecimal("1000.00"));

        Set<Long> participantIds = new HashSet<>();
        participantIds.add(1L);
        updateDTO.setParticipantIds(participantIds);

        given(tournamentRepository.findById(1L)).willReturn(Optional.of(tournament));
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        given(tournamentRepository.save(any(Tournament.class))).willReturn(tournament);

        TournamentDTO result = tournamentService.updateTournament(1L, updateDTO);

        assertThat(result).isNotNull();
        verify(tournamentRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).findById(1L);
        verify(tournamentRepository, times(1)).save(any(Tournament.class));
    }

    @Test
    public void testDeleteTournament() {
        given(tournamentRepository.findById(1L)).willReturn(Optional.of(tournament));
        doNothing().when(tournamentRepository).delete(any(Tournament.class));

        tournamentService.deleteTournament(1L);

        verify(tournamentRepository, times(1)).findById(1L);
        verify(tournamentRepository, times(1)).delete(tournament);
    }

    @Test
    public void testAddMemberToTournament() {
        given(tournamentRepository.findById(1L)).willReturn(Optional.of(tournament));
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        given(tournamentRepository.save(any(Tournament.class))).willReturn(tournament);

        TournamentDTO result = tournamentService.addMemberToTournament(1L, 1L);

        assertThat(result).isNotNull();
        verify(tournamentRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).findById(1L);
        verify(tournamentRepository, times(1)).save(tournament);
    }

    @Test
    public void testRemoveMemberFromTournament() {
        Set<Member> participants = new HashSet<>();
        participants.add(member);
        tournament.setParticipants(participants);

        given(tournamentRepository.findById(1L)).willReturn(Optional.of(tournament));
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        given(tournamentRepository.save(any(Tournament.class))).willReturn(tournament);

        TournamentDTO result = tournamentService.removeMemberFromTournament(1L, 1L);

        assertThat(result).isNotNull();
        verify(tournamentRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).findById(1L);
        verify(tournamentRepository, times(1)).save(tournament);
    }

    @Test
    public void testFindTournamentsByStartDate() {
        List<Tournament> tournaments = Collections.singletonList(tournament);
        given(tournamentRepository.findByStartDate(startDate)).willReturn(tournaments);

        List<TournamentDTO> result = tournamentService.findTournamentsByStartDate(startDate);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getStartDate()).isEqualTo(startDate);
        verify(tournamentRepository, times(1)).findByStartDate(startDate);
    }

    @Test
    public void testFindTournamentsByLocation() {
        List<Tournament> tournaments = Collections.singletonList(tournament);
        given(tournamentRepository.findByLocationContainingIgnoreCase("Tennis")).willReturn(tournaments);

        List<TournamentDTO> result = tournamentService.findTournamentsByLocation("Tennis");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getLocation()).isEqualTo("Tennis Club");
        verify(tournamentRepository, times(1)).findByLocationContainingIgnoreCase("Tennis");
    }

    @Test
    public void testFindTournamentsByDateRange() {
        LocalDate rangeStart = LocalDate.of(2023, 5, 15);
        LocalDate rangeEnd = LocalDate.of(2023, 6, 15);
        List<Tournament> tournaments = Collections.singletonList(tournament);
        given(tournamentRepository.findByDateRange(rangeStart, rangeEnd)).willReturn(tournaments);

        List<TournamentDTO> result = tournamentService.findTournamentsByDateRange(rangeStart, rangeEnd);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        verify(tournamentRepository, times(1)).findByDateRange(rangeStart, rangeEnd);
    }

    @Test
    public void testFindTournamentsByMemberId() {
        List<Tournament> tournaments = Collections.singletonList(tournament);
        given(tournamentRepository.findByMemberId(1L)).willReturn(tournaments);

        List<TournamentDTO> result = tournamentService.findTournamentsByMemberId(1L);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        verify(tournamentRepository, times(1)).findByMemberId(1L);
    }
}
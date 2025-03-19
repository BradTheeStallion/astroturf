package com.astroturf.astroturf.service;

import com.astroturf.astroturf.dto.TournamentDTO;

import java.time.LocalDate;
import java.util.List;

public interface TournamentService {

    TournamentDTO createTournament(TournamentDTO tournamentDTO);

    TournamentDTO getTournamentById(Long id);

    List<TournamentDTO> getAllTournaments();

    TournamentDTO updateTournament(Long id, TournamentDTO tournamentDTO);

    void deleteTournament(Long id);

    TournamentDTO addMemberToTournament(Long tournamentId, Long memberId);

    TournamentDTO removeMemberFromTournament(Long tournamentId, Long memberId);

    List<TournamentDTO> findTournamentsByStartDate(LocalDate startDate);

    List<TournamentDTO> findTournamentsByLocation(String location);

    List<TournamentDTO> findTournamentsByDateRange(LocalDate startDate, LocalDate endDate);

    List<TournamentDTO> findTournamentsByMemberId(Long memberId);
}
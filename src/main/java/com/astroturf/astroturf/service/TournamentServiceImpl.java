package com.astroturf.astroturf.service;

import com.astroturf.astroturf.dto.TournamentDTO;
import com.astroturf.astroturf.exception.ResourceNotFoundException;
import com.astroturf.astroturf.model.Member;
import com.astroturf.astroturf.model.Tournament;
import com.astroturf.astroturf.repository.MemberRepository;
import com.astroturf.astroturf.repository.TournamentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public TournamentDTO createTournament(TournamentDTO tournamentDTO) {
        Tournament tournament = mapToEntity(tournamentDTO);

        if (tournamentDTO.getParticipantIds() != null && !tournamentDTO.getParticipantIds().isEmpty()) {
            Set<Member> participants = new HashSet<>();
            for (Long memberId : tournamentDTO.getParticipantIds()) {
                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));
                participants.add(member);
            }
            tournament.setParticipants(participants);
        }

        Tournament newTournament = tournamentRepository.save(tournament);
        return mapToDTO(newTournament);
    }

    @Override
    public TournamentDTO getTournamentById(Long id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found with id: " + id));
        return mapToDTO(tournament);
    }

    @Override
    public List<TournamentDTO> getAllTournaments() {
        List<Tournament> tournaments = tournamentRepository.findAll();
        return tournaments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TournamentDTO updateTournament(Long id, TournamentDTO tournamentDTO) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found with id: " + id));

        tournament.setStartDate(tournamentDTO.getStartDate());
        tournament.setEndDate(tournamentDTO.getEndDate());
        tournament.setLocation(tournamentDTO.getLocation());
        tournament.setEntryFee(tournamentDTO.getEntryFee());
        tournament.setCashPrize(tournamentDTO.getCashPrize());

        if (tournamentDTO.getParticipantIds() != null) {
            Set<Member> participants = new HashSet<>();
            for (Long memberId : tournamentDTO.getParticipantIds()) {
                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));
                participants.add(member);
            }
            tournament.setParticipants(participants);
        }

        Tournament updatedTournament = tournamentRepository.save(tournament);
        return mapToDTO(updatedTournament);
    }

    @Override
    public void deleteTournament(Long id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found with id: " + id));
        tournamentRepository.delete(tournament);
    }

    @Override
    @Transactional
    public TournamentDTO addMemberToTournament(Long tournamentId, Long memberId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found with id: " + tournamentId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        tournament.getParticipants().add(member);
        Tournament updatedTournament = tournamentRepository.save(tournament);

        return mapToDTO(updatedTournament);
    }

    @Override
    @Transactional
    public TournamentDTO removeMemberFromTournament(Long tournamentId, Long memberId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found with id: " + tournamentId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        tournament.getParticipants().remove(member);
        Tournament updatedTournament = tournamentRepository.save(tournament);

        return mapToDTO(updatedTournament);
    }

    @Override
    public List<TournamentDTO> findTournamentsByStartDate(LocalDate startDate) {
        List<Tournament> tournaments = tournamentRepository.findByStartDate(startDate);
        return tournaments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TournamentDTO> findTournamentsByLocation(String location) {
        List<Tournament> tournaments = tournamentRepository.findByLocationContainingIgnoreCase(location);
        return tournaments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TournamentDTO> findTournamentsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Tournament> tournaments = tournamentRepository.findByDateRange(startDate, endDate);
        return tournaments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TournamentDTO> findTournamentsByMemberId(Long memberId) {
        List<Tournament> tournaments = tournamentRepository.findByMemberId(memberId);
        return tournaments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private TournamentDTO mapToDTO(Tournament tournament) {
        TournamentDTO tournamentDTO = new TournamentDTO();
        tournamentDTO.setId(tournament.getId());
        tournamentDTO.setStartDate(tournament.getStartDate());
        tournamentDTO.setEndDate(tournament.getEndDate());
        tournamentDTO.setLocation(tournament.getLocation());
        tournamentDTO.setEntryFee(tournament.getEntryFee());
        tournamentDTO.setCashPrize(tournament.getCashPrize());

        Set<Long> participantIds = new HashSet<>();
        if (tournament.getParticipants() != null) {
            participantIds = tournament.getParticipants().stream()
                    .map(Member::getId)
                    .collect(Collectors.toSet());
        }
        tournamentDTO.setParticipantIds(participantIds);

        return tournamentDTO;
    }

    private Tournament mapToEntity(TournamentDTO tournamentDTO) {
        Tournament tournament = new Tournament();
        tournament.setId(tournamentDTO.getId());
        tournament.setStartDate(tournamentDTO.getStartDate());
        tournament.setEndDate(tournamentDTO.getEndDate());
        tournament.setLocation(tournamentDTO.getLocation());
        tournament.setEntryFee(tournamentDTO.getEntryFee());
        tournament.setCashPrize(tournamentDTO.getCashPrize());
        return tournament;
    }
}
package com.astroturf.astroturf.controller;

import com.astroturf.astroturf.dto.TournamentDTO;
import com.astroturf.astroturf.service.TournamentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<TournamentDTO> createTournament(@Valid @RequestBody TournamentDTO tournamentDTO) {
        return new ResponseEntity<>(tournamentService.createTournament(tournamentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.getTournamentById(id));
    }

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAllTournaments() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TournamentDTO> updateTournament(@PathVariable Long id, @Valid @RequestBody TournamentDTO tournamentDTO) {
        return ResponseEntity.ok(tournamentService.updateTournament(id, tournamentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tournamentId}/members/{memberId}")
    public ResponseEntity<TournamentDTO> addMemberToTournament(
            @PathVariable Long tournamentId, @PathVariable Long memberId) {
        return ResponseEntity.ok(tournamentService.addMemberToTournament(tournamentId, memberId));
    }

    @DeleteMapping("/{tournamentId}/members/{memberId}")
    public ResponseEntity<TournamentDTO> removeMemberFromTournament(
            @PathVariable Long tournamentId, @PathVariable Long memberId) {
        return ResponseEntity.ok(tournamentService.removeMemberFromTournament(tournamentId, memberId));
    }

    @GetMapping("/search/by-start-date")
    public ResponseEntity<List<TournamentDTO>> getTournamentsByStartDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        return ResponseEntity.ok(tournamentService.findTournamentsByStartDate(startDate));
    }

    @GetMapping("/search/by-location")
    public ResponseEntity<List<TournamentDTO>> getTournamentsByLocation(@RequestParam String location) {
        return ResponseEntity.ok(tournamentService.findTournamentsByLocation(location));
    }

    @GetMapping("/search/by-date-range")
    public ResponseEntity<List<TournamentDTO>> getTournamentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(tournamentService.findTournamentsByDateRange(startDate, endDate));
    }

    @GetMapping("/search/by-member")
    public ResponseEntity<List<TournamentDTO>> getTournamentsByMemberId(@RequestParam Long memberId) {
        return ResponseEntity.ok(tournamentService.findTournamentsByMemberId(memberId));
    }
}
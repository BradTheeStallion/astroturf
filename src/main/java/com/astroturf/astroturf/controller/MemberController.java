package com.astroturf.astroturf.controller;

import com.astroturf.astroturf.dto.MemberDTO;
import com.astroturf.astroturf.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO memberDTO) {
        return new ResponseEntity<>(memberService.createMember(memberDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.updateMember(id, memberDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<List<MemberDTO>> getMembersByName(@RequestParam String name) {
        return ResponseEntity.ok(memberService.findMembersByName(name));
    }

    @GetMapping("/search/by-phone")
    public ResponseEntity<List<MemberDTO>> getMembersByPhone(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(memberService.findMembersByPhoneNumber(phoneNumber));
    }

    @GetMapping("/search/by-membership-start-date")
    public ResponseEntity<List<MemberDTO>> getMembersByMembershipStartDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        return ResponseEntity.ok(memberService.findMembersByMembershipStartDate(startDate));
    }

    @GetMapping("/search/by-membership-duration")
    public ResponseEntity<List<MemberDTO>> getMembersByMembershipDuration(@RequestParam Integer duration) {
        return ResponseEntity.ok(memberService.findMembersByMembershipDuration(duration));
    }

    @GetMapping("/search/by-tournament")
    public ResponseEntity<List<MemberDTO>> getMembersByTournamentId(@RequestParam Long tournamentId) {
        return ResponseEntity.ok(memberService.findMembersByTournamentId(tournamentId));
    }

    @GetMapping("/search/by-tournament-start-date")
    public ResponseEntity<List<MemberDTO>> getMembersByTournamentStartDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        return ResponseEntity.ok(memberService.findMembersByTournamentStartDate(startDate));
    }
}
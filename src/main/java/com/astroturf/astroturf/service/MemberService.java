package com.astroturf.astroturf.service;

import com.astroturf.astroturf.dto.MemberDTO;

import java.time.LocalDate;
import java.util.List;

public interface MemberService {

    MemberDTO createMember(MemberDTO memberDTO);

    MemberDTO getMemberById(Long id);

    List<MemberDTO> getAllMembers();

    MemberDTO updateMember(Long id, MemberDTO memberDTO);

    void deleteMember(Long id);

    List<MemberDTO> findMembersByName(String name);

    List<MemberDTO> findMembersByPhoneNumber(String phoneNumber);

    List<MemberDTO> findMembersByMembershipStartDate(LocalDate startDate);

    List<MemberDTO> findMembersByMembershipDuration(Integer duration);

    List<MemberDTO> findMembersByTournamentId(Long tournamentId);

    List<MemberDTO> findMembersByTournamentStartDate(LocalDate startDate);
}
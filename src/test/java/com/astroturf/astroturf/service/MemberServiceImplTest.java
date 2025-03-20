package com.astroturf.astroturf.service;

import com.astroturf.astroturf.dto.MemberDTO;
import com.astroturf.astroturf.exception.ResourceNotFoundException;
import com.astroturf.astroturf.model.Member;
import com.astroturf.astroturf.model.Tournament;
import com.astroturf.astroturf.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member;
    private MemberDTO memberDTO;
    private LocalDate membershipStartDate;

    @BeforeEach
    public void setup() {
        membershipStartDate = LocalDate.of(2023, 1, 1);

        member = new Member();
        member.setId(1L);
        member.setName("John Doe");
        member.setAddress("123 Main St");
        member.setEmail("john.doe@example.com");
        member.setPhoneNumber("555-1234");
        member.setMembershipStartDate(membershipStartDate);
        member.setMembershipDuration(12);
        member.setTournaments(new HashSet<>());

        memberDTO = new MemberDTO();
        memberDTO.setId(1L);
        memberDTO.setName("John Doe");
        memberDTO.setAddress("123 Main St");
        memberDTO.setEmail("john.doe@example.com");
        memberDTO.setPhoneNumber("555-1234");
        memberDTO.setMembershipStartDate(membershipStartDate);
        memberDTO.setMembershipDuration(12);
    }

    @Test
    public void testCreateMember() {
        given(memberRepository.save(any(Member.class))).willReturn(member);

        MemberDTO savedMember = memberService.createMember(memberDTO);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isEqualTo(1L);
        assertThat(savedMember.getName()).isEqualTo("John Doe");
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void testGetMemberById() {
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        MemberDTO foundMember = memberService.getMemberById(1L);

        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getId()).isEqualTo(1L);
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetMemberById_NotFound() {
        given(memberRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            memberService.getMemberById(99L);
        });
        verify(memberRepository, times(1)).findById(99L);
    }

    @Test
    public void testGetAllMembers() {
        Member member2 = new Member();
        member2.setId(2L);
        member2.setName("Jane Smith");
        member2.setEmail("jane.smith@example.com");
        member2.setAddress("456 Oak St");
        member2.setPhoneNumber("555-5678");
        member2.setMembershipStartDate(LocalDate.of(2023, 2, 1));
        member2.setMembershipDuration(6);

        List<Member> members = Arrays.asList(member, member2);

        given(memberRepository.findAll()).willReturn(members);

        List<MemberDTO> allMembers = memberService.getAllMembers();

        assertThat(allMembers).isNotNull();
        assertThat(allMembers.size()).isEqualTo(2);
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateMember() {
        MemberDTO updateDTO = new MemberDTO();
        updateDTO.setId(1L);
        updateDTO.setName("John Updated");
        updateDTO.setAddress("Updated Address");
        updateDTO.setEmail("john.updated@example.com");
        updateDTO.setPhoneNumber("555-9876");
        updateDTO.setMembershipStartDate(LocalDate.of(2023, 3, 1));
        updateDTO.setMembershipDuration(24);

        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        Member updatedMember = new Member();
        updatedMember.setId(1L);
        updatedMember.setName("John Updated");
        updatedMember.setAddress("Updated Address");
        updatedMember.setEmail("john.updated@example.com");
        updatedMember.setPhoneNumber("555-9876");
        updatedMember.setMembershipStartDate(LocalDate.of(2023, 3, 1));
        updatedMember.setMembershipDuration(24);

        given(memberRepository.save(any(Member.class))).willReturn(updatedMember);

        MemberDTO result = memberService.updateMember(1L, updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Updated");
        assertThat(result.getEmail()).isEqualTo("john.updated@example.com");
        assertThat(result.getMembershipDuration()).isEqualTo(24);
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void testDeleteMember() {
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        doNothing().when(memberRepository).delete(any(Member.class));

        memberService.deleteMember(1L);

        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).delete(member);
    }

    @Test
    public void testFindMembersByName() {
        List<Member> members = Collections.singletonList(member);
        given(memberRepository.findByNameContainingIgnoreCase("John")).willReturn(members);

        List<MemberDTO> result = memberService.findMembersByName("John");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("John Doe");
        verify(memberRepository, times(1)).findByNameContainingIgnoreCase("John");
    }

    @Test
    public void testFindMembersByPhoneNumber() {
        List<Member> members = Collections.singletonList(member);
        given(memberRepository.findByPhoneNumberContaining("555")).willReturn(members);

        List<MemberDTO> result = memberService.findMembersByPhoneNumber("555");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getPhoneNumber()).isEqualTo("555-1234");
        verify(memberRepository, times(1)).findByPhoneNumberContaining("555");
    }

    @Test
    public void testFindMembersByMembershipStartDate() {
        List<Member> members = Collections.singletonList(member);
        given(memberRepository.findByMembershipStartDate(membershipStartDate)).willReturn(members);

        List<MemberDTO> result = memberService.findMembersByMembershipStartDate(membershipStartDate);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getMembershipStartDate()).isEqualTo(membershipStartDate);
        verify(memberRepository, times(1)).findByMembershipStartDate(membershipStartDate);
    }

    @Test
    public void testFindMembersByMembershipDuration() {
        List<Member> members = Collections.singletonList(member);
        given(memberRepository.findByMembershipDuration(12)).willReturn(members);

        List<MemberDTO> result = memberService.findMembersByMembershipDuration(12);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getMembershipDuration()).isEqualTo(12);
        verify(memberRepository, times(1)).findByMembershipDuration(12);
    }

    @Test
    public void testFindMembersByTournamentId() {
        Tournament tournament = new Tournament();
        tournament.setId(1L);

        Set<Tournament> tournaments = new HashSet<>();
        tournaments.add(tournament);
        member.setTournaments(tournaments);

        List<Member> members = Collections.singletonList(member);
        given(memberRepository.findByTournamentId(1L)).willReturn(members);

        List<MemberDTO> result = memberService.findMembersByTournamentId(1L);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        verify(memberRepository, times(1)).findByTournamentId(1L);
    }

    @Test
    public void testFindMembersByTournamentStartDate() {
        LocalDate tournamentStartDate = LocalDate.of(2023, 5, 1);

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setStartDate(tournamentStartDate);

        Set<Tournament> tournaments = new HashSet<>();
        tournaments.add(tournament);
        member.setTournaments(tournaments);

        List<Member> members = Collections.singletonList(member);
        given(memberRepository.findByTournamentStartDate(tournamentStartDate)).willReturn(members);

        List<MemberDTO> result = memberService.findMembersByTournamentStartDate(tournamentStartDate);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        verify(memberRepository, times(1)).findByTournamentStartDate(tournamentStartDate);
    }
}
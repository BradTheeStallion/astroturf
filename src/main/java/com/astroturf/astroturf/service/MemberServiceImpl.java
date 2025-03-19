package com.astroturf.astroturf.service;

import com.astroturf.astroturf.dto.MemberDTO;
import com.astroturf.astroturf.exception.ResourceNotFoundException;
import com.astroturf.astroturf.model.Member;
import com.astroturf.astroturf.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public MemberDTO createMember(MemberDTO memberDTO) {
        Member member = mapToEntity(memberDTO);
        Member newMember = memberRepository.save(member);
        return mapToDTO(newMember);
    }

    @Override
    public MemberDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return mapToDTO(member);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        member.setName(memberDTO.getName());
        member.setAddress(memberDTO.getAddress());
        member.setEmail(memberDTO.getEmail());
        member.setPhoneNumber(memberDTO.getPhoneNumber());
        member.setMembershipStartDate(memberDTO.getMembershipStartDate());
        member.setMembershipDuration(memberDTO.getMembershipDuration());

        Member updatedMember = memberRepository.save(member);
        return mapToDTO(updatedMember);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        memberRepository.delete(member);
    }

    @Override
    public List<MemberDTO> findMembersByName(String name) {
        List<Member> members = memberRepository.findByNameContainingIgnoreCase(name);
        return members.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MemberDTO> findMembersByPhoneNumber(String phoneNumber) {
        List<Member> members = memberRepository.findByPhoneNumberContaining(phoneNumber);
        return members.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MemberDTO> findMembersByMembershipStartDate(LocalDate startDate) {
        List<Member> members = memberRepository.findByMembershipStartDate(startDate);
        return members.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MemberDTO> findMembersByMembershipDuration(Integer duration) {
        List<Member> members = memberRepository.findByMembershipDuration(duration);
        return members.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MemberDTO> findMembersByTournamentId(Long tournamentId) {
        List<Member> members = memberRepository.findByTournamentId(tournamentId);
        return members.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MemberDTO> findMembersByTournamentStartDate(LocalDate startDate) {
        List<Member> members = memberRepository.findByTournamentStartDate(startDate);
        return members.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private MemberDTO mapToDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setName(member.getName());
        memberDTO.setAddress(member.getAddress());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPhoneNumber(member.getPhoneNumber());
        memberDTO.setMembershipStartDate(member.getMembershipStartDate());
        memberDTO.setMembershipDuration(member.getMembershipDuration());
        return memberDTO;
    }

    private Member mapToEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setAddress(memberDTO.getAddress());
        member.setEmail(memberDTO.getEmail());
        member.setPhoneNumber(memberDTO.getPhoneNumber());
        member.setMembershipStartDate(memberDTO.getMembershipStartDate());
        member.setMembershipDuration(memberDTO.getMembershipDuration());
        return member;
    }
}
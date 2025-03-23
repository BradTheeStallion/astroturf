package com.astroturf.astroturf.controller;

import com.astroturf.astroturf.dto.MemberDTO;
import com.astroturf.astroturf.exception.ResourceNotFoundException;
import com.astroturf.astroturf.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    private MemberDTO memberDTO;
    private LocalDate membershipStartDate;

    @BeforeEach
    public void setup() {
        membershipStartDate = LocalDate.of(2023, 1, 1);

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
    public void testCreateMember() throws Exception {
        given(memberService.createMember(any(MemberDTO.class))).willReturn(memberDTO);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(memberDTO.getName())))
                .andExpect(jsonPath("$.email", is(memberDTO.getEmail())));

        verify(memberService, times(1)).createMember(any(MemberDTO.class));
    }

    @Test
    public void testGetMemberById() throws Exception {
        given(memberService.getMemberById(1L)).willReturn(memberDTO);

        mockMvc.perform(get("/api/members/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(memberDTO.getName())))
                .andExpect(jsonPath("$.email", is(memberDTO.getEmail())));

        verify(memberService, times(1)).getMemberById(1L);
    }

    @Test
    public void testGetMemberById_NotFound() throws Exception {
        given(memberService.getMemberById(99L)).willThrow(new ResourceNotFoundException("Member not found with id: 99"));

        mockMvc.perform(get("/api/members/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(memberService, times(1)).getMemberById(99L);
    }

    @Test
    public void testGetAllMembers() throws Exception {
        MemberDTO member2 = new MemberDTO();
        member2.setId(2L);
        member2.setName("Jane Smith");
        member2.setEmail("jane.smith@example.com");
        member2.setAddress("456 Oak St");
        member2.setPhoneNumber("555-5678");
        member2.setMembershipStartDate(LocalDate.of(2023, 2, 1));
        member2.setMembershipDuration(6);

        List<MemberDTO> allMembers = Arrays.asList(memberDTO, member2);

        given(memberService.getAllMembers()).willReturn(allMembers);

        mockMvc.perform(get("/api/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(memberDTO.getName())))
                .andExpect(jsonPath("$[1].name", is(member2.getName())));

        verify(memberService, times(1)).getAllMembers();
    }

    @Test
    public void testUpdateMember() throws Exception {
        MemberDTO updatedMemberDTO = new MemberDTO();
        updatedMemberDTO.setId(1L);
        updatedMemberDTO.setName("John Updated");
        updatedMemberDTO.setAddress("Updated Address");
        updatedMemberDTO.setEmail("john.updated@example.com");
        updatedMemberDTO.setPhoneNumber("555-9876");
        updatedMemberDTO.setMembershipStartDate(LocalDate.of(2023, 3, 1));
        updatedMemberDTO.setMembershipDuration(24);

        given(memberService.updateMember(eq(1L), any(MemberDTO.class))).willReturn(updatedMemberDTO);

        mockMvc.perform(put("/api/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMemberDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Updated")))
                .andExpect(jsonPath("$.email", is("john.updated@example.com")));

        verify(memberService, times(1)).updateMember(eq(1L), any(MemberDTO.class));
    }

    @Test
    public void testDeleteMember() throws Exception {
        doNothing().when(memberService).deleteMember(1L);

        mockMvc.perform(delete("/api/members/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(memberService, times(1)).deleteMember(1L);
    }

    @Test
    public void testGetMembersByName() throws Exception {
        List<MemberDTO> members = Collections.singletonList(memberDTO);
        given(memberService.findMembersByName("John")).willReturn(members);

        mockMvc.perform(get("/api/members/search/by-name")
                        .param("name", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(memberDTO.getName())));

        verify(memberService, times(1)).findMembersByName("John");
    }

    @Test
    public void testGetMembersByPhone() throws Exception {
        List<MemberDTO> members = Collections.singletonList(memberDTO);
        given(memberService.findMembersByPhoneNumber("555")).willReturn(members);

        mockMvc.perform(get("/api/members/search/by-phone")
                        .param("phoneNumber", "555")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].phoneNumber", is(memberDTO.getPhoneNumber())));

        verify(memberService, times(1)).findMembersByPhoneNumber("555");
    }

    @Test
    public void testGetMembersByMembershipStartDate() throws Exception {
        List<MemberDTO> members = Collections.singletonList(memberDTO);
        given(memberService.findMembersByMembershipStartDate(membershipStartDate)).willReturn(members);

        mockMvc.perform(get("/api/members/search/by-membership-start-date")
                        .param("startDate", "2023-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(memberDTO.getName())));

        verify(memberService, times(1)).findMembersByMembershipStartDate(membershipStartDate);
    }

    @Test
    public void testGetMembersByMembershipDuration() throws Exception {
        List<MemberDTO> members = Collections.singletonList(memberDTO);
        given(memberService.findMembersByMembershipDuration(12)).willReturn(members);

        mockMvc.perform(get("/api/members/search/by-membership-duration")
                        .param("duration", "12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].membershipDuration", is(12)));

        verify(memberService, times(1)).findMembersByMembershipDuration(12);
    }

    @Test
    public void testGetMembersByTournamentId() throws Exception {
        List<MemberDTO> members = Collections.singletonList(memberDTO);
        given(memberService.findMembersByTournamentId(1L)).willReturn(members);

        mockMvc.perform(get("/api/members/search/by-tournament")
                        .param("tournamentId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(memberDTO.getName())));

        verify(memberService, times(1)).findMembersByTournamentId(1L);
    }

    @Test
    public void testGetMembersByTournamentStartDate() throws Exception {
        LocalDate tournamentStartDate = LocalDate.of(2023, 5, 1);
        List<MemberDTO> members = Collections.singletonList(memberDTO);
        given(memberService.findMembersByTournamentStartDate(tournamentStartDate)).willReturn(members);

        mockMvc.perform(get("/api/members/search/by-tournament-start-date")
                        .param("startDate", "2023-05-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(memberDTO.getName())));

        verify(memberService, times(1)).findMembersByTournamentStartDate(tournamentStartDate);
    }

    @Test
    public void testCreateMember_ValidationError() throws Exception {
        MemberDTO invalidMember = new MemberDTO();

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidMember)))
                .andExpect(status().isBadRequest());

        verify(memberService, never()).createMember(any(MemberDTO.class));
    }
}
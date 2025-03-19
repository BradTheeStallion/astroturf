package com.astroturf.astroturf.repository;

import com.astroturf.astroturf.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByNameContainingIgnoreCase(String name);

    List<Member> findByPhoneNumberContaining(String phoneNumber);

    @Query("SELECT m FROM Member m WHERE m.membershipStartDate = :startDate")
    List<Member> findByMembershipStartDate(@Param("startDate") LocalDate startDate);

    @Query("SELECT m FROM Member m WHERE m.membershipDuration = :duration")
    List<Member> findByMembershipDuration(@Param("duration") Integer duration);

    @Query("SELECT m FROM Member m JOIN m.tournaments t WHERE t.id = :tournamentId")
    List<Member> findByTournamentId(@Param("tournamentId") Long tournamentId);

    @Query("SELECT m FROM Member m JOIN m.tournaments t WHERE t.startDate = :startDate")
    List<Member> findByTournamentStartDate(@Param("startDate") LocalDate startDate);
}
package com.astroturf.astroturf.repository;

import com.astroturf.astroturf.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    List<Tournament> findByStartDate(LocalDate startDate);

    List<Tournament> findByLocationContainingIgnoreCase(String location);

    @Query("SELECT t FROM Tournament t WHERE t.startDate BETWEEN :startDate AND :endDate")
    List<Tournament> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT t FROM Tournament t JOIN t.participants m WHERE m.id = :memberId")
    List<Tournament> findByMemberId(@Param("memberId") Long memberId);
}
package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.MeetHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetRep extends JpaRepository<MeetHistory,Long> {
}

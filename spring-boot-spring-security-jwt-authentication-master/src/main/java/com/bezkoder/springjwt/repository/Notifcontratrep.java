package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.contratNotif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Notifcontratrep extends JpaRepository<contratNotif,Long> {
}

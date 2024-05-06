package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Productowner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PownerRep extends JpaRepository<Productowner,Long> {
}

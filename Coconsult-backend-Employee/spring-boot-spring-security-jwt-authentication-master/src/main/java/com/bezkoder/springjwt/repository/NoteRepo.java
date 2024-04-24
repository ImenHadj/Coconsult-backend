package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note,Long> {
    @Query("SELECT n.critere, AVG(n.note) FROM Note n GROUP BY n.critere")
    List<Object[]> findAverageByCriteria();
}



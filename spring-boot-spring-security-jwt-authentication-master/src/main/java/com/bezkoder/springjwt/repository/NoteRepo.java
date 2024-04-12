package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepo extends JpaRepository<Note,Long> {


}

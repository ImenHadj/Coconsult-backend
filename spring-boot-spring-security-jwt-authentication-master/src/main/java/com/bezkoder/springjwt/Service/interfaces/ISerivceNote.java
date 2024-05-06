package com.bezkoder.springjwt.Service.interfaces;

import com.bezkoder.springjwt.models.Note;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface ISerivceNote {
    public void addNote(Note note,Long id);
    public void addPerfermance(Long id);
    public Note updateNote(Long id,Note updatedNote);
    public void deleteNote(Long id);
    public Note getNote(Long id);
    List<Note> retrieveAll();
    public Set<Note> getNotesByEmp(Long id);

    public ResponseEntity<?> getUserNameByIdUSer(Long id);

    public ResponseEntity<?> getUserNameByIdEmpl(Long id);
}

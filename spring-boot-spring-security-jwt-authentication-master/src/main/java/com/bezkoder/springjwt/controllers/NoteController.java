package com.bezkoder.springjwt.controllers;


import com.bezkoder.springjwt.Service.interfaces.ISerivceNote;
import com.bezkoder.springjwt.models.*;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/Note")
@CrossOrigin(origins = "http://localhost:4200")

public class NoteController {
    ISerivceNote iSerivceNote;

    @PostMapping("/addNote/{id}")
    public void addNote(@RequestBody Note p,@PathVariable("id") Long id){
        iSerivceNote.addNote(p,id);
    }
    @PostMapping("/addPerfermance/{id}")
    public void addPerfermance(@PathVariable("id") Long id){
        iSerivceNote.addPerfermance(id);
    }
    @PutMapping("/updateNote/{id}")
    public Note updateNote(@PathVariable("id") Long id,@RequestBody Note p){
        return iSerivceNote.updateNote(id,p);
    }
    @DeleteMapping("/deleteNote/{p}")
    public void deleteNote(@PathVariable("p") Long p) {
        iSerivceNote.deleteNote(p);
    }
    @GetMapping("/getNote/{id}")
    public Note getNote(@PathVariable("id") Long id){
        return iSerivceNote.getNote(id);
    }
    @GetMapping("/retrieveAll")
    public List<Note> retrieveAll(){
        return iSerivceNote.retrieveAll();
    }

    @GetMapping("/getNotes/{id}")
    public Set<Note> getCongesByEmp(@PathVariable("id") Long id){
        return iSerivceNote.getNotesByEmp(id);
    }

    @GetMapping("/getUsername/{id}")
    public ResponseEntity<?> getUsername(@PathVariable("id") Long id){
        return iSerivceNote.getUserNameByIdUSer(id);
    }

}

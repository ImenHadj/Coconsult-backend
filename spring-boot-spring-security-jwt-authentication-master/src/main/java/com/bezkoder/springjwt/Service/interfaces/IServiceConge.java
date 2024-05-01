package com.bezkoder.springjwt.Service.interfaces;

import com.bezkoder.springjwt.models.Conge;
import com.bezkoder.springjwt.models.CongeStatut;
import com.bezkoder.springjwt.models.CongeType;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IServiceConge {
    public ResponseEntity<?> saveConge(Conge conge, Long id);
    public ResponseEntity<?> updateConge(Long id,Conge absence);
    public void deleteConge(Long id);
    public Conge getConge(Long id);
    List<Conge> retrieveAll();
//    public List<Conge> searchCongesByStartingLetters(String StartingLetter);
    public Set<Conge> getCongesByEmp(Long id);
    public Map<CongeType, Integer> calculerNombreCongesParType();
    public List<Conge> searchCongesByStartingLetters(String startingLette);
    public List<Conge> filterByStatus(CongeStatut status);

    public  ResponseEntity<?> SendEmailConge(Long id, Conge conge);
}

package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Reclamation;

import java.util.List;

public interface IReclamationService {

    List<Reclamation> retrieveAllReclamations();
    Reclamation addReclamation(Reclamation r);
    Reclamation updateReclamation(Reclamation r);
    Reclamation retrieveReclamation(Long idRec);
    void removeReclamation(Long idRec);
}

package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Reclamation;
import com.bezkoder.springjwt.repository.ReclamationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ReclamationService implements IReclamationService{
    ReclamationRepository reclamationRepository ;

    @Override
    public List<Reclamation> retrieveAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation addReclamation(Reclamation r) {
        return reclamationRepository.save(r);
    }

    @Override
    public Reclamation updateReclamation(Reclamation r) {
        return reclamationRepository.save(r);
    }

    @Override
    public Reclamation retrieveReclamation(Long idRec) {
        return reclamationRepository.findById(idRec).orElse(null);
    }

    @Override
    public void removeReclamation(Long idRec) {
        log.debug("debugging");
        reclamationRepository.deleteById(idRec);

    }
}

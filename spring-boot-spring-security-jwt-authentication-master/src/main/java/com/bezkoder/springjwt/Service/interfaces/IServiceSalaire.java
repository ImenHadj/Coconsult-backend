package com.bezkoder.springjwt.Service.interfaces;

import com.bezkoder.springjwt.models.SalaireEmployee;
import com.bezkoder.springjwt.models.PosteEmployee;


import java.util.List;

public interface IServiceSalaire {
    public void addSalaire(SalaireEmployee absence, Long employeeId);
    public SalaireEmployee updateSalaire(SalaireEmployee absence,Long id);
    public void deleteSalaire(Long id);
    public SalaireEmployee getSalaire(Long id);
    List<SalaireEmployee> retrieveAll();
    public List<SalaireEmployee> findBySalaireBaseGreaterThan(Float minSalaire);

    public Float calculateAverageSalaryByPoste(PosteEmployee posteEmployee);

}

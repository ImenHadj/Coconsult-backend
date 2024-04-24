package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.models.ResourcesCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur,Long> {
    @Query("SELECT f FROM Fournisseur f WHERE f.typeFournisseur = :typeFournisseur  ORDER BY f.score DESC")
    List<Fournisseur> findFournisseurByTypeFournisseurOrderByScoreDesc(@Param("typeFournisseur") ResourcesCategorie typeFournisseur);


    @Query("SELECT f FROM Fournisseur f JOIN f.stocks s GROUP BY f ORDER BY COUNT(s) DESC")
    List<Fournisseur> findTopThreeFournisseursWithStocks();
}


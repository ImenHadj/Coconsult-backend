package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Fournisseur;

import java.util.List;

public interface IFournisseurService {

    List<Fournisseur> retrieveAllFournisseurs();
    Fournisseur addFournisseur(Fournisseur f);
    Fournisseur updateFournisseur(Fournisseur f);
    Fournisseur retrieveFournisseur(Long idF);
    void removeFournisseur(Long idF);
    public int calculerScoreFournisseur(Fournisseur fournisseur);

    public List<Fournisseur> getTopThreeFournisseursWithStocks();
    public int getNombreStocksFournisseur(Long fournisseurID);
}

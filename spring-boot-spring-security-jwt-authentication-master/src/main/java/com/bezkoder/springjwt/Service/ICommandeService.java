package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Commande;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.models.ResourcesCategorie;

import java.util.List;

public interface ICommandeService {

    List<Commande> retrieveAllCommandes();
    Commande addCommande(Commande c);
    Commande updateCommande(Commande c);
    Commande retrieveCommande(Long idCom);
    void removeCommande(Long idCom);
    public void marquerCommandeCommeArrivee(Long commandeId);
    public List<Fournisseur> retrieveFournisseurByCategorie(ResourcesCategorie resourcesCategorie);
}

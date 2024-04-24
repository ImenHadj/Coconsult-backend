package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.DefautsRange;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.models.Stock;
import com.bezkoder.springjwt.repository.FournisseurRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class FournisseurService implements IFournisseurService {

    FournisseurRepository fournisseurRepository ;

    @Override
    public List<Fournisseur> retrieveAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    @Override
    public Fournisseur addFournisseur(Fournisseur f) {
        f.setScore(10);
        return fournisseurRepository.save(f);
    }

    @Override
    public Fournisseur updateFournisseur(Fournisseur f) {
        return fournisseurRepository.save(f);
    }

    @Override
    public Fournisseur retrieveFournisseur(Long idF) {
        return fournisseurRepository.findById(idF).orElse(null);
    }

    @Override
    public void removeFournisseur(Long idF) {
        log.debug("debugging");
        fournisseurRepository.deleteById(idF);

    }


    private static final int POINTS_PAR_STOCK = 7;
    private static final Map<String, Integer> POINTS_PAR_QUALITE = Map.of(
            "excellent", 20,
            "good", 14,
            "average", 10,
            "below average", 6,
            "mediocre", 2
    );
    private static final Map<DefautsRange, Integer> POINTS_PAR_DEFAUTS = Map.of(
            DefautsRange.LOW, 0,
            DefautsRange.MEDIUM, 5,
            DefautsRange.HIGH, 8
    );
    private static final float POINTS_PAR_TEMPS_ARRIVEE = 0.5F;

    @Override
    public int calculerScoreFournisseur(Fournisseur fournisseur) {
        int score = 0;


        int nombreStocks = fournisseur.getStocks().size();
        score += nombreStocks * POINTS_PAR_STOCK;


        for (Stock stock : fournisseur.getStocks()) {
            score += POINTS_PAR_QUALITE.getOrDefault(stock.getQuality().toLowerCase(), 0);
        }


        for (Stock stock : fournisseur.getStocks()) {
            score -= POINTS_PAR_DEFAUTS.getOrDefault(getDefautsRange(stock.getPourcentageDefauts()), 0);
        }


        for (Stock stock : fournisseur.getStocks()) {
            long joursArrivee = joursEntre(stock.getPurchaseDate(), stock.getEntryDate());
            score -= (int) (POINTS_PAR_TEMPS_ARRIVEE * (int) joursArrivee);
        }

        return score;
    }

    private DefautsRange getDefautsRange(int pourcentageDefauts) {
        if (pourcentageDefauts >= 0 && pourcentageDefauts <= 10) {
            return DefautsRange.LOW;
        } else if (pourcentageDefauts > 10 && pourcentageDefauts <= 20) {
            return DefautsRange.MEDIUM;
        } else {
            return DefautsRange.HIGH;
        }
    }

    private long joursEntre(Date dateDebut, Date dateFin) {
        long differenceEnMillis = dateFin.getTime() - dateDebut.getTime();
        return TimeUnit.DAYS.convert(differenceEnMillis, TimeUnit.MILLISECONDS);
    }


    @Override
    public List<Fournisseur> getTopThreeFournisseursWithStocks() {
        List<Fournisseur> fournisseurs = fournisseurRepository.findTopThreeFournisseursWithStocks();
        // Limiter la liste à trois éléments si elle contient plus de trois éléments
        return fournisseurs.subList(0, Math.min(fournisseurs.size(), 3));
    }

    @Override
    public int getNombreStocksFournisseur(Long fournisseurID) {
        // Récupérer le fournisseur par son ID
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurID).orElse(null);

        // Vérifier si le fournisseur existe
        if (fournisseur != null) {
            // Récupérer la liste des stocks associés au fournisseur
            List<Stock> stocks = fournisseur.getStocks();
            // Retourner le nombre de stocks associés
            return stocks.size();
        } else {
            // Si le fournisseur n'existe pas, retourner -1 pour indiquer une erreur
            return -1;
        }
    }
}

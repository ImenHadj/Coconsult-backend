package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.notifdto.depassagefacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FactureRep extends JpaRepository<Facture,Long> {

    @Query("SELECT f FROM Facture f WHERE f.client.idClient = :idclient AND f.contract.idContract = :idcontrat")
    List<Facture> findAllFactures(@Param("idclient") Long idclient, @Param("idcontrat") Long idcontrat);
/*notif*/
@Query("SELECT DISTINCT new com.bezkoder.springjwt.notifdto.depassagefacture(c, f.due_date) FROM Client c " +
        "JOIN FETCH c.factures f " +
        "WHERE f.due_date < :today")
List<depassagefacture> notification(@Param("today") LocalDateTime today);

}

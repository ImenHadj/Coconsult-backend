package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Paiment;
import com.bezkoder.springjwt.notifdto.MonthlyPaymentDTO;
import com.bezkoder.springjwt.notifdto.paymentpercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaimentRep extends JpaRepository<Paiment,Long> {



    @Query("SELECT new com.bezkoder.springjwt.notifdto.paymentpercentage(p.typepaiment, count(p)) FROM Paiment p GROUP BY p.typepaiment")
    List<paymentpercentage> getPaymentTypePercentages();

    @Query("SELECT new com.bezkoder.springjwt.notifdto.MonthlyPaymentDTO(MONTH(p.payment_date), YEAR(p.payment_date), SUM(p.amount)) " +
            "FROM Paiment p " +
            "GROUP BY MONTH(p.payment_date), YEAR(p.payment_date)")
    List<MonthlyPaymentDTO> getTotalPaymentsByMonth();

}

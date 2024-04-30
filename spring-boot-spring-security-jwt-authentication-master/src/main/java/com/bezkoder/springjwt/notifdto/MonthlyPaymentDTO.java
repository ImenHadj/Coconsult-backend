package com.bezkoder.springjwt.notifdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyPaymentDTO implements Serializable {
    private int month;
    private int year;
    private BigDecimal totalPayment;
}

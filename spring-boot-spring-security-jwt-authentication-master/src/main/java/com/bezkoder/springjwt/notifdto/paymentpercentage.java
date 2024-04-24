package com.bezkoder.springjwt.notifdto;

import com.bezkoder.springjwt.models.Typepaiment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class paymentpercentage implements Serializable {
    @Enumerated(EnumType.STRING)
    Typepaiment  paimenttype;
    public double percentage;
}

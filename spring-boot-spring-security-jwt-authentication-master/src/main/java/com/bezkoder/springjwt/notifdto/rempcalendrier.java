package com.bezkoder.springjwt.notifdto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class rempcalendrier implements Serializable {

    public String nom;
    public String prenom;
    public LocalDateTime dueDate;
}

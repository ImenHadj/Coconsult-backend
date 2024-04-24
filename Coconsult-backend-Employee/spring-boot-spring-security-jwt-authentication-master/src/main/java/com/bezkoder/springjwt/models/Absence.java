package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Absence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_absence ;
    private String motif ;
    private LocalDate date;
    private boolean validee;

    @ManyToOne(cascade = CascadeType.ALL)

    Employee emp;
    @OneToOne(cascade = CascadeType.ALL)

    Image image;

}

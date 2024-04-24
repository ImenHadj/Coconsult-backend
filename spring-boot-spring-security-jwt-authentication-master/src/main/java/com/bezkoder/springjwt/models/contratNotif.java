package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class contratNotif implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idmail")

    private Long idmail; // Clé primaire
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime maildate;
    private Boolean notified;
    private String clientemail;
    private String clientname;

    @ManyToOne
    Contract contract;

}

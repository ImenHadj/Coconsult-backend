package com.bezkoder.springjwt.notifdto;

import  com.bezkoder.springjwt.models.Client;
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
public class depassagefacture implements Serializable {

    public  Client client;
    public  LocalDateTime dueDate;
}

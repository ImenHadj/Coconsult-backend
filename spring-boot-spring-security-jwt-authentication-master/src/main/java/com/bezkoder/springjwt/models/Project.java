package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectid;
    private String projectname;
    private String projectdescription;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    @Enumerated(EnumType.STRING)
    private ProjectType type;
    private double budget;
    private double cost;
    private double expectedRevenue;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @OneToOne(cascade = CascadeType.ALL)
    private Team team;


    @OneToMany(mappedBy ="project",  cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<Task> tasks;
    @JsonIgnore
    @OneToMany(mappedBy ="projectt",  cascade = CascadeType.ALL)
    List<Consultant>  consultants;
    @JsonIgnore
    @ManyToMany
    List<Resources> resources;
    @ManyToOne
    User user ;
}

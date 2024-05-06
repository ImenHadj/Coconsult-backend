package com.bezkoder.springjwt.DTO;

import com.bezkoder.springjwt.models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectDTO {
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
    private Team team;

    public static ProjectDTO toDto(Project Entity){
        return ProjectDTO.builder()
                .projectid(Entity.getProjectid())
                .projectname(Entity.getProjectname())
                .projectdescription(Entity.getProjectdescription())
                .startDate(Entity.getStartDate())
                .endDate(Entity.getEndDate())
                .status(Entity.getStatus())
                .type(Entity.getType())
                .budget(Entity.getBudget())
                .cost(Entity.getCost())
                .expectedRevenue(Entity.getExpectedRevenue())
                .priority(Entity.getPriority())
                .team(Entity.getTeam())
                .build();
    }
    public static Project toEntity(ProjectDTO Entity){
        return Project.builder()
                .projectid(Entity.getProjectid())
                .projectname(Entity.getProjectname())
                .projectdescription(Entity.getProjectdescription())
                .startDate(Entity.getStartDate())
                .endDate(Entity.getEndDate())
                .status(Entity.getStatus())
                .type(Entity.getType())
                .budget(Entity.getBudget())
                .cost(Entity.getCost())
                .expectedRevenue(Entity.getExpectedRevenue())
                .priority(Entity.getPriority())
                .team(Entity.getTeam())
                .build();
    }
}

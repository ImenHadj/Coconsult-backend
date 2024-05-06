package com.bezkoder.springjwt.DTO;

import com.bezkoder.springjwt.models.Employee;
import com.bezkoder.springjwt.models.Project;
import com.bezkoder.springjwt.models.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TeamDTO {
    private Long team_id;
    private String team_name;
    private boolean availability;
    private double nbteam ;
    private Project project;
    public static TeamDTO toDto(Team Entity){
        return TeamDTO.builder()
                .team_id(Entity.getTeam_id())
                .team_name(Entity.getTeam_name())
                .nbteam(Entity.getNbteam())
                .project(Entity.getProject())
                .build();
    }
    public static Team toEntity(TeamDTO Entity){
        return Team.builder()
                .team_id(Entity.getTeam_id())
                .team_name(Entity.getTeam_name())
                .nbteam(Entity.getNbteam())
                .project(Entity.getProject())
                .build();
    }
}

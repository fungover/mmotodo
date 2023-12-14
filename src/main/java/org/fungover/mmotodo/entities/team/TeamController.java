package org.fungover.mmotodo.entities.team;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @QueryMapping
    public Team teamById(@Argument int id) {
        return teamService.getTeamById(id);
    }

    @QueryMapping
    public List<Team> allTeams() {
        return teamService.getAllTeams();
    }


    @MutationMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Team addTeam(@Valid @Argument TeamDto team) {
        return teamService.createTeam(team);
    }

    @MutationMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void  addUserToTeam(@Valid @Argument int teamId, int userId) {
        teamService.addUserToTeam(teamId,userId);
    }

    @MutationMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTeam(@Argument int id) {
        teamService.deleteTeam(id);
        return "team successfully deleted";
    }

    @MutationMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String removeUserFromTeam(@Argument int teamId, int userId) {
        teamService.removeUserFromTeam(teamId,userId);
        return "user successfully removed from team";
    }



    @MutationMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public boolean updateTeam(@Argument @Valid TeamDto team){
        return teamService.updateTeam(team);
    }
}

package org.fungover.mmotodo.team;

import jakarta.validation.Valid;
import org.fungover.mmotodo.team.Team;
import org.fungover.mmotodo.team.TeamDto;
import org.fungover.mmotodo.team.TeamService;
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
    public Team createTeam(@Valid @Argument TeamDto team) {
        return teamService.createTeam(team);
    }

    @MutationMapping
    public String addUserToTeam(@Valid @Argument int teamId, @Argument int userId) {
        teamService.addUserToTeam(teamId, userId);
        return "user with id " + userId + " successfully added to team";
    }

    @MutationMapping
    public String addTaskToTeam(@Valid @Argument int teamId, @Argument int taskId) {
        teamService.addTaskToTeam(teamId, taskId);
        return "task with id " + taskId + " successfully added to team";
    }

    @MutationMapping
    public String deleteTeam(@Argument int id) {
        teamService.deleteTeam(id);
        return "team successfully deleted";
    }

    @MutationMapping
    public String removeUserFromTeam(@Argument int teamId, @Argument int userId) {
        teamService.removeUserFromTeam(teamId, userId);
        return "user with id " + userId + " successfully removed from team";
    }

    @MutationMapping
    public String removeTaskFromTeam(@Argument int teamId, @Argument int taskId) {
        teamService.removeTaskFromTeam(teamId, taskId);
        return "task with id " + taskId + " successfully removed from team";
    }


    @MutationMapping()
    public Team updateTeam(@Argument @Valid TeamDto team) {
        return teamService.updateTeam(team);
    }
}

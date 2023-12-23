package org.fungover.mmotodo.team;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.fungover.mmotodo.exception.TeamNotFoundException;
import org.fungover.mmotodo.task.Task;
import org.fungover.mmotodo.task.TaskRepository;
import org.fungover.mmotodo.user.User;
import org.fungover.mmotodo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TeamService {
    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, UserRepository userRepository, TaskRepository taskRepository) {

        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    //get all teams
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(int teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
    }

    @Transactional
    public String deleteTeam(int teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        teamRepository.delete(team);
        return "Team with id:" + teamId + " deleted";
    }


    @Transactional
    public Team createTeam(@Valid TeamDto teamCreate) {
        // Check if the team with the same name already exists
        if (teamRepository.existsByName(teamCreate.name())) {
            throw new RuntimeException("Team already exists");
        }

        Team team = new Team();
        team.setName(teamCreate.name());
        return teamRepository.save(team);

    }

    @Transactional
    public Team updateTeam(TeamUpdateDto teamUpdate) {

        Team team = teamRepository.findById(teamUpdate.id()).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        team.setName(teamUpdate.name());
        return teamRepository.save(team);

    }

    @Transactional
    public String addUserToTeam(int teamId, int userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        if (team.getUsers().contains(user)) {
            throw new RuntimeException("User already present in team");
        }
        team.addUser(user);
        teamRepository.save(team);
        return"user added";

    }

    @Transactional
    public String removeUserFromTeam(int teamId, int userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team  not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
        if (!team.getUsers().contains(user)) {
            throw new RuntimeException("User not present in team: " + team.getName());
        }
        team.removeUser(user);
        teamRepository.save(team);
        return "user removed";

    }

    @Transactional
    public String addTaskToTeam(int teamId, int taskId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if (team.getTasks().contains(task)) {
            throw new RuntimeException("This task has  already been assigned to this team");
        }
        team.addTask(task);
        teamRepository.save(team);
        return "task added";

    }

    @Transactional
    public String removeTaskFromTeam(int teamId, int taskId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team  not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if (!team.getTasks().contains(task)) {
            throw new RuntimeException("Task is not assigned to  team: " + team.getName());
        }
        team.removeTask(task);
        teamRepository.save(team);
        return "task removed";


    }


}



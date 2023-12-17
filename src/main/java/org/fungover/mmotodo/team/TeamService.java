package org.fungover.mmotodo.team;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.fungover.mmotodo.exception.TaskNotFoundException;
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
        this.taskRepository=taskRepository;
    }

    //get all teams
    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }
 public Team getTeamById(int teamId){
        return teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
 }

    @Transactional
    public String deleteTeam(int teamId){
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
       teamRepository.delete(team);
     return "Team with id:" + teamId + " deleted";
 }


    @Transactional
    public Team createTeam( @Valid TeamDto teamCreate) {
        try {

            Team team = new Team();
            team.setName(teamCreate.name());
            team.setUsers(new ArrayList<>());
            team.setTasks(new ArrayList<>());
            return teamRepository.save(team);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create team: " + e.getMessage());
        }
    }

   @Transactional
   public Team updateTeam ( @Valid TeamDto teamUpdate){
        Team team = teamRepository.findById(teamUpdate.id()).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        team.setName(teamUpdate.name());
         return teamRepository.save(team);

    }

    @Transactional
    public void addUserToTeam(int teamId, int userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        team.addUser(user);
        teamRepository.save(team);
    }

    @Transactional
    public void removeUserFromTeam(int teamId, int userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team  not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
        team.removeUser(user);
        teamRepository.save(team);
    }

    @Transactional
    public void addTaskToTeam(int teamId, int taskId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        team.addTask(task);
        teamRepository.save(team);
    }

    @Transactional
    public void removeTaskFromTeam(int teamId, int taskId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team  not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        team.removeTask(task);
        teamRepository.save(team);
    }


}



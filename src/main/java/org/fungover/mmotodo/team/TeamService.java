package org.fungover.mmotodo.team;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
        return teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
 }

    @Transactional
    public String deleteTeam(int teamId){
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
       teamRepository.delete(team);
     return "Team with id:" + teamId + " deleted";
 }


 public Team createTeam (TeamDto teamCreate){
        Team team = new Team();
        team.setName(teamCreate.name());
       return teamRepository.save(team);
 }


    public Boolean updateTeam ( TeamDto teamUpdate){
        Team team = teamRepository.findById(teamUpdate.id()).orElseThrow(() -> new RuntimeException("Team not found"));
        team.setName(teamUpdate.name());
         teamRepository.save(team);
         return true;
    }

    public void addUserToTeam(int teamId, int userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with ID " + teamId + " not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
        team.getUsers().add(user);
        teamRepository.save(team);
    }

    public void removeUserFromTeam(int teamId, int userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with ID " + teamId + " not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
        team.getUsers().remove(user);
        teamRepository.save(team);
    }
}


package org.fungover.mmotodo.entities.team;

import jakarta.transaction.Transactional;
import org.fungover.mmotodo.entities.task.Task;
import org.fungover.mmotodo.entities.task.TaskRepository;
import org.fungover.mmotodo.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    // I will inject it when I get the userRepository
    private final UserRepository userRepository;
    // I will inject it when I get the taskRepository
    private final TaskRepository taskRepository;
    @Autowired
    public TeamService(TeamRepository teamRepository,UserRepository userRepository,TaskRepository taskRepository) {

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

 @Transactional
 public Team createTeam (TeamDto teamCreate){
        Team team = new Team();
        team.setName(teamCreate.name());
       List<User> users = userRepository.findAllByIdIn(teamCreate.userIds());
        team.setUsers(Optional.ofNullable(users).orElseThrow(() -> new IllegalStateException("No users found for the specified role.")));
        List<Task> tasks = taskRepository.findAllByIdIn(teamCreate.taskIds());
     team.setTasks(Optional.ofNullable(tasks).orElseThrow(() -> new IllegalStateException("No tasks found for the specified category.")));
      return teamRepository.save(team);
 }

    @Transactional
    public Boolean updateTeam ( TeamDto teamUpdate){
        Team team = teamRepository.findById(teamUpdate.id()).orElseThrow(() -> new RuntimeException("Team not found"));
        team.setName(teamUpdate.name());
        List<User> users = userRepository.findAllByIdIn(teamUpdate.userIds());
        team.setUsers(users);
        List<Task> tasks = taskRepository.findAllByIdIn(teamUpdate.taskIds());
        team.setTasks(tasks);
         teamRepository.save(team);
         return true;

    }
}


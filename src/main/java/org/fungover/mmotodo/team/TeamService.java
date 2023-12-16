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

            if(teamCreate.users()!=null){
                List<User> users = userRepository.findAllById(teamCreate.users());
                System.out.println("users: " + users);
                team.setUsers(users);
            }


           if(teamCreate.tasks()!=null){
               List<Task> tasks = taskRepository.findAllById(teamCreate.tasks());
               System.out.println("tasks: " + tasks);
               team.setTasks(tasks);
           }

            System.out.println("team" + team);
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
       if(teamUpdate.users()!=null){
           List<User> users = userRepository.findAllById(teamUpdate.users());
           team.setUsers(users);
       }
       if(teamUpdate.tasks()!=null){
           List<Task> tasks = taskRepository.findAllById(teamUpdate.tasks());
           team.setTasks(tasks);
       }

         return teamRepository.save(team);

    }

    @Transactional
    public void addUserToTeam(int teamId, int userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        team.getUsers().add(user);
        teamRepository.save(team);
    }

    @Transactional
    public void removeUserFromTeam(int teamId, int userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team  not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
        team.getUsers().remove(user);
        teamRepository.save(team);
    }
}


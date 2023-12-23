package org.fungover.mmotodo.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fungover.mmotodo.task.GraphQlConfig;
import org.fungover.mmotodo.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@GraphQlTest(TeamController.class)
@Import(value = {GraphQlConfig.class})
public class TeamControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private TeamService teamService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Team> teams;
    private static int counter;

    @BeforeEach
    void setUp() {
        counter = 1;
        teams = new ArrayList<>();
        teams.add(createTeam("Engineering Team"));
        teams.add(createTeam("Architectural Team"));
    }

    @Test
    void shouldRespondWithAllTeamNames() throws Exception {
        Mockito.when(teamService.getAllTeams()).thenReturn(teams);

        String query = "query{allTeams{name}}";

        var expectedList = teams.stream().map(team -> new Object() {
            public final String name = team.getName();

        }).toList();

        String expected = objectMapper.writeValueAsString(expectedList);
        graphQlTester.document(query)
                .execute()
                .path("allTeams")
                .matchesJson(expected);
    }

    @Test
    void shouldRespondWithTeamData() throws Exception {

        Team team = createTeam("manufacturing Team");
        Mockito.when(teamService.getTeamById(1)).thenReturn(team);
        String query = "{ teamById(id: 1) { id name created updated users { id firstName lastName role created updated tasks { id title } } tasks { id title description created updated timeEstimation dueDate status  } }}";


        String expected = objectMapper.writeValueAsString(team);
        graphQlTester.document(query)
                .execute()
                .path("teamById")
                .matchesJson(expected);
    }

    @Test
    void shouldAddNewTeam() throws Exception {

        TeamDto teamDto = new TeamDto(1, "developer team");
        Team team = new Team();
        team.setId(teamDto.id());
        team.setName(teamDto.name());
        Mockito.when(teamService.createTeam(teamDto)).thenReturn(team);
        String mutation = "mutation { createTeam(team: { name: \"developer team\" }) { id name  } }";
        System.out.println("mutation: " +  teamDto);
        String expected = objectMapper.writeValueAsString(team);
        System.out.println("team: " + expected);
        graphQlTester.document(mutation)
                .execute()
                .path("createTeam")
                .matchesJson(expected);
    }

    @Test
    void shouldAddNewUserToTeam() throws Exception{

        Mockito.when(teamService.addUserToTeam(1,1))
                .thenReturn("user with id 1 successfully added to team");

        String query = "mutation { addUserToTeam(teamId: 1, userId: 1) }";
        String expected = objectMapper.writeValueAsString("user with id 1 successfully added to team");
         graphQlTester.document(query)
                .execute()
                .path("addUserToTeam")
                 .matchesJson(expected);

    }

    @Test
    void shouldAddNewTaskToTeam() throws Exception{

        Mockito.when(teamService.addTaskToTeam(1,1))
                .thenReturn("task with id 1 successfully added to team");

        String query = "mutation { addTaskToTeam(teamId: 1, taskId: 1) }";
        String expected = objectMapper.writeValueAsString("task with id 1 successfully added to team");
        graphQlTester.document(query)
                .execute()
                .path("addTaskToTeam")
                .matchesJson(expected);

    }

    @Test
    void shouldRemoveUserFromTeam() throws Exception{

        Mockito.when(teamService.removeUserFromTeam(1,1))
                .thenReturn("user with id 1 successfully removed from team");

        String query = "mutation { removeUserFromTeam(teamId: 1, userId: 1) }";
        String expected = objectMapper.writeValueAsString("user with id 1 successfully removed from team");
        graphQlTester.document(query)
                .execute()
                .path("removeUserFromTeam")
                .matchesJson(expected);

    }

    @Test
    void shouldRemoveTaskToTeam() throws Exception{

        Mockito.when(teamService.removeTaskFromTeam(1,1))
                .thenReturn("task with id 1 successfully removed from team");

        String query = "mutation { removeTaskFromTeam(teamId: 1, taskId: 1) }";
        String expected = objectMapper.writeValueAsString("task with id 1 successfully removed from team");
        graphQlTester.document(query)
                .execute()
                .path("removeTaskFromTeam")
                .matchesJson(expected);

    }

    private Team createTeam(String name) {
        Team team = new Team();
        team.setId(1);
        team.setName(name);
        return team;
    }


}

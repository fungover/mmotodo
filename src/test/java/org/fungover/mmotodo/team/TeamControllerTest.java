package org.fungover.mmotodo.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fungover.mmotodo.task.GraphQlConfig;
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

    @BeforeEach
    void setUp() {
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
        TeamCreateDto teamDto = new TeamCreateDto("developer team");
        Team team = new Team();
        team.setName(teamDto.name());
        Mockito.when(teamService.createTeam(teamDto)).thenReturn(team);
        String mutation = "mutation {\n" +
                "  createTeam(team: {\n" +
                "    name: \"developer team\"\n" +
                "  }) {\n" +
                "    id\n" +
                "    name\n" +
                "    created\n" +
                "    updated\n" +
                "    users {\n" +
                "      id\n" +
                "      firstName\n" +
                "      lastName\n" +
                "      role\n" +
                "      created\n" +
                "      updated\n" +
                "    }\n" +
                "    tasks {\n" +
                "      id\n" +
                "      title\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String expected = objectMapper.writeValueAsString(team);
        graphQlTester.document(mutation)
                .execute()
                .path("createTeam")
                .matchesJson(expected);
    }

    @Test
    void shouldUpdateANewTeam() throws Exception {
        // Create a TeamUpdateDto with an id and updated name
        TeamUpdateDto teamDto = new TeamUpdateDto(1, "update team");

        // Create a Team with the same id and updated name
        Team team = new Team();
        team.setId(teamDto.id());
        team.setName(teamDto.name());
        // Ensure other fields are properly set during the update

        // Mock the service response
        Mockito.when(teamService.updateTeam(teamDto)).thenReturn(team);

        // GraphQL mutation
        String mutation = "mutation {\n" +
                "  updateTeam(team: {\n" +
                "    id: 1,\n" +
                "    name: \"update team\"\n" +
                "  }) {\n" +
                "    id\n" +
                "    name\n" +
                "    created\n" +
                "    updated\n" +
                "    users {\n" +
                "      id\n" +
                "      firstName\n" +
                "      lastName\n" +
                "      role\n" +
                "      created\n" +
                "      updated\n" +
                "    }\n" +
                "    tasks {\n" +
                "      id\n" +
                "      title\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String expected = objectMapper.writeValueAsString(team);
        System.out.println("Expected: " + expected);

        graphQlTester.document(mutation)
                .execute()
                .path("updateTeam")
                .matchesJson(expected);
    }


    @Test
    void shouldAddNewUserToTeam() throws Exception {

        Mockito.when(teamService.addUserToTeam(1, 1))
                .thenReturn("user with id 1 successfully added to team");

        String query = "mutation { addUserToTeam(teamId: 1, userId: 1) }";
        String expected = objectMapper.writeValueAsString("user with id 1 successfully added to team");
        graphQlTester.document(query)
                .execute()
                .path("addUserToTeam")
                .matchesJson(expected);

    }

    @Test
    void shouldAddNewTaskToTeam() throws Exception {
        Mockito.when(teamService.addTaskToTeam(1, 1))
                .thenReturn("task with id 1 successfully added to team");

        String query = "mutation { addTaskToTeam(teamId: 1, taskId: 1) }";
        String expected = objectMapper.writeValueAsString("task with id 1 successfully added to team");
        graphQlTester.document(query)
                .execute()
                .path("addTaskToTeam")
                .matchesJson(expected);
    }

    @Test
    void shouldRemoveUserFromTeam() throws Exception {
        Mockito.when(teamService.removeUserFromTeam(1, 1))
                .thenReturn("user with id 1 successfully removed from team");

        String query = "mutation { removeUserFromTeam(teamId: 1, userId: 1) }";
        String expected = objectMapper.writeValueAsString("user with id 1 successfully removed from team");
        graphQlTester.document(query)
                .execute()
                .path("removeUserFromTeam")
                .matchesJson(expected);
    }

    @Test
    void shouldRemoveTaskToTeam() throws Exception {
        Mockito.when(teamService.removeTaskFromTeam(1, 1))
                .thenReturn("task with id 1 successfully removed from team");

        String query = "mutation { removeTaskFromTeam(teamId: 1, taskId: 1) }";
        String expected = objectMapper.writeValueAsString("task with id 1 successfully removed from team");
        graphQlTester.document(query)
                .execute()
                .path("removeTaskFromTeam")
                .matchesJson(expected);
    }

    @Test
    void shouldDeleteTeam() throws Exception {
        Mockito.when(teamService.deleteTeam(1)).thenReturn("Team with id 1 deleted");
        String query = "mutation { deleteTeam(id: 1) }";
        String expected = objectMapper.writeValueAsString("Team with id 1 deleted");
        graphQlTester.document(query)
                .execute()
                .path("deleteTeam")
                .matchesJson(expected);
    }

    private Team createTeam(String name) {
        Team team = new Team();
        team.setId(1);
        team.setName(name);
        return team;
    }
}

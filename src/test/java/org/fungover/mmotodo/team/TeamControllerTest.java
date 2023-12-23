package org.fungover.mmotodo.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQL;
import net.minidev.json.JSONUtil;
import org.fungover.mmotodo.task.GraphQlConfig;
import org.fungover.mmotodo.task.Task;
import org.fungover.mmotodo.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

    private Team createTeam(String name) {
        Team team = new Team();
        team.setId(1);
        team.setName(name);
        return team;
    }


}

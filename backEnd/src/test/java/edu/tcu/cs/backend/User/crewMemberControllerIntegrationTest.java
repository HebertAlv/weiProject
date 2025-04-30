package edu.tcu.cs.backend.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.backend.System.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Crew Member Controller Integration Test")
@Tag("Integration")
public class crewMemberControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testFindAllUsers() throws Exception {
        this.mockMvc.perform(get(this.baseUrl + "/crewMember").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testFindUserById() throws Exception {
        int userId = 1;
        this.mockMvc.perform(get(this.baseUrl + "/crewMember/" + userId).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data.id").value(userId));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testAddUser() throws Exception {
        // Arrange
        crewMember user = new crewMember();
        user.setId(4);
        user.setFirstName("Jack");
        user.setLastName("Daniels");
        user.setEmail("jack@daniels.com");
        user.setPhoneNumber("1234567890");
        user.setRole("USER");
        user.setPassword("password");
        user.setPositions(List.of("DIRECTOR", "PRODUCER"));

        String json = this.objectMapper.writeValueAsString(user);

        // Act & Assert - POST
        this.mockMvc.perform(post(this.baseUrl + "/crewMember")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Create Success"))
                .andExpect(jsonPath("$.data.id").value(4))
                .andExpect(jsonPath("$.data.firstName").value("Jack"))
                .andExpect(jsonPath("$.data.lastName").value("Daniels"));

        // Act & Assert - GET
        this.mockMvc.perform(get(this.baseUrl + "/crewMember/4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data.id").value(4))
                .andExpect(jsonPath("$.data.firstName").value("Jack"))
                .andExpect(jsonPath("$.data.lastName").value("Daniels"));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testDeleteUserById() throws Exception {
        int userId = 3;
        this.mockMvc.perform(delete(this.baseUrl + "/crewMember/" + userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testInviteCrewMembers() throws Exception {
        List<Integer> crewMemberIds = List.of(1, 2);

        String json = this.objectMapper.writeValueAsString(crewMemberIds);

        this.mockMvc.perform(post(this.baseUrl + "/invite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Invitations generated successfully"))
                .andExpect(jsonPath("$.data").isArray());
    }
}
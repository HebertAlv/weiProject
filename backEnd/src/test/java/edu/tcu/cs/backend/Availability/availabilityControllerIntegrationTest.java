package edu.tcu.cs.backend.Availability;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Availability Controller Integration Test")
@Tag("Integration")
public class availabilityControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testGetAvailability() throws Exception {
        int userId = 1;
        int scheduleId = 1;

        this.mockMvc.perform(get(this.baseUrl + "/" + userId + "/schedule/" + scheduleId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.gameId").value(scheduleId))
                .andExpect(jsonPath("$.availability").value(true));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testUpdateAvailability() throws Exception {
        availability updatedAvailability = new availability();
        updatedAvailability.setUserId(1);
        updatedAvailability.setGameId(1);
        updatedAvailability.setAvailability(false);
        updatedAvailability.setComments("Unavailable for the game");

        String json = this.objectMapper.writeValueAsString(updatedAvailability);

        this.mockMvc.perform(put(this.baseUrl + "/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.gameId").value(1))
                .andExpect(jsonPath("$.data.availability").value(false))
                .andExpect(jsonPath("$.data.comments").value("Available for the game"));
    }
}
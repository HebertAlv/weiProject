package edu.tcu.cs.backend.GameSchedule;

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
@DisplayName("Game Schedule Controller Integration Test")
@Tag("Integration")
public class gameScheduleControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testFindAllGameSchedules() throws Exception {
        this.mockMvc.perform(get(this.baseUrl + "/gameSchedule/games").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testAddGameSchedule() throws Exception {
        gameSchedule newSchedule = new gameSchedule();
        newSchedule.setId(3);
        newSchedule.setSport("Tennis");
        newSchedule.setSeason("Spring");

        String json = this.objectMapper.writeValueAsString(newSchedule);

        this.mockMvc.perform(post(this.baseUrl + "/gameSchedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Game Schedule created successfully"))
                .andExpect(jsonPath("$.data.id").value(3))
                .andExpect(jsonPath("$.data.sport").value("Tennis"))
                .andExpect(jsonPath("$.data.season").value("Spring"));
    }


}
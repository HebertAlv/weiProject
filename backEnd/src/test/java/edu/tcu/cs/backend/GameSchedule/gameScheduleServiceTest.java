package edu.tcu.cs.backend.GameSchedule;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class gameScheduleServiceTest {

    @Mock
    private gameScheduleRepository gameScheduleRepository;

    @InjectMocks
    private gameScheduleService gameScheduleService;

    @Test
    void TestSaveGameSchedule() {
        // Arrange
        gameSchedule newSchedule = new gameSchedule();
        newSchedule.setId(3);
        newSchedule.setSport("Tennis");
        newSchedule.setSeason("Spring");

        given(gameScheduleRepository.save(newSchedule)).willReturn(newSchedule);

        // Act
        gameSchedule savedSchedule = gameScheduleService.save(newSchedule);

        // Assert
        assertThat(savedSchedule).isNotNull();
        assertThat(savedSchedule.getId()).isEqualTo(3);
        assertThat(savedSchedule.getSport()).isEqualTo("Tennis");
        assertThat(savedSchedule.getSeason()).isEqualTo("Spring");
        verify(gameScheduleRepository, times(1)).save(newSchedule);
    }
}
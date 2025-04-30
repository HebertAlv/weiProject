package edu.tcu.cs.backend.Games;


import edu.tcu.cs.backend.System.Exception.ObjectNotFoundException;
import edu.tcu.cs.backend.Games.game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class gameServiceTest {

    @Mock
    gameRepository gameRepository;

    @InjectMocks
    gameService gameService;
    List<game> games;

    @BeforeEach
    void setUp() {
        game g1 = new game();
        g1.setGameId(1);
        g1.setScheduleId(1);
        g1.setVenue("Stadium A");
        g1.setGameDate("2023-10-01");
        g1.setOpponent("Team A");
        g1.setIsFinalized(false);

        game g2 = new game();
        g2.setGameId(2);
        g2.setScheduleId(1);
        g2.setVenue("Stadium B");
        g2.setGameDate("2023-10-02");
        g2.setOpponent("Team B");
        g2.setIsFinalized(true);

        game g3 = new game();
        g3.setGameId(3);
        g3.setScheduleId(2);
        g3.setVenue("Stadium C");
        g3.setGameDate("2023-10-03");
        g3.setOpponent("Team C");
        g3.setIsFinalized(false);

        this.games = new ArrayList<>();
        this.games.add(g1);
        this.games.add(g2);
        this.games.add(g3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TestFindAll() {
        given(this.gameRepository.findAll()).willReturn(this.games);

        List<game> foundGames = this.gameService.findAll();

        assertThat(foundGames.size()).isEqualTo(this.games.size());
        verify(this.gameRepository, times(1)).findAll();
    }

    @Test
    void TestSaveGame() {
        // Arrange
        game newGame = new game();
        newGame.setGameId(4);
        newGame.setScheduleId(2);
        newGame.setVenue("Stadium D");
        newGame.setGameDate("2023-10-04");
        newGame.setOpponent("Team E");
        newGame.setIsFinalized(false);

        given(gameRepository.save(newGame)).willReturn(newGame);

        // Act
        game savedGame = gameService.save(newGame);

        // Assert
        assertThat(savedGame).isNotNull();
        assertThat(savedGame.getGameId()).isEqualTo(4);
        assertThat(savedGame.getScheduleId()).isEqualTo(2);
        assertThat(savedGame.getVenue()).isEqualTo("Stadium D");
        assertThat(savedGame.getGameDate()).isEqualTo("2023-10-04");
        assertThat(savedGame.getOpponent()).isEqualTo("Team E");
        assertThat(savedGame.getIsFinalized()).isFalse();
        verify(gameRepository, times(1)).save(newGame);
    }
}
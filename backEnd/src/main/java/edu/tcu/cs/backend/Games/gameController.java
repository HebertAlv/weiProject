package edu.tcu.cs.backend.Games;

import edu.tcu.cs.backend.Games.Converter.GameDtoToGameConverter;
import edu.tcu.cs.backend.Games.Converter.GameToGameDtoConverter;
import edu.tcu.cs.backend.Games.Dto.GameDto;
import edu.tcu.cs.backend.System.Result;
import edu.tcu.cs.backend.System.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}")
public class gameController {

    private final gameService GamesService;

    private final GameToGameDtoConverter gameToGameDtoConverter;

    private final GameDtoToGameConverter gameDtoToGameConverter;

    public gameController(gameService gamesService, GameToGameDtoConverter gameToGameDtoConverter, GameDtoToGameConverter gameDtoToGameConverter) {
        GamesService = gamesService;
        this.gameToGameDtoConverter = gameToGameDtoConverter;
        this.gameDtoToGameConverter = gameDtoToGameConverter;
    }

    @GetMapping("/gameSchedule/games")
    public Result findAllGames() {
        List<game> foundGames = this.GamesService.findAll();
        List<GameDto> gameDtos = foundGames.stream()
                .map(gameToGameDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find Success", gameDtos);
    }

    @PostMapping("/gameSchedule/{id}/games")
    public Result addGameToSchedule(@PathVariable int id, @RequestBody @Valid GameDto gameDto) {
        // Convert DTO to entity
        game newGame = gameDtoToGameConverter.convert(gameDto);
        newGame.setScheduleId(id); // Associate the game with the given schedule ID

        // Save the game
        game savedGame = GamesService.save(newGame);

        // Convert the saved entity back to DTO
        GameDto savedGameDto = gameToGameDtoConverter.convert(savedGame);

        // Return success response
        return new Result(true, StatusCode.SUCCESS, "Game added to schedule successfully", savedGameDto);
    }
}

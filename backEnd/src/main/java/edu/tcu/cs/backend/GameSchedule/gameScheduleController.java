package edu.tcu.cs.backend.GameSchedule;

import edu.tcu.cs.backend.System.Result;
import edu.tcu.cs.backend.System.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}")
public class gameScheduleController {

    private final gameScheduleService gameScheduleService;

    @Autowired
    public gameScheduleController(gameScheduleService gameScheduleService) {
        this.gameScheduleService = gameScheduleService;
    }

    @PostMapping("/gameSchedule")
    public Result saveGameSchedule(@RequestBody gameSchedule newGameSchedule) {
        gameSchedule savedGameSchedule = gameScheduleService.save(newGameSchedule);
        return new Result(true, StatusCode.SUCCESS, "Game Schedule created successfully", savedGameSchedule);
    }
}
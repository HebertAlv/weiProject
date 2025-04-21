package edu.tcu.cs.backend.gameSchedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class gameScheduleService {

    private final gameScheduleRepository gameScheduleRepository;

    @Autowired
    public gameScheduleService(gameScheduleRepository gameScheduleRepository) {
        this.gameScheduleRepository = gameScheduleRepository;
    }

    public List<gameSchedule> getAllGames() {

        return gameScheduleRepository.findAll();
    }
}
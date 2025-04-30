package edu.tcu.cs.backend.Games;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class gameService {

    private final gameRepository gamesRepository;

    public gameService(gameRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public List<game> findAll() {
        return this.gamesRepository.findAll();
    }

    public game save(game newGame) {
        return this.gamesRepository.save(newGame);
    }


}

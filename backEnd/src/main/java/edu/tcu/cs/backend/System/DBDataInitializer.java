package edu.tcu.cs.backend.System;

import edu.tcu.cs.backend.Availability.availability;
import edu.tcu.cs.backend.Availability.availabilityRepository;
import edu.tcu.cs.backend.CrewList.crewList;
import edu.tcu.cs.backend.CrewList.crewListRepository;
import edu.tcu.cs.backend.CrewedUser.crewedUser;
import edu.tcu.cs.backend.CrewedUser.crewedUserRepository;
import edu.tcu.cs.backend.User.crewMember;
import edu.tcu.cs.backend.User.crewMemberRepository;
import edu.tcu.cs.backend.GameSchedule.gameSchedule;
import edu.tcu.cs.backend.GameSchedule.gameScheduleRepository;
import edu.tcu.cs.backend.Games.game;
import edu.tcu.cs.backend.Games.gameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final crewMemberRepository crewMemberRepository;
    private final crewListRepository crewListRepository;
    private final crewedUserRepository crewedUserRepository;
    private final availabilityRepository availabilityRepository;
    private final gameScheduleRepository gameScheduleRepository;
    private final gameRepository gameRepository;

    public DBDataInitializer(crewMemberRepository crewMemberRepository,
                             crewListRepository crewListRepository,
                             crewedUserRepository crewedUserRepository,
                             availabilityRepository availabilityRepository,
                             gameScheduleRepository gameScheduleRepository,
                             gameRepository gameRepository) {
        this.crewMemberRepository = crewMemberRepository;
        this.crewListRepository = crewListRepository;
        this.crewedUserRepository = crewedUserRepository;
        this.availabilityRepository = availabilityRepository;
        this.gameScheduleRepository = gameScheduleRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Populate crew members
        crewMember cm1 = new crewMember();
        cm1.setId(1);
        cm1.setFirstName("John");
        cm1.setLastName("Doe");
        cm1.setEmail("john.doe@gmail.com");
        cm1.setPhoneNumber("1234567890");
        cm1.setRole("ADMIN");
        cm1.setPassword("password");
        cm1.setPositions(List.of("DIRECTOR", "PRODUCER"));

        crewMember cm2 = new crewMember();
        cm2.setId(2);
        cm2.setFirstName("Jane");
        cm2.setLastName("Smith");
        cm2.setEmail("jane.smith@gmail.com");
        cm2.setPhoneNumber("0987654321");
        cm2.setRole("USER");
        cm2.setPositions(List.of("ACTOR", "EDITOR"));

        crewMember cm3 = new crewMember();
        cm3.setId(3);
        cm3.setFirstName("Alice");
        cm3.setLastName("Johnson");
        cm3.setEmail("alice.johnson@gmail.com");
        cm3.setPhoneNumber("1122334455");
        cm3.setRole("USER");
        cm3.setPositions(List.of("DIRECTOR", "PRODUCER"));

        crewMemberRepository.save(cm1);
        crewMemberRepository.save(cm2);
        crewMemberRepository.save(cm3);

        // Populate game schedules
        gameSchedule gs1 = new gameSchedule();
        gs1.setId(1);
        gs1.setSport("Soccer");
        gs1.setSeason("Fall");

        gameSchedule gs2 = new gameSchedule();
        gs2.setId(2);
        gs2.setSport("Basketball");
        gs2.setSeason("Winter");

        gameScheduleRepository.save(gs1);
        gameScheduleRepository.save(gs2);

        // Populate games
        game g1 = new game();
        g1.setGameId(1);
        g1.setScheduleId(1);
        g1.setVenue("Stadium A");
        g1.setGameDate("2023-10-01");
        g1.setOpponent("Team A");
        g1.setIsFinalized(false);

        game g2 = new game();
        g2.setGameId(2);
        g2.setScheduleId(2);
        g2.setVenue("Stadium B");
        g2.setGameDate("2023-11-15");
        g2.setOpponent("Team B");
        g2.setIsFinalized(true);

        gameRepository.save(g1);
        gameRepository.save(g2);

        // Populate crew lists
        crewList cl1 = new crewList();
        cl1.setGameId(1);
        cl1.setGameStart("10:00:00");
        cl1.setGameDate("2023-10-01");
        cl1.setVenue("Stadium A");
        cl1.setOpponent("Team A");

        crewList cl2 = new crewList();
        cl2.setGameId(2);
        cl2.setGameStart("15:00:00");
        cl2.setGameDate("2023-11-15");
        cl2.setVenue("Stadium B");
        cl2.setOpponent("Team B");

        crewListRepository.save(cl1);
        crewListRepository.save(cl2);

        // Populate crewed users
        crewedUser cu1 = new crewedUser();
        cu1.setUserId(1);
        cu1.setFullName("John Doe");
        cu1.setReportTime("09:00:00");
        cu1.setReportLocation("Stadium A");
        cu1.setPosition("DIRECTOR");
        cu1.setCrewList(cl1);

        crewedUser cu2 = new crewedUser();
        cu2.setUserId(2);
        cu2.setFullName("Jane Smith");
        cu2.setReportTime("09:30:00");
        cu2.setReportLocation("Stadium A");
        cu2.setPosition("ACTOR");
        cu2.setCrewList(cl1);

        crewedUser cu3 = new crewedUser();
        cu3.setUserId(3);
        cu3.setFullName("Alice Johnson");
        cu3.setReportTime("09:15:00");
        cu3.setReportLocation("Stadium A");
        cu3.setPosition("DIRECTOR");
        cu3.setCrewList(cl1);

        crewedUserRepository.save(cu1);
        crewedUserRepository.save(cu2);
        crewedUserRepository.save(cu3);

        // Populate availability
        availability a1 = new availability();
        a1.setUserId(1);
        a1.setGameId(1);
        a1.setAvailability(true);
        a1.setComments("Available for the game");

        availability a2 = new availability();
        a2.setUserId(2);
        a2.setGameId(1);
        a2.setAvailability(false);
        a2.setComments("Not available for the game");

        availabilityRepository.save(a1);
        availabilityRepository.save(a2);
    }
}
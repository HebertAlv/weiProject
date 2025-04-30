package edu.tcu.cs.backend.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface crewMemberRepository extends JpaRepository<crewMember, Integer> {
    Optional<crewMember> findByEmail(String username);
}

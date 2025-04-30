package edu.tcu.cs.backend.User;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class crewMemberService implements UserDetailsService {
    private final crewMemberRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public crewMemberService(crewMemberRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public crewMember findUserById(int userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new crewMemberNotFoundException(userId));
    }

    public List<crewMember> findAll() {
        return this.userRepository.findAll();
    }

    public void deleteUserById(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new crewMemberNotFoundException(userId);
        }
        userRepository.deleteById(userId);
    }

    public crewMember save(crewMember newCrewMember) {
        newCrewMember.setPassword(this.passwordEncoder.encode(newCrewMember.getPassword()));
        return this.userRepository.save(newCrewMember);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .map(crewMember -> new MyUserPrincipal(crewMember))
                .orElseThrow(() -> new UsernameNotFoundException("username " + email + " is not found."));
    }
}
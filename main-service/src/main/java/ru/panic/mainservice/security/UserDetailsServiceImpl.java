package ru.panic.mainservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.panic.mainservice.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return new ru.panic.mainservice.security.UserDetails(userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UsernameNotFoundException("Authentication failed")).getId().toString());
    }
}

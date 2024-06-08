package ru.panic.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.panic.mainservice.exception.InvalidPasswordException;
import ru.panic.mainservice.exception.UserHasExistException;
import ru.panic.mainservice.exception.UserNotExistException;
import ru.panic.mainservice.model.User;
import ru.panic.mainservice.payload.request.SignInRequest;
import ru.panic.mainservice.payload.request.SignUpRequest;
import ru.panic.mainservice.payload.response.SignInResponse;
import ru.panic.mainservice.payload.response.SignUpResponse;
import ru.panic.mainservice.repository.UserRepository;
import ru.panic.mainservice.security.UserDetails;
import ru.panic.mainservice.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class UserAuthorizationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        //Checking on user exist by Email

        if (userRepository.existByEmail(signUpRequest.email())) {
            throw new UserHasExistException("A user with this Email already exists");
        }

        //Save user operation

        User savedUser = userRepository.save(
                User.builder()
                        .email(signUpRequest.email())
                        .password(passwordEncoder.encode(signUpRequest.password()))
                        .registeredAt(System.currentTimeMillis())
                        .build()
        );

        //Generating auth token for signed user

        String signedUserToken = jwtUtil.generateAccessToken(new UserDetails(savedUser.getId().toString()));

        return SignUpResponse.builder()
                .token(signedUserToken)
                .build();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SignInResponse signIn(SignInRequest signInRequest) {
        //Checking on user exist by email

        User signedUser = userRepository.findByEmail(signInRequest.email())
                .orElseThrow(() -> new UserNotExistException("Invalid email or password"));

        //Checking on password matching

        if (!passwordEncoder.matches(signInRequest.password(), signedUser.getPassword())) {
            throw new InvalidPasswordException("Invalid email or password");
        }


        //Generating auth token for signed user
        
        String signedUserToken = jwtUtil.generateAccessToken(new UserDetails(signedUser.getId().toString()));

        return SignInResponse.builder()
                .token(signedUserToken)
                .build();
    }

}

package ru.panic.mainservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.panic.mainservice.payload.request.SignInRequest;
import ru.panic.mainservice.payload.request.SignUpRequest;
import ru.panic.mainservice.payload.response.SignInResponse;
import ru.panic.mainservice.payload.response.SignUpResponse;
import ru.panic.mainservice.service.UserAuthorizationService;

@RestController
@RequestMapping("/api/v1/user/auth")
@RequiredArgsConstructor
public class UserAuthorizationController {

    private final UserAuthorizationService userAuthorizationService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userAuthorizationService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(userAuthorizationService.signIn(signInRequest));
    }
}

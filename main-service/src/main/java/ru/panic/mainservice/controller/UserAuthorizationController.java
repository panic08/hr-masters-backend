package ru.panic.mainservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth API", description = "This blocks describe the Auth API")
@RequiredArgsConstructor
public class UserAuthorizationController {

    private final UserAuthorizationService userAuthorizationService;

    @PostMapping("/signup")
    @Operation(description = "Sign up user")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Email must be correct; The password size can be from 4 to 24 characters long"),
            @ApiResponse(responseCode = "401", description = "A user with this Email already exists")
    })
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userAuthorizationService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    @Operation(description = "Sign in user")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(userAuthorizationService.signIn(signInRequest));
    }
}

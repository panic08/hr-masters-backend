package ru.panic.mainservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.panic.mainservice.dto.ParsedCandidateDto;
import ru.panic.mainservice.service.ParsedCandidateService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parsedCandidate")
@Tag(name = "ParsedCandidate API", description = "This blocks describe the ParsedCandidate API")
@RequiredArgsConstructor
public class ParsedCandidateController {

    private final ParsedCandidateService parsedCandidateService;

    @GetMapping("/getAll")
    @Operation(description = "Get all ParsedCandidate")
    @Parameter(in = ParameterIn.HEADER, name = "Authorization",
            required = true,
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<ParsedCandidateDto>> getAll(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return ResponseEntity.ok(parsedCandidateService.getAll(UUID.fromString((String) usernamePasswordAuthenticationToken.getPrincipal())));
    }

}

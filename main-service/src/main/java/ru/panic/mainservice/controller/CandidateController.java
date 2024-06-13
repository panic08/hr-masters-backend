package ru.panic.mainservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.panic.mainservice.dto.CandidateDto;
import ru.panic.mainservice.payload.request.DeleteCandidateByIdsRequest;
import ru.panic.mainservice.service.CandidateService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/candidate")
@Tag(name = "Candidate API", description = "This blocks describe the Candidate API")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/get_all")
    @Operation(description = "Get all")
    @Parameter(in = ParameterIn.HEADER, name = "Authorization",
            required = true,
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<CandidateDto>> getAll(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return ResponseEntity.ok(candidateService.getAll(UUID.fromString((String) usernamePasswordAuthenticationToken.getPrincipal())));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete By id")
    @Parameter(in = ParameterIn.HEADER, name = "Authorization",
            required = true,
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> delete(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken,
                                       @PathVariable("id") UUID id) {
        candidateService.delete(UUID.fromString((String) usernamePasswordAuthenticationToken.getPrincipal()), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteByIds")
    @Operation(description = "Delete by ids")
    @Parameter(in = ParameterIn.HEADER, name = "Authorization",
            required = true,
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Enter at least one id"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> deleteByIds(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken,
                                            @RequestBody @Valid DeleteCandidateByIdsRequest deleteCandidateByIdsRequest) {
        candidateService.deleteByIds(UUID.fromString((String) usernamePasswordAuthenticationToken.getPrincipal()),
                deleteCandidateByIdsRequest);
        return ResponseEntity.ok().build();
    }
}

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
import ru.panic.mainservice.dto.ParsedCandidateDto;
import ru.panic.mainservice.payload.request.DeleteParsedCandidateByIdsRequest;
import ru.panic.mainservice.payload.request.ReplaceAllToCandidateRequest;
import ru.panic.mainservice.service.ParsedCandidateService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parsed_candidate")
@Tag(name = "ParsedCandidate API", description = "This blocks describe the ParsedCandidate API")
@RequiredArgsConstructor
public class ParsedCandidateController {

    private final ParsedCandidateService parsedCandidateService;

    @GetMapping("/get_all")
    @Operation(description = "Get all")
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

    @PostMapping("/parse")
    @Operation(description = "Parse from different sources")
    @Parameter(in = ParameterIn.HEADER, name = "Authorization",
            required = true,
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<ParsedCandidateDto>> parse(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken,
                                                          @RequestParam("position_name") String positionName,
                                                          @RequestParam("position_count") Integer positionCount) {
        return ResponseEntity.ok(parsedCandidateService.parse(UUID.fromString((String) usernamePasswordAuthenticationToken.getPrincipal()),
                positionName, positionCount));
    }

    @PostMapping("/replace_all_to_candidate")
    @Operation(description = "Replace all to Candidate by ids")
    @Parameter(in = ParameterIn.HEADER, name = "Authorization",
            required = true,
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Enter at least one id"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<Void> replaceAllToCandidate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken,
                                                      @RequestBody @Valid ReplaceAllToCandidateRequest replaceAllToCandidateRequest) {
        parsedCandidateService.replaceAllToCandidate(UUID.fromString((String) usernamePasswordAuthenticationToken.getPrincipal()),
                replaceAllToCandidateRequest);
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
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<Void> deleteByIds(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken,
                                               @RequestBody @Valid DeleteParsedCandidateByIdsRequest deleteParsedCandidateByIdsRequest) {
        parsedCandidateService.deleteByIds(UUID.fromString((String) usernamePasswordAuthenticationToken.getPrincipal()),
                deleteParsedCandidateByIdsRequest);
        return ResponseEntity.ok().build();
    }

}

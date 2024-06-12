package ru.panic.mainservice.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "ParsedCandidate")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ParsedCandidateDto(UUID id, String fido, String pictureUrl, String positionName, Integer salary, String cvUrl) {
}

package ru.panic.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "User")
public record UserDto(UUID id, String email, String password, @JsonProperty("created_at") Long createdAt) {
}

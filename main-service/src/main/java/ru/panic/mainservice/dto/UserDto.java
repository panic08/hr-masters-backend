package ru.panic.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserDto(UUID id, String email, String password, @JsonProperty("created_at") Long createdAt) {
}

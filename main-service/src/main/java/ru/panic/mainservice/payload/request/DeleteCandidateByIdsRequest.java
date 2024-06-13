package ru.panic.mainservice.payload.request;

import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record DeleteCandidateByIdsRequest(@NotNull(message = "Enter at least one id") Set<UUID> ids) {
}

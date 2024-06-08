package ru.panic.mainservice.payload.response;

import lombok.Builder;

@Builder
public record SignUpResponse(String token) {
}

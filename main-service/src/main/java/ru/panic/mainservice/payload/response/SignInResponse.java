package ru.panic.mainservice.payload.response;

import lombok.Builder;

@Builder
public record SignInResponse(String token) {
}

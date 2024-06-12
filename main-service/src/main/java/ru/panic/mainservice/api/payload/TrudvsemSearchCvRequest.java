package ru.panic.mainservice.api.payload;

import lombok.Builder;

@Builder
public record TrudvsemSearchCvRequest(String orderColumn, int page, int pageSize, TrudvsemSearchCvRequestFilter filter) {

    @Builder
    public record TrudvsemSearchCvRequestFilter(String[] title, String[] titleType, String[] salary, String[] cvType) {
    }
}

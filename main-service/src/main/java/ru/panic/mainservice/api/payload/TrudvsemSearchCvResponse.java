package ru.panic.mainservice.api.payload;

import java.util.List;

public record TrudvsemSearchCvResponse(TrudvsemSearchCvResponseResult result) {

    public record TrudvsemSearchCvResponseResult(String code, List<List<Object>> data) {
    }
}

package ru.panic.mainservice.api.payload;

public record TrudvsemViewCvResponse(String code, TrudvsemViewCvResponseData data) {

    public record TrudvsemViewCvResponseData(boolean isRecommendations, TrudvsemViewCvResponseCv cv) {

        public record TrudvsemViewCvResponseCv(String positionName, String fio, Integer salary) {
        }
    }
}

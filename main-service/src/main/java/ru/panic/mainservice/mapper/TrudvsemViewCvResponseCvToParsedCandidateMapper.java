package ru.panic.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.panic.mainservice.api.payload.TrudvsemViewCvResponse;
import ru.panic.mainservice.model.ParsedCandidate;

@Mapper(componentModel = "spring")
public interface TrudvsemViewCvResponseCvToParsedCandidateMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "positionName", target = "positionName"),
            @Mapping(source = "fio", target = "fido"),
            @Mapping(source = "salary", target = "salary"),
            @Mapping(target = "pictureUrl", ignore = true),
            @Mapping(target = "cvUrl", ignore = true),
    })
    ParsedCandidate trudvsemViewCvResponseCvToParsedCandidate(TrudvsemViewCvResponse.TrudvsemViewCvResponseData.TrudvsemViewCvResponseCv cv);
}

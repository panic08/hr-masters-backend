package ru.panic.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.panic.mainservice.dto.ParsedCandidateDto;
import ru.panic.mainservice.model.ParsedCandidate;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParsedCandidateToParsedCandidateDtoMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "fido", target = "fido"),
            @Mapping(source = "pictureUrl", target = "pictureUrl"),
            @Mapping(source = "positionName", target = "positionName"),
            @Mapping(source = "salary", target = "salary"),
            @Mapping(source = "cvUrl", target = "cvUrl")
    })
    ParsedCandidateDto parsedCandidateToParsedCandidateDto(ParsedCandidate parsedCandidate);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "fido", target = "fido"),
            @Mapping(source = "pictureUrl", target = "pictureUrl"),
            @Mapping(source = "positionName", target = "positionName"),
            @Mapping(source = "salary", target = "salary"),
            @Mapping(source = "cvUrl", target = "cvUrl")
    })
    List<ParsedCandidateDto> parsedCandidateListToParsedCandidateDtoList(List<ParsedCandidate> parsedCandidateList);
}

package ru.panic.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.panic.mainservice.model.Candidate;
import ru.panic.mainservice.model.ParsedCandidate;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParsedCandidateToCandidateMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "fido", target = "fido"),
            @Mapping(source = "pictureUrl", target = "pictureUrl"),
            @Mapping(source = "positionName", target = "positionName"),
            @Mapping(source = "salary", target = "salary"),
            @Mapping(source = "cvUrl", target = "cvUrl")
    })
    Candidate parsedCandidateToCandidate(ParsedCandidate parsedCandidate);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "fido", target = "fido"),
            @Mapping(source = "pictureUrl", target = "pictureUrl"),
            @Mapping(source = "positionName", target = "positionName"),
            @Mapping(source = "salary", target = "salary"),
            @Mapping(source = "cvUrl", target = "cvUrl")
    })
    List<Candidate> parsedCandidateListToCandidateList(List<ParsedCandidate> parsedCandidateList);
}

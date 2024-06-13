package ru.panic.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.panic.mainservice.dto.CandidateDto;
import ru.panic.mainservice.model.Candidate;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateToCandidateDtoMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "fido", target = "fido"),
            @Mapping(source = "pictureUrl", target = "pictureUrl"),
            @Mapping(source = "positionName", target = "positionName"),
            @Mapping(source = "salary", target = "salary"),
            @Mapping(source = "cvUrl", target = "cvUrl")
    })
    CandidateDto candidateToCandidateDto(Candidate candidate);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "fido", target = "fido"),
            @Mapping(source = "pictureUrl", target = "pictureUrl"),
            @Mapping(source = "positionName", target = "positionName"),
            @Mapping(source = "salary", target = "salary"),
            @Mapping(source = "cvUrl", target = "cvUrl")
    })
    List<CandidateDto> candidateListToCandidateDtoList(List<Candidate> candidateList);
}

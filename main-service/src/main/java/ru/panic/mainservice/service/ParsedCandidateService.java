package ru.panic.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.panic.mainservice.dto.ParsedCandidateDto;
import ru.panic.mainservice.mapper.ParsedCandidateToParsedCandidateDtoMapperImpl;
import ru.panic.mainservice.repository.ParsedCandidateRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParsedCandidateService {

    private final ParsedCandidateRepository parsedCandidateRepository;
    private final ParsedCandidateToParsedCandidateDtoMapperImpl parsedCandidateToParsedCandidateDtoMapper;

    public List<ParsedCandidateDto> getAll(UUID principalId) {

        //Get all ParsedCandidate and map it

        return parsedCandidateToParsedCandidateDtoMapper.parsedCandidateListToParsedCandidateDtoList(
                parsedCandidateRepository.findAllByUserId(principalId)
        );
    }

}

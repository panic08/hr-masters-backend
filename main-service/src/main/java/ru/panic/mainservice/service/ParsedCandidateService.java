package ru.panic.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.panic.mainservice.api.TrudvsemApi;
import ru.panic.mainservice.api.payload.TrudvsemSearchCvRequest;
import ru.panic.mainservice.api.payload.TrudvsemSearchCvResponse;
import ru.panic.mainservice.api.payload.TrudvsemViewCvResponse;
import ru.panic.mainservice.dto.ParsedCandidateDto;
import ru.panic.mainservice.mapper.ParsedCandidateToParsedCandidateDtoMapperImpl;
import ru.panic.mainservice.mapper.TrudvsemViewCvResponseCvToParsedCandidateMapperImpl;
import ru.panic.mainservice.model.ParsedCandidate;
import ru.panic.mainservice.repository.ParsedCandidateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParsedCandidateService {

    private final String TRUDVSEM_URL = "https://trudvsem.ru";

    private final ParsedCandidateRepository parsedCandidateRepository;
    private final ParsedCandidateToParsedCandidateDtoMapperImpl parsedCandidateToParsedCandidateDtoMapper;
    private final TrudvsemApi trudvsemApi;
    private final TrudvsemViewCvResponseCvToParsedCandidateMapperImpl trudvsemViewCvResponseCvToParsedCandidateMapper;

    public List<ParsedCandidateDto> getAll(UUID principalId) {

        //Get all ParsedCandidate and map it

        return parsedCandidateToParsedCandidateDtoMapper.parsedCandidateListToParsedCandidateDtoList(
                parsedCandidateRepository.findAllByUserId(principalId)
        );
    }

    public List<ParsedCandidateDto> parse(UUID principalId, String positionName, Integer positionCount) {
        //todo do this parse method
        return null;
    }

    protected List<ParsedCandidate> getParsedCandidateByTrudvsem(String positionName, Integer positionCount) {
        //todo create comments to this component

        TrudvsemSearchCvResponse response = trudvsemApi.searchCv(TrudvsemSearchCvRequest.builder()
                .page(0)
                .pageSize(positionCount)
                .orderColumn("RELEVANCE_DESC")
                .filter(
                        TrudvsemSearchCvRequest.TrudvsemSearchCvRequestFilter.builder()
                                .title(new String[] {positionName})
                                .titleType(new String[] {"CV_ALL"})
                                .salary(new String[] {"0", "999999999999"})
                                .cvType(new String[] {"LONG"})
                                .build()
                )
                .build());

        List<ParsedCandidate> parsedCandidates = new ArrayList<>();

        for (List<Object> data : response.result().data()) {
            String firstId = (String) data.get(1);
            String secondId = (String) data.get(0);

            TrudvsemViewCvResponse cv = trudvsemApi.viewCv(firstId, secondId);

            ParsedCandidate currentParsedCandidate = trudvsemViewCvResponseCvToParsedCandidateMapper
                    .trudvsemViewCvResponseCvToParsedCandidate(cv.data().cv());

            currentParsedCandidate.setCvUrl(TRUDVSEM_URL + "/cv/card/" + firstId + "/" + secondId);
            currentParsedCandidate.setPictureUrl(TRUDVSEM_URL + "/iblocks/files/" + secondId);

            parsedCandidates.add(currentParsedCandidate);

        }

        return parsedCandidates;
    }

}

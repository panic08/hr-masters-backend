package ru.panic.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.panic.mainservice.api.TrudvsemApi;
import ru.panic.mainservice.api.payload.TrudvsemSearchCvRequest;
import ru.panic.mainservice.api.payload.TrudvsemSearchCvResponse;
import ru.panic.mainservice.api.payload.TrudvsemViewCvResponse;
import ru.panic.mainservice.dto.ParsedCandidateDto;
import ru.panic.mainservice.mapper.ParsedCandidateToCandidateMapperImpl;
import ru.panic.mainservice.mapper.ParsedCandidateToParsedCandidateDtoMapperImpl;
import ru.panic.mainservice.mapper.TrudvsemViewCvResponseCvToParsedCandidateMapperImpl;
import ru.panic.mainservice.model.Candidate;
import ru.panic.mainservice.model.ParsedCandidate;
import ru.panic.mainservice.payload.request.DeleteParsedCandidateByIdsRequest;
import ru.panic.mainservice.payload.request.ReplaceAllToCandidateRequest;
import ru.panic.mainservice.repository.CandidateRepository;
import ru.panic.mainservice.repository.ParsedCandidateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParsedCandidateService {

    private final String TRUDVSEM_URL = "https://trudvsem.ru";

    private final ParsedCandidateRepository parsedCandidateRepository;
    private final CandidateRepository candidateRepository;
    private final ParsedCandidateToParsedCandidateDtoMapperImpl parsedCandidateToParsedCandidateDtoMapper;
    private final TrudvsemApi trudvsemApi;
    private final TrudvsemViewCvResponseCvToParsedCandidateMapperImpl trudvsemViewCvResponseCvToParsedCandidateMapper;
    private final ParsedCandidateToCandidateMapperImpl parsedCandidateToCandidateMapper;

    public List<ParsedCandidateDto> getAll(UUID principalId) {
        //Get all ParsedCandidate by principalId and map it

        return parsedCandidateToParsedCandidateDtoMapper.parsedCandidateListToParsedCandidateDtoList(
                parsedCandidateRepository.findAllByUserId(principalId)
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ParsedCandidateDto> parse(UUID principalId, String positionName, Integer positionCount) {
        // Get ParsedCandidate from all sources
        List<ParsedCandidate> parsedCandidates = new ArrayList<>();

        //Get ParsedCandidate from Trudsvsem service
        parsedCandidates.addAll(getParsedCandidateByTrudvsem(positionName, positionCount));

        //Get ParsedCandidate from ... service
        //

        //Set userId to every ParsedCandidate
        for (ParsedCandidate parsedCandidate : parsedCandidates) {
            parsedCandidate.setUserId(principalId);
        }

        //Save all ParsedCandidate
        parsedCandidates = parsedCandidateRepository.saveAll(parsedCandidates);

        //Mapping parsedCandidates
        return parsedCandidateToParsedCandidateDtoMapper
                .parsedCandidateListToParsedCandidateDtoList(parsedCandidates);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void replaceAllToCandidate(UUID principalId, ReplaceAllToCandidateRequest replaceAllToCandidateRequest) {
        //Find all ParsedCandidate by ids and principalId
        List<ParsedCandidate> parsedCandidates = parsedCandidateRepository
                .findAllByIdsAndUserId(replaceAllToCandidateRequest.ids(), principalId);

        //Mapping in new Candidate
        List<Candidate> newMappedCandidates = parsedCandidateToCandidateMapper
                .parsedCandidateListToCandidateList(parsedCandidates);

        //Saving all Candidate
        newMappedCandidates = candidateRepository.saveAll(newMappedCandidates);

        //Deleting ParsedCandidate by ids and principalId
        parsedCandidateRepository.deleteByIdsAndUserId(replaceAllToCandidateRequest.ids(), principalId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteByIds(UUID principalId, DeleteParsedCandidateByIdsRequest deleteParsedCandidateByIdsRequest) {
        //Delete ParsedCandidate by ids and principalId

        parsedCandidateRepository.deleteByIdsAndUserId(deleteParsedCandidateByIdsRequest.ids(), principalId);
    }

    protected List<ParsedCandidate> getParsedCandidateByTrudvsem(String positionName, Integer positionCount) {
        //Searching cv in Trudvsem service

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

        //Creating ParsedCandidate to map parsed cvs from Trudvsem Service

        List<ParsedCandidate> parsedCandidates = new ArrayList<>();

        //Searcing every parsed cv to get detail information

        for (List<Object> data : response.result().data()) {
            String firstId = (String) data.get(1);
            String secondId = (String) data.get(0);

            //Get detail information from parsed cv

            TrudvsemViewCvResponse cv = trudvsemApi.viewCv(firstId, secondId);

            //Map detail information from parsed cv

            ParsedCandidate currentParsedCandidate = trudvsemViewCvResponseCvToParsedCandidateMapper
                    .trudvsemViewCvResponseCvToParsedCandidate(cv.data().cv());

            currentParsedCandidate.setCvUrl(TRUDVSEM_URL + "/cv/card/" + firstId + "/" + secondId);
            currentParsedCandidate.setPictureUrl(TRUDVSEM_URL + "/iblocks/files/" + secondId);

            parsedCandidates.add(currentParsedCandidate);

        }

        return parsedCandidates;
    }

}

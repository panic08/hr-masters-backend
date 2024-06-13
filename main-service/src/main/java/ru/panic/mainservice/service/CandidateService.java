package ru.panic.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.panic.mainservice.dto.CandidateDto;
import ru.panic.mainservice.mapper.CandidateToCandidateDtoMapperImpl;
import ru.panic.mainservice.payload.request.DeleteCandidateByIdsRequest;
import ru.panic.mainservice.repository.CandidateRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateToCandidateDtoMapperImpl candidateToCandidateDtoMapper;

    public List<CandidateDto> getAll(UUID principalId) {
        //Get all by principalId and map it

        return candidateToCandidateDtoMapper.candidateListToCandidateDtoList(
                candidateRepository.findAllByUserId(principalId)
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(UUID principalId, UUID id) {
        //Delete by id and principalId

        candidateRepository.deleteByIdAndUserId(id, principalId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteByIds(UUID principalId, DeleteCandidateByIdsRequest deleteCandidateByIdsRequest) {
        //Delete by ids and principalId

        candidateRepository.deleteByIdsAndUserId(deleteCandidateByIdsRequest.ids(), principalId);
    }
}

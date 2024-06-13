package ru.panic.mainservice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.CandidatesTable;
import org.springframework.stereotype.Repository;
import ru.panic.mainservice.model.Candidate;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CandidateRepository {

    private final DSLContext dslContext;

    public List<Candidate> findAllByUserId(UUID userId) {
        return dslContext.selectFrom(CandidatesTable.CANDIDATES_TABLE)
                .where(CandidatesTable.CANDIDATES_TABLE.USER_ID.eq(userId))
                .fetchInto(Candidate.class);
    }

    public void deleteByIdAndUserId(UUID id, UUID userId) {
        dslContext.deleteFrom(CandidatesTable.CANDIDATES_TABLE)
                .where(CandidatesTable.CANDIDATES_TABLE.ID.eq(id))
                .and(CandidatesTable.CANDIDATES_TABLE.USER_ID.eq(userId))
                .execute();
    }

    public void deleteByIdsAndUserId(Set<UUID> ids, UUID userId) {
        dslContext.deleteFrom(CandidatesTable.CANDIDATES_TABLE)
                .where(CandidatesTable.CANDIDATES_TABLE.ID.in(ids))
                .and(CandidatesTable.CANDIDATES_TABLE.USER_ID.eq(userId))
                .execute();
    }

    public List<Candidate> saveAll(List<Candidate> candidates) {
        var records = candidates.stream().map(candidate -> {
            return dslContext.newRecord(CandidatesTable.CANDIDATES_TABLE)
                    .setUserId(candidate.getUserId())
                    .setFido(candidate.getFido())
                    .setPictureUrl(candidate.getPictureUrl())
                    .setPositionName(candidate.getPositionName())
                    .setSalary(candidate.getSalary())
                    .setCvUrl(candidate.getCvUrl());
        }).toList();

        var insertedRecords = dslContext.insertInto(CandidatesTable.CANDIDATES_TABLE)
                .set(records)
                .returning(CandidatesTable.CANDIDATES_TABLE.ID,
                        CandidatesTable.CANDIDATES_TABLE.USER_ID,
                        CandidatesTable.CANDIDATES_TABLE.FIDO,
                        CandidatesTable.CANDIDATES_TABLE.PICTURE_URL,
                        CandidatesTable.CANDIDATES_TABLE.POSITION_NAME,
                        CandidatesTable.CANDIDATES_TABLE.SALARY,
                        CandidatesTable.CANDIDATES_TABLE.CV_URL)
                .fetch();

        return insertedRecords.stream().map(record ->
                Candidate.builder()
                        .id(record.getValue(CandidatesTable.CANDIDATES_TABLE.ID))
                        .userId(record.getValue(CandidatesTable.CANDIDATES_TABLE.USER_ID))
                        .fido(record.getValue(CandidatesTable.CANDIDATES_TABLE.FIDO))
                        .pictureUrl(record.getValue(CandidatesTable.CANDIDATES_TABLE.PICTURE_URL))
                        .positionName(record.getValue(CandidatesTable.CANDIDATES_TABLE.POSITION_NAME))
                        .salary(record.getValue(CandidatesTable.CANDIDATES_TABLE.SALARY))
                        .cvUrl(record.getValue(CandidatesTable.CANDIDATES_TABLE.CV_URL))
                        .build()
        ).toList();
    }

}

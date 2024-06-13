package ru.panic.mainservice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.ParsedCandidatesTable;
import org.springframework.stereotype.Repository;
import ru.panic.mainservice.model.ParsedCandidate;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ParsedCandidateRepository {

    private final DSLContext dslContext;

    public List<ParsedCandidate> findAllByUserId(UUID userId) {
        return dslContext.selectFrom(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE)
                .where(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.USER_ID.eq(userId))
                .fetchInto(ParsedCandidate.class);
    }

    public List<ParsedCandidate> findAllByIdsAndUserId(Set<UUID> ids, UUID userId) {
        return dslContext.selectFrom(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE)
                .where(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.ID.in(ids))
                .and(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.USER_ID.eq(userId))
                .fetchInto(ParsedCandidate.class);
    }

    public void deleteByIdsAndUserId(Set<UUID> ids, UUID userId) {
        dslContext.deleteFrom(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE)
                .where(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.ID.in(ids))
                .and(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.USER_ID.eq(userId))
                .execute();
    }

    public List<ParsedCandidate> saveAll(List<ParsedCandidate> parsedCandidates) {
        var records = parsedCandidates.stream().map(parsedCandidate -> {
            return dslContext.newRecord(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE)
                    .setUserId(parsedCandidate.getUserId())
                    .setFido(parsedCandidate.getFido())
                    .setPictureUrl(parsedCandidate.getPictureUrl())
                    .setPositionName(parsedCandidate.getPositionName())
                    .setSalary(parsedCandidate.getSalary())
                    .setCvUrl(parsedCandidate.getCvUrl());
        }).toList();

        var insertedRecords = dslContext.insertInto(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE)
                .set(records)
                .returning(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.ID,
                        ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.USER_ID,
                        ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.FIDO,
                        ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.PICTURE_URL,
                        ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.POSITION_NAME,
                        ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.SALARY,
                        ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.CV_URL)
                .fetch();

        return insertedRecords.stream().map(record ->
                ParsedCandidate.builder()
                        .id(record.getValue(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.ID))
                        .userId(record.getValue(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.USER_ID))
                        .fido(record.getValue(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.FIDO))
                        .pictureUrl(record.getValue(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.PICTURE_URL))
                        .positionName(record.getValue(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.POSITION_NAME))
                        .salary(record.getValue(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.SALARY))
                        .cvUrl(record.getValue(ParsedCandidatesTable.PARSED_CANDIDATES_TABLE.CV_URL))
                        .build()
        ).toList();
    }
}

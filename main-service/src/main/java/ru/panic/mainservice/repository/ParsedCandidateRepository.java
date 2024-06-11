package ru.panic.mainservice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.ParsedCandidatesTable;
import org.springframework.stereotype.Repository;
import ru.panic.mainservice.model.ParsedCandidate;

import java.util.List;
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

}

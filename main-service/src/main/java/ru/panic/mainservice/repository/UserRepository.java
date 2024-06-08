package ru.panic.mainservice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.panic.mainservice.model.User;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DSLContext dslContext;


}

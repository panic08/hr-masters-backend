package ru.panic.mainservice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.UsersTable;
import org.springframework.stereotype.Repository;
import ru.panic.mainservice.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dslContext;

    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(dslContext.selectFrom(UsersTable.USERS_TABLE)
                .where(UsersTable.USERS_TABLE.ID.eq(id))
                .fetchOneInto(User.class));
    }

    public boolean existByEmail(String email) {
        return dslContext.fetchExists(dslContext.selectFrom(UsersTable.USERS_TABLE)
                .where(UsersTable.USERS_TABLE.EMAIL.eq(email))
        );
    }

    public User save(User user) {
        return dslContext.insertInto(UsersTable.USERS_TABLE)
                .set(UsersTable.USERS_TABLE.EMAIL, user.getEmail())
                .set(UsersTable.USERS_TABLE.PASSWORD, user.getPassword())
                .set(UsersTable.USERS_TABLE.REGISTERED_AT, user.getRegisteredAt())
                .returningResult(UsersTable.USERS_TABLE)
                .fetchOneInto(User.class);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(dslContext.selectFrom(UsersTable.USERS_TABLE)
                .where(UsersTable.USERS_TABLE.EMAIL.eq(email))
                .fetchOneInto(User.class));
    }
}

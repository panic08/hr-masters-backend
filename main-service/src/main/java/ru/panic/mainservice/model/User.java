package ru.panic.mainservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "users_table")
@Data
@Builder
public class User {
    @Id
    private UUID id;

    private String email;

    private String password;

    @Column("registered_at")
    private Long registeredAt;
}

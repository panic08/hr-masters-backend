package ru.panic.mainservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "candidates_table")
@Data
@Builder
public class Candidate {
    @Id
    private UUID id;

    @Column("user_id")
    private UUID userId;

    private String fido;

    @Column("picture_url")
    private String pictureUrl;

    @Column("position_name")
    private String positionName;

    private Integer salary;

    @Column("cv_url")
    private String cvUrl;
}

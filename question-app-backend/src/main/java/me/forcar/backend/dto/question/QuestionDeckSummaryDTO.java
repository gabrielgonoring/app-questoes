package me.forcar.backend.dto.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class QuestionDeckSummaryDTO {
    private UUID id;
    private String name;
    private LocalDateTime creationDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}

package me.forcar.backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class QuestionDeckDTO {
    private UUID id;
    private String name;
    private LocalDateTime creationDate;
    private List<QuestionDeckAttributeDTO> attributes;

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


    public List<QuestionDeckAttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<QuestionDeckAttributeDTO> attributes) {
        this.attributes = attributes;
    }

}

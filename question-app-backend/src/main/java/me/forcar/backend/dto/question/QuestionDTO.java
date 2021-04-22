package me.forcar.backend.dto.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class QuestionDTO {

    private UUID id;

    private String questionNumber;

    private String correctAlternativeSymbol;

    private LocalDateTime creationDate;

    private String questionDeckId;

    private String questionDeckName;

    private String content;

    private List<QuestionAlternativeDTO> alternatives;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getCorrectAlternativeSymbol() {
        return correctAlternativeSymbol;
    }

    public void setCorrectAlternativeSymbol(String correctAlternativeSymbol) {
        this.correctAlternativeSymbol = correctAlternativeSymbol;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getQuestionDeckId() {
        return questionDeckId;
    }

    public void setQuestionDeckId(String questionDeckId) {
        this.questionDeckId = questionDeckId;
    }

    public String getQuestionDeckName() {
        return questionDeckName;
    }

    public void setQuestionDeckName(String questionDeckName) {
        this.questionDeckName = questionDeckName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<QuestionAlternativeDTO> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<QuestionAlternativeDTO> alternatives) {
        this.alternatives = alternatives;
    }
}

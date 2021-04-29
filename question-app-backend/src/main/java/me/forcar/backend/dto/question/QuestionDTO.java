package me.forcar.backend.dto.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class QuestionDTO {

    private UUID id;

    private Integer questionNumber;

    private String correctAlternativeSymbol;

    private LocalDateTime creationDate;

    private UUID questionDeckId;

    private String questionDeckName;

    private String content;

    private UUID nextQuestionId;

    private UUID previousQuestionId;

    private List<QuestionAlternativeDTO> alternatives;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
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

    public UUID getQuestionDeckId() {
        return questionDeckId;
    }

    public void setQuestionDeckId(UUID questionDeckId) {
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

    public UUID getNextQuestionId() {
        return nextQuestionId;
    }

    public void setNextQuestionId(UUID nextQuestionId) {
        this.nextQuestionId = nextQuestionId;
    }

    public UUID getPreviousQuestionId() {
        return previousQuestionId;
    }

    public void setPreviousQuestionId(UUID previousQuestionId) {
        this.previousQuestionId = previousQuestionId;
    }

    public List<QuestionAlternativeDTO> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<QuestionAlternativeDTO> alternatives) {
        this.alternatives = alternatives;
    }
}

package me.forcar.backend.dto.question;

import me.forcar.backend.jpa.po.question.QuestionAlternativePO;
import me.forcar.backend.jpa.po.question.QuestionDeckPO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class QuestionSummaryDTO {

    private UUID id;

    private Integer questionNumber;

    private LocalDateTime creationDate;

    private UUID questionDeckId;

    private String questionDeckName;

    private String content;//TODO: CREATE A CONTENT SUMMARY

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
}

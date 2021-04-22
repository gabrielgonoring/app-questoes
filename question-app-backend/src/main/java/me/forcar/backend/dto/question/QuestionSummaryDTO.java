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

    private String questionNumber;

    private LocalDateTime creationDate;

    private String questionDeckId;

    private String questionDeckName;

    private String content;//TODO: CREATE A CONTENT SUMMARY

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
}

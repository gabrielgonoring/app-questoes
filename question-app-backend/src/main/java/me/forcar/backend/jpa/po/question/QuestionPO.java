package me.forcar.backend.jpa.po.question;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_question")
public class QuestionPO {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id")
    private UUID id;

    @Column(name = "question_number")
    private Integer questionNumber;

    @Column(name = "correct_alternative_symbol")
    private String correctAlternativeSymbol;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_deck", referencedColumnName = "id")
    private QuestionDeckPO questionDeck;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionAlternativePO> alternatives;

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

    public QuestionDeckPO getQuestionDeck() {
        return questionDeck;
    }

    public void setQuestionDeck(QuestionDeckPO questionDeck) {
        this.questionDeck = questionDeck;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<QuestionAlternativePO> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<QuestionAlternativePO> alternatives) {
        this.alternatives = alternatives;
    }
}

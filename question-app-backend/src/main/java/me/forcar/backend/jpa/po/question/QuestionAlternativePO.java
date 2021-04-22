package me.forcar.backend.jpa.po.question;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tb_question_alternative")
public class QuestionAlternativePO {

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

    @Column(name = "alternative_symbol", nullable = false)
    private String alternativeSymbol;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_question", referencedColumnName = "id")
    private QuestionPO question;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAlternativeSymbol() {
        return alternativeSymbol;
    }

    public void setAlternativeSymbol(String alternativeSymbol) {
        this.alternativeSymbol = alternativeSymbol;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QuestionPO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionPO question) {
        this.question = question;
    }
}

package me.forcar.backend.jpa.po.question;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tb_question_deck_attibute")
public class QuestionDeckAttributePO {

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

    @Column(name = "description")
    private String descrioption;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_deck", referencedColumnName = "id")
    private QuestionDeckPO questionDeck;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescrioption() {
        return descrioption;
    }

    public void setDescrioption(String descrioption) {
        this.descrioption = descrioption;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public QuestionDeckPO getQuestionDeck() {
        return questionDeck;
    }

    public void setQuestionDeck(QuestionDeckPO questionDeck) {
        this.questionDeck = questionDeck;
    }
}

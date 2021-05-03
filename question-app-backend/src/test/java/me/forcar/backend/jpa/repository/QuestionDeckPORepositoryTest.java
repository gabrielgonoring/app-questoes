package me.forcar.backend.jpa.repository;

import me.forcar.backend.jpa.po.question.QuestionDeckAttributePO;
import me.forcar.backend.jpa.po.question.QuestionDeckPO;
import me.forcar.backend.jpa.repository.question.QuestionDeckPORepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
public class QuestionDeckPORepositoryTest {
    
    @Autowired
    private QuestionDeckPORepository questionDeckPORepository;

    @Autowired
    private TestEntityManager em;

    private QuestionDeckPO createQuestionDeck(){
        QuestionDeckPO questionDeckPO = new QuestionDeckPO();
        questionDeckPO.setName("Simple deck");
        questionDeckPO.setCreationDate(LocalDateTime.now());

        em.persist(questionDeckPO);

        return questionDeckPO;
    }

    private List<QuestionDeckAttributePO> createTwoQuestionDeckAttributes(QuestionDeckPO questionDeckPO){
        QuestionDeckAttributePO questionDeckAttributePO = new QuestionDeckAttributePO();
        questionDeckAttributePO.setDescription("year");
        questionDeckAttributePO.setValue("2020");
        questionDeckAttributePO.setQuestionDeck(questionDeckPO);

        em.persist(questionDeckAttributePO);


        QuestionDeckAttributePO questionDeckAttributePO2 = new QuestionDeckAttributePO();
        questionDeckAttributePO2.setDescription("university");
        questionDeckAttributePO2.setValue("MIT");
        questionDeckAttributePO2.setQuestionDeck(questionDeckPO);

        em.persist(questionDeckAttributePO2);

        return Arrays.asList(questionDeckAttributePO, questionDeckAttributePO2);
    }

    @Test
    public void shouldRecoverADeckWithItsAttributes() {
        QuestionDeckPO questionDeckPO = createQuestionDeck();
        var deckAttributeList = createTwoQuestionDeckAttributes(questionDeckPO);

        em.flush();
        em.clear();

        QuestionDeckPO questionDeckPORecovered = questionDeckPORepository.findFetchAttributesById(questionDeckPO.getId());

        em.detach(questionDeckPORecovered);

        Assertions.assertNotNull(questionDeckPORecovered);

        Assertions.assertEquals(
                deckAttributeList.size(),
                Assertions.assertDoesNotThrow(() -> questionDeckPORecovered.getAttributes().size())
        );
    }
}

package me.forcar.backend.jpa.repository;

import me.forcar.backend.jpa.po.question.QuestionAlternativePO;
import me.forcar.backend.jpa.po.question.QuestionDeckPO;
import me.forcar.backend.jpa.po.question.QuestionPO;
import me.forcar.backend.jpa.repository.question.QuestionAlternativePORepository;
import me.forcar.backend.jpa.repository.question.QuestionPORepository;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
public class QuestionPORepositoryTest {

    @Autowired
    private QuestionPORepository questionPORepository;

    @Autowired
    private QuestionAlternativePORepository questionAlternativePORepository;

    @Autowired
    private TestEntityManager em;

    private QuestionDeckPO createQuestionDeck(){
        QuestionDeckPO questionDeckPO = new QuestionDeckPO();
        questionDeckPO.setName("SIMPLE DECK");
        questionDeckPO.setCreationDate(LocalDateTime.now());

        em.persist(questionDeckPO);

        return questionDeckPO;
    }

    private List<QuestionPO> createThreeQuestionsForDeck(QuestionDeckPO questionDeckPO){
        QuestionPO questionPO = new QuestionPO();
        questionPO.setQuestionNumber(1);
        questionPO.setQuestionDeck(questionDeckPO);
        questionPO.setCreationDate(LocalDateTime.now());
        questionPO.setContent("any content 1");
        questionPO.setCorrectAlternativeSymbol("a");
        em.persist(questionPO);
        createQuestionAlternatives(questionPO);

        QuestionPO questionPO2 = new QuestionPO();
        questionPO2.setQuestionNumber(2);
        questionPO2.setQuestionDeck(questionDeckPO);
        questionPO2.setCreationDate(LocalDateTime.now());
        questionPO2.setContent("any content 2");
        questionPO2.setCorrectAlternativeSymbol("b");
        em.persist(questionPO2);
        createQuestionAlternatives(questionPO2);

        QuestionPO questionPO3 = new QuestionPO();
        questionPO3.setQuestionNumber(3);
        questionPO3.setQuestionDeck(questionDeckPO);
        questionPO3.setCreationDate(LocalDateTime.now());
        questionPO3.setContent("any content 3");
        questionPO3.setCorrectAlternativeSymbol("c");
        em.persist(questionPO3);
        createQuestionAlternatives(questionPO3);

        return Arrays.asList(questionPO, questionPO2, questionPO3);
    }

    private List<QuestionPO> createThreeQuestions(){
        return createThreeQuestionsForDeck(createQuestionDeck());
    }

    private List<QuestionAlternativePO> createQuestionAlternatives(QuestionPO questionPO){
        QuestionAlternativePO questionAlternativePO = new QuestionAlternativePO();
        questionAlternativePO.setAlternativeSymbol("a");
        questionAlternativePO.setContent("alternative content a");
        questionAlternativePO.setQuestion(questionPO);

        em.persist(questionAlternativePO);

        QuestionAlternativePO questionAlternativePO2 = new QuestionAlternativePO();
        questionAlternativePO2.setAlternativeSymbol("b");
        questionAlternativePO2.setContent("alternative content b");
        questionAlternativePO2.setQuestion(questionPO);

        em.persist(questionAlternativePO2);


        QuestionAlternativePO questionAlternativePO3 = new QuestionAlternativePO();
        questionAlternativePO3.setAlternativeSymbol("c");
        questionAlternativePO3.setContent("alternative content c");
        questionAlternativePO3.setQuestion(questionPO);

        em.persist(questionAlternativePO3);

        return Arrays.asList(questionAlternativePO, questionAlternativePO2, questionAlternativePO3);
    }

    @Test
    public void shouldRecoverPreviousQuestion(){

        QuestionDeckPO questionDeckPO = createQuestionDeck();
        createThreeQuestionsForDeck(questionDeckPO);

        var previousQuestion = questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberLessThanOrderByQuestionNumberDesc(questionDeckPO.getId(), 2);

        Assertions.assertNotNull(previousQuestion);
        Assertions.assertEquals(1, previousQuestion.getQuestionNumber());
    }

    @Test
    public void shouldRecoverNextQuestion(){

        QuestionDeckPO questionDeckPO = createQuestionDeck();
        createThreeQuestionsForDeck(questionDeckPO);

        var nextQuestion = questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberGreaterThanOrderByQuestionNumberAsc(questionDeckPO.getId(), 2);

        Assertions.assertNotNull(nextQuestion);
        Assertions.assertEquals(3, nextQuestion.getQuestionNumber());
    }

    @Test
    public void shouldNotRecoverNextQuestion(){

        QuestionDeckPO questionDeckPO = createQuestionDeck();
        createThreeQuestionsForDeck(questionDeckPO);

        var nextQuestion = questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberGreaterThanOrderByQuestionNumberAsc(questionDeckPO.getId(), 3);

        Assertions.assertNull(nextQuestion);
    }

    @Test
    public void shouldNotRecoverPreviousQuestion(){

        QuestionDeckPO questionDeckPO = createQuestionDeck();
        createThreeQuestionsForDeck(questionDeckPO);

        var previousQuestion = questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberLessThanOrderByQuestionNumberDesc(questionDeckPO.getId(), 1);

        Assertions.assertNull(previousQuestion);
    }

    @Test
    public void shouldRecoverThreeQuestionsByDeckId(){

        createThreeQuestions();

        QuestionDeckPO questionDeckPO = createQuestionDeck();
        createThreeQuestionsForDeck(questionDeckPO);

        var questionPOPage = questionPORepository.findAllByQuestionDeckId(PageRequest.of(0,20), questionDeckPO.getId());

        Assertions.assertNotNull(questionPOPage);
        Assertions.assertEquals(3L, questionPOPage.getTotalElements());
    }

    @Test
    public void shouldRecoverThreeQuestionAlternatives(){
        QuestionPO questionPO = createThreeQuestions().get(0);

        em.flush();//syncing all sql command with database
        em.clear();//removing all entitys from the context, now the next selection commands will be made in the database

        QuestionPO questionPORecoveredByFetch = questionPORepository.findFetchById(questionPO.getId());

        em.detach(questionPORecoveredByFetch);//this is for lazy loading does not work

        Assertions.assertNotNull(questionPORecoveredByFetch);
        Assertions.assertEquals(
                3,
                Assertions.assertDoesNotThrow(()-> questionPORecoveredByFetch.getAlternatives().size())
        );
    }
}

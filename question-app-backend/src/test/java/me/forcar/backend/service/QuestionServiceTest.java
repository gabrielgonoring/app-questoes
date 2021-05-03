package me.forcar.backend.service;

import me.forcar.backend.dto.question.QuestionAlternativeDTO;
import me.forcar.backend.dto.question.QuestionDTO;
import me.forcar.backend.dto.question.QuestionSummaryDTO;
import me.forcar.backend.jpa.po.question.QuestionAlternativePO;
import me.forcar.backend.jpa.po.question.QuestionDeckPO;
import me.forcar.backend.jpa.po.question.QuestionPO;
import me.forcar.backend.jpa.repository.question.QuestionPORepository;
import me.forcar.backend.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionPORepository questionPORepository;

    private QuestionService questionService;


    @BeforeEach
    private void beforeEach(){
        questionService = new QuestionService(questionPORepository, new ModelMapper());
    }

    private QuestionDeckPO createQuestionDeck(){
        QuestionDeckPO questionDeckPO = new QuestionDeckPO();
        questionDeckPO.setId(UUID.randomUUID());
        questionDeckPO.setName("any deck");
        questionDeckPO.setCreationDate(LocalDateTime.now());

        return questionDeckPO;
    }

    private Page<QuestionPO> createQuestionPageWithThreeElement(QuestionDeckPO questionDeckPO, Pageable pageable){
        return Utils.toPage(createThreeQuestionsForDeck(questionDeckPO), pageable);
    }

    private List<QuestionPO> createThreeQuestionsForDeck(QuestionDeckPO questionDeckPO){
        QuestionPO questionPO = new QuestionPO();
        questionPO.setId(UUID.randomUUID());
        questionPO.setQuestionNumber(1);
        questionPO.setQuestionDeck(questionDeckPO);
        questionPO.setCreationDate(LocalDateTime.now());
        questionPO.setContent("any content 1");
        questionPO.setCorrectAlternativeSymbol("a");
        questionPO.setAlternatives(createThreeQuestionAlternatives(questionPO));

        QuestionPO questionPO2 = new QuestionPO();
        questionPO2.setId(UUID.randomUUID());
        questionPO2.setQuestionNumber(2);
        questionPO2.setQuestionDeck(questionDeckPO);
        questionPO2.setCreationDate(LocalDateTime.now());
        questionPO2.setContent("any content 2");
        questionPO2.setCorrectAlternativeSymbol("b");
        questionPO2.setAlternatives(createThreeQuestionAlternatives(questionPO2));

        QuestionPO questionPO3 = new QuestionPO();
        questionPO3.setId(UUID.randomUUID());
        questionPO3.setQuestionNumber(3);
        questionPO3.setQuestionDeck(questionDeckPO);
        questionPO3.setCreationDate(LocalDateTime.now());
        questionPO3.setContent("any content 3");
        questionPO3.setCorrectAlternativeSymbol("c");
        questionPO3.setAlternatives(createThreeQuestionAlternatives(questionPO3));

        return Arrays.asList(questionPO, questionPO2, questionPO3);
    }

    private List<QuestionAlternativePO> createThreeQuestionAlternatives(QuestionPO questionPO){
        QuestionAlternativePO questionAlternativePO = new QuestionAlternativePO();
        questionAlternativePO.setId(UUID.randomUUID());
        questionAlternativePO.setAlternativeSymbol("a");
        questionAlternativePO.setContent("alternative content a");
        questionAlternativePO.setQuestion(questionPO);

        QuestionAlternativePO questionAlternativePO2 = new QuestionAlternativePO();
        questionAlternativePO2.setId(UUID.randomUUID());
        questionAlternativePO2.setAlternativeSymbol("b");
        questionAlternativePO2.setContent("alternative content b");
        questionAlternativePO2.setQuestion(questionPO);


        QuestionAlternativePO questionAlternativePO3 = new QuestionAlternativePO();
        questionAlternativePO3.setAlternativeSymbol("c");
        questionAlternativePO3.setContent("alternative content c");
        questionAlternativePO3.setQuestion(questionPO);

        return Arrays.asList(questionAlternativePO, questionAlternativePO2, questionAlternativePO3);
    }


    @Test
    public void shouldReturnCompleteQuestionById() {
        QuestionDeckPO questionDeckPO = createQuestionDeck();
        List<QuestionPO> questionPOList = createThreeQuestionsForDeck(questionDeckPO);

        QuestionPO previousQuestionPO = questionPOList.get(0);
        QuestionPO questionPO = questionPOList.get(1);
        QuestionPO nextQuestionPO = questionPOList.get(2);

        configMockReturnsToShouldReturnCompleteQuestionById(
                questionDeckPO,
                questionPO,
                previousQuestionPO,
                nextQuestionPO
        );

        QuestionDTO questionDTO = questionService.getQuestionById(questionPO.getId());

        Assertions.assertNotNull(questionDTO);
        Assertions.assertEquals(questionPO.getId(), questionDTO.getId());
        Assertions.assertEquals(questionPO.getQuestionNumber(), questionDTO.getQuestionNumber());
        Assertions.assertEquals(questionPO.getContent(), questionDTO.getContent());
        Assertions.assertEquals(questionPO.getCorrectAlternativeSymbol(), questionDTO.getCorrectAlternativeSymbol());
        Assertions.assertEquals(questionPO.getCreationDate(), questionDTO.getCreationDate());
        Assertions.assertEquals(questionPO.getQuestionDeck().getId(), questionDTO.getQuestionDeckId());
        Assertions.assertEquals(questionPO.getQuestionDeck().getName(), questionDTO.getQuestionDeckName());
        Assertions.assertEquals(nextQuestionPO.getId(), questionDTO.getNextQuestionId());
        Assertions.assertEquals(previousQuestionPO.getId(), questionDTO.getPreviousQuestionId());
        Assertions.assertEquals(3, questionDTO.getAlternatives().size());

        Map<UUID, QuestionAlternativePO> questionAlternativePOMap = questionPO.getAlternatives()
                                                                        .stream()
                                                                            .collect(Collectors.toMap(QuestionAlternativePO::getId, alternative -> alternative));

        for (QuestionAlternativeDTO questionAlternativeDTO : questionDTO.getAlternatives()){
            QuestionAlternativePO questionAlternativePO = questionAlternativePOMap.get(questionAlternativeDTO.getId());

            Assertions.assertEquals(questionAlternativePO.getId(), questionAlternativeDTO.getId());
            Assertions.assertEquals(questionAlternativePO.getAlternativeSymbol(), questionAlternativeDTO.getAlternativeSymbol());
            Assertions.assertEquals(questionAlternativePO.getContent(), questionAlternativeDTO.getContent());
        }
    }

    private void configMockReturnsToShouldReturnCompleteQuestionById(
            QuestionDeckPO questionDeckPO,
            QuestionPO questionPO,
            QuestionPO previousQuestionPO,
            QuestionPO nextQuestionPO){

        Mockito.when(questionPORepository.findFetchById(questionPO.getId())).thenReturn(questionPO);

        Mockito.when(
                questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberLessThanOrderByQuestionNumberDesc(
                        questionDeckPO.getId(),
                        questionPO.getQuestionNumber()
                )
        )
        .thenReturn(previousQuestionPO);

        Mockito.when(
                questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberGreaterThanOrderByQuestionNumberAsc(
                        questionDeckPO.getId(),
                        questionPO.getQuestionNumber()
                )
        )
        .thenReturn(nextQuestionPO);
    }

    @Test
    public void shouldNotReturnPreviousQuestionIdWhenSearchFirstQuestionById() {
        QuestionDeckPO questionDeckPO = createQuestionDeck();
        List<QuestionPO> questionPOList = createThreeQuestionsForDeck(questionDeckPO);

        QuestionPO questionPO = questionPOList.get(0);
        QuestionPO nextQuestionPO = questionPOList.get(1);

        configMockReturnsTShouldNotReturnPreviousQuestionIdWhenSearchFirstQuestionById(
                questionDeckPO,
                questionPO,
                nextQuestionPO
        );

        QuestionDTO questionDTO = questionService.getQuestionById(questionPO.getId());

        Assertions.assertEquals(questionPO.getId(), questionDTO.getId());
        Assertions.assertEquals(nextQuestionPO.getId(), questionDTO.getNextQuestionId());
        Assertions.assertNull(questionDTO.getPreviousQuestionId());
        Assertions.assertEquals(3, questionDTO.getAlternatives().size());
    }

    private void configMockReturnsTShouldNotReturnPreviousQuestionIdWhenSearchFirstQuestionById(
            QuestionDeckPO questionDeckPO,
            QuestionPO questionPO,
            QuestionPO nextQuestionPO){

        Mockito.when(questionPORepository.findFetchById(questionPO.getId())).thenReturn(questionPO);

        Mockito.when(
                questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberLessThanOrderByQuestionNumberDesc(
                        questionDeckPO.getId(),
                        questionPO.getQuestionNumber()
                )
        )
        .thenReturn(null);

        Mockito.when(
                questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberGreaterThanOrderByQuestionNumberAsc(
                        questionDeckPO.getId(),
                        questionPO.getQuestionNumber()
                )
        )
        .thenReturn(nextQuestionPO);
    }


    @Test
    public void shouldNotReturnNextQuestionIdWhenSearchFirstQuestionById() {
        QuestionDeckPO questionDeckPO = createQuestionDeck();
        List<QuestionPO> questionPOList = createThreeQuestionsForDeck(questionDeckPO);

        QuestionPO previousQuestionPO = questionPOList.get(1);
        QuestionPO questionPO = questionPOList.get(2);

        configMockReturnsTShouldNotReturnNextQuestionIdWhenSearchFirstQuestionById(
                questionDeckPO,
                questionPO,
                previousQuestionPO
        );

        QuestionDTO questionDTO = questionService.getQuestionById(questionPO.getId());

        Assertions.assertEquals(questionPO.getId(), questionDTO.getId());
        Assertions.assertNull(questionDTO.getNextQuestionId());
        Assertions.assertEquals(previousQuestionPO.getId(), questionDTO.getPreviousQuestionId());
        Assertions.assertEquals(3, questionDTO.getAlternatives().size());
    }

    private void configMockReturnsTShouldNotReturnNextQuestionIdWhenSearchFirstQuestionById(
            QuestionDeckPO questionDeckPO,
            QuestionPO questionPO,
            QuestionPO previousQuestionPO){

        Mockito.when(questionPORepository.findFetchById(questionPO.getId())).thenReturn(questionPO);

        Mockito.when(
                questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberLessThanOrderByQuestionNumberDesc(
                        questionDeckPO.getId(),
                        questionPO.getQuestionNumber()
                )
        )
                .thenReturn(previousQuestionPO);

        Mockito.when(
                questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberGreaterThanOrderByQuestionNumberAsc(
                        questionDeckPO.getId(),
                        questionPO.getQuestionNumber()
                )
        )
        .thenReturn(null);
    }

    @Test
    public void shouldReturnQuestionPageWithThreeElementWhenSearchByQuestionDeckId(){
        Pageable pageable = PageRequest.of(0, 20);
        QuestionDeckPO questionDeckPO = createQuestionDeck();
        Page<QuestionPO> questionPOPage = createQuestionPageWithThreeElement(questionDeckPO, pageable);

        Mockito
            .when(questionPORepository.findAllByQuestionDeckId(pageable, questionDeckPO.getId()))
            .thenReturn(questionPOPage);

        Page<QuestionSummaryDTO> questionSummaryDTOPage = questionService.getQuestionByIdDeck(pageable, questionDeckPO.getId());

        Assertions.assertNotNull(questionSummaryDTOPage);
        Assertions.assertEquals(3, questionSummaryDTOPage.getNumberOfElements());

        Map<UUID, QuestionPO> questionPOMap = questionPOPage.stream().collect(Collectors.toMap(QuestionPO::getId, question -> question));

        for (QuestionSummaryDTO questionSummaryDTO : questionSummaryDTOPage){
            QuestionPO questionPO = questionPOMap.get(questionSummaryDTO.getId());

            Assertions.assertEquals(questionPO.getId(), questionSummaryDTO.getId());
            Assertions.assertEquals(questionPO.getQuestionDeck().getId(), questionSummaryDTO.getQuestionDeckId());
            Assertions.assertEquals(questionPO.getQuestionDeck().getName(), questionSummaryDTO.getQuestionDeckName());
            Assertions.assertEquals(questionPO.getQuestionNumber(), questionSummaryDTO.getQuestionNumber());
            Assertions.assertEquals(questionPO.getContent(), questionSummaryDTO.getContent());
            Assertions.assertEquals(questionPO.getCreationDate(), questionSummaryDTO.getCreationDate());
        }
    }

    @Test
    public void shouldReturnEmptyQuestionPageWhenSearchByQuestionDeckId(){
        Pageable pageable = PageRequest.of(0, 20);
        UUID questionDeckID = UUID.randomUUID();

        Mockito
            .when(questionPORepository.findAllByQuestionDeckId(pageable, questionDeckID))
            .thenReturn(Utils.toPage(Collections.emptyList(), pageable));

        Page<QuestionSummaryDTO> questionSummaryDTOPage = questionService.getQuestionByIdDeck(pageable, questionDeckID);

        Assertions.assertNotNull(questionSummaryDTOPage);
        Assertions.assertEquals(0, questionSummaryDTOPage.getNumberOfElements());

    }
}

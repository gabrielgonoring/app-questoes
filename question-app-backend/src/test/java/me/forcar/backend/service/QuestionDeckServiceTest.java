package me.forcar.backend.service;

import me.forcar.backend.dto.question.QuestionDeckDTO;
import me.forcar.backend.dto.question.QuestionDeckSummaryDTO;
import me.forcar.backend.jpa.po.question.QuestionDeckAttributePO;
import me.forcar.backend.jpa.po.question.QuestionDeckPO;
import me.forcar.backend.jpa.repository.question.QuestionDeckPORepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class QuestionDeckServiceTest {

    @Mock
    private QuestionDeckPORepository questionDeckPORepository;

    private QuestionDeckService questionDeckService;

    @BeforeEach
    private void beforeEach(){
        questionDeckService = new QuestionDeckService(questionDeckPORepository, new ModelMapper());
    }

    private Page<QuestionDeckPO> createQuestionDeckPageWithThreeElements(Pageable pageable){
        return toPage(createThreeQuestionDeck(), pageable);
    }

    private <T> Page<T> toPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        if(start > list.size())
            return new PageImpl<>(new ArrayList<>(), pageable, list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    private List<QuestionDeckPO> createThreeQuestionDeck(){
        QuestionDeckPO questionDeckPO = new QuestionDeckPO();
        questionDeckPO.setId(UUID.randomUUID());
        questionDeckPO.setName("any deck 1");
        questionDeckPO.setCreationDate(LocalDateTime.now());
        questionDeckPO.setAttributes(createTwoQuestionDeckAttributes());

        QuestionDeckPO questionDeckPO2 = new QuestionDeckPO();
        questionDeckPO2.setId(UUID.randomUUID());
        questionDeckPO2.setName("any deck 2");
        questionDeckPO2.setCreationDate(LocalDateTime.now());
        questionDeckPO2.setAttributes(createTwoQuestionDeckAttributes());

        QuestionDeckPO questionDeckPO3 = new QuestionDeckPO();
        questionDeckPO3.setId(UUID.randomUUID());
        questionDeckPO3.setName("any deck 3");
        questionDeckPO3.setCreationDate(LocalDateTime.now());
        questionDeckPO3.setAttributes(createTwoQuestionDeckAttributes());

        return Arrays.asList(questionDeckPO, questionDeckPO2, questionDeckPO3);
    }

    private List<QuestionDeckAttributePO> createTwoQuestionDeckAttributes(){
        QuestionDeckAttributePO questionDeckAttributePO = new QuestionDeckAttributePO();
        questionDeckAttributePO.setId(UUID.randomUUID());
        questionDeckAttributePO.setDescription("field 1");
        questionDeckAttributePO.setValue("field 1 values");

        QuestionDeckAttributePO questionDeckAttributePO2 = new QuestionDeckAttributePO();
        questionDeckAttributePO2.setId(UUID.randomUUID());
        questionDeckAttributePO2.setDescription("field 2");
        questionDeckAttributePO2.setValue("field 2 values");

        return Arrays.asList(questionDeckAttributePO, questionDeckAttributePO2);
    }

    @Test
    void shouldReturnADeckWithTwoAttributes() {
        QuestionDeckPO questionDeckPO = createThreeQuestionDeck().get(0);

        Mockito.when(questionDeckPORepository.findFetchAttributesById(questionDeckPO.getId())).thenReturn(questionDeckPO);

        QuestionDeckDTO questionDeckDTO = questionDeckService.getDeckById(questionDeckPO.getId());

        Assertions.assertNotNull(questionDeckDTO);
        Assertions.assertEquals(questionDeckPO.getId(), questionDeckDTO.getId());
        Assertions.assertEquals(questionDeckPO.getName(), questionDeckDTO.getName());
        Assertions.assertEquals(questionDeckPO.getCreationDate(), questionDeckDTO.getCreationDate());
        Assertions.assertEquals(2, questionDeckDTO.getAttributes().size());

        Map<UUID, QuestionDeckAttributePO> questionDeckAttributePOMap = questionDeckPO
                                                                            .getAttributes()
                                                                                .stream()
                                                                                    .collect(Collectors.toMap(QuestionDeckAttributePO::getId, attribute -> attribute));

        for (var questionDeckAttributeDTO : questionDeckDTO.getAttributes()){

            QuestionDeckAttributePO questionDeckAttributePO = questionDeckAttributePOMap.get(questionDeckAttributeDTO.getId());

            Assertions.assertEquals(questionDeckAttributePO.getId(), questionDeckAttributeDTO.getId());
            Assertions.assertEquals(questionDeckAttributePO.getDescription(), questionDeckAttributeDTO.getDescription());
            Assertions.assertEquals(questionDeckAttributePO.getValue(), questionDeckAttributeDTO.getValue());
        }
    }

    @Test
    void shouldReturnADeckPageWithThreeElements() {

        Pageable pageable = PageRequest.of(0,20);

        Page<QuestionDeckPO> questionDeckPOPage = createQuestionDeckPageWithThreeElements(pageable);

        Mockito.when(questionDeckPORepository.findAll(pageable)).thenReturn(questionDeckPOPage);

        Page<QuestionDeckSummaryDTO> questionDeckSummaryDTOPage = questionDeckService.getDecks(pageable);

        Map<UUID, QuestionDeckPO> questionDeckPOMap = questionDeckPOPage
                                                            .stream()
                                                                .collect(Collectors.toMap(QuestionDeckPO::getId, deck -> deck));

        Assertions.assertNotNull(questionDeckSummaryDTOPage);
        Assertions.assertEquals(3, questionDeckSummaryDTOPage.getTotalElements());

        for (var questionDeckSummaryDTO : questionDeckSummaryDTOPage){
            QuestionDeckPO questionDeckPO = questionDeckPOMap.get(questionDeckSummaryDTO.getId());

            Assertions.assertEquals(questionDeckPO.getId(), questionDeckSummaryDTO.getId());
            Assertions.assertEquals(questionDeckPO.getName(), questionDeckSummaryDTO.getName());
            Assertions.assertEquals(questionDeckPO.getCreationDate(), questionDeckSummaryDTO.getCreationDate());
        }
    }



}

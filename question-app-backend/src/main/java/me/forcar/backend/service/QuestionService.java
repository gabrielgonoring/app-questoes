package me.forcar.backend.service;

import me.forcar.backend.dto.question.QuestionDTO;
import me.forcar.backend.dto.question.QuestionSummaryDTO;
import me.forcar.backend.jpa.po.question.QuestionPO;
import me.forcar.backend.jpa.repository.question.QuestionPORepository;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {

    @Autowired
    private QuestionPORepository questionPORepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<QuestionSummaryDTO> getQuestionByIdDeck(Pageable pageable, UUID idDeck){
        return questionPORepository
                .findAllByQuestionDeckId(pageable, idDeck)
                .map(this::convertToQuestionSummaryDTO);
    }

    public QuestionSummaryDTO convertToQuestionSummaryDTO(QuestionPO questionPO){
        Hibernate.unproxy(questionPO);
        return modelMapper.map(questionPO, QuestionSummaryDTO.class);
    }

    public QuestionDTO getQuestionById(UUID questionID){
        QuestionPO questionPO = questionPORepository.findFetchById(questionID);

        QuestionDTO questionDTO = modelMapper.map(questionPO, QuestionDTO.class);

        questionDTO.setNextQuestionId(
                Optional.ofNullable(questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberGreaterThanOrderByQuestionNumberAsc(questionDTO.getQuestionDeckId(), questionDTO.getQuestionNumber()))
                        .orElse(new QuestionPO())
                        .getId()
        );

        questionDTO.setPreviousQuestionId(
                Optional.ofNullable(questionPORepository.findFirstByQuestionDeckIdAndQuestionNumberLessThanOrderByQuestionNumberDesc(questionDTO.getQuestionDeckId(), questionDTO.getQuestionNumber()))
                        .orElse(new QuestionPO())
                        .getId()
        );


        return questionDTO;
    }
}

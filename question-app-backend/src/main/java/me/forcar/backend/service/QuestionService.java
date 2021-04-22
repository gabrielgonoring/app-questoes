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
        var questionPO = questionPORepository.findFetchById(questionID);

        return modelMapper.map(questionPO, QuestionDTO.class);
    }
}

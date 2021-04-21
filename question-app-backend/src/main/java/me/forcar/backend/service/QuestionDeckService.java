package me.forcar.backend.service;

import me.forcar.backend.dto.QuestionDeckDTO;
import me.forcar.backend.dto.QuestionDeckSummaryDTO;
import me.forcar.backend.jpa.po.question.QuestionDeckAttributePO;
import me.forcar.backend.jpa.po.question.QuestionDeckPO;
import me.forcar.backend.jpa.repository.question.QuestionDeckPORepository;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionDeckService {

    @Autowired
    private QuestionDeckPORepository questionDeckPORepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<QuestionDeckSummaryDTO> getDecks(Pageable pageable){
        return questionDeckPORepository
                .findAll(pageable)
                .map(this::convertToQuestionDeckSummaryDTO);
    }

    public QuestionDeckSummaryDTO convertToQuestionDeckSummaryDTO(QuestionDeckPO questionDeckPO){
        Hibernate.unproxy(questionDeckPO);
        return modelMapper.map(questionDeckPO, QuestionDeckSummaryDTO.class);
    }

    public QuestionDeckDTO getDeckById(UUID id){
        var questionDeckPO = questionDeckPORepository.findById(id).orElseThrow();

        return modelMapper.map(questionDeckPO, QuestionDeckDTO.class);
    }
}

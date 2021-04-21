package me.forcar.backend.controller;

import me.forcar.backend.dto.question.QuestionDeckDTO;
import me.forcar.backend.dto.question.QuestionDeckSummaryDTO;
import me.forcar.backend.service.QuestionDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/question/deck")
public class QuestionDeckController {

    @Autowired
    private QuestionDeckService questionDeckService;

    @GetMapping("/list")
    private Page<QuestionDeckSummaryDTO> getDecks(Pageable pageable){
        return questionDeckService.getDecks(pageable);
    }

    @GetMapping("/{idQuestionDeck}")
    public QuestionDeckDTO getDeckById(@PathVariable @NotNull UUID idQuestionDeck){
        return questionDeckService.getDeckById(idQuestionDeck);
    }
}

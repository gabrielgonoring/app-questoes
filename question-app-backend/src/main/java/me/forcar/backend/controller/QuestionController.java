package me.forcar.backend.controller;

import me.forcar.backend.dto.question.QuestionDTO;
import me.forcar.backend.dto.question.QuestionSummaryDTO;
import me.forcar.backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/list/by/deck/{idDeck}")
    public ResponseEntity<Page<QuestionSummaryDTO>> getQuestionsByIdDeck(Pageable pageable, @PathVariable UUID idDeck){
        return ResponseEntity.ok(questionService.getQuestionByIdDeck(pageable, idDeck));
    }

    @GetMapping("/{idQuestion}")
    private ResponseEntity<QuestionDTO> getById(@PathVariable UUID idQuestion){
        return ResponseEntity.ok(questionService.getQuestionById(idQuestion));
    }
}

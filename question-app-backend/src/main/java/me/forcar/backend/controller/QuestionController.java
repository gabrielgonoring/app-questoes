package me.forcar.backend.controller;

import io.swagger.annotations.ApiOperation;
import me.forcar.backend.dto.question.QuestionDTO;
import me.forcar.backend.dto.question.QuestionSummaryDTO;
import me.forcar.backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/question")
@CrossOrigin//DELETE THIS WHEN WILL CONFIGURING THE CORS
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/list/by/deck/{idDeck}")
    @ApiOperation("Return a list of Question by Deck id")
    public ResponseEntity<Page<QuestionSummaryDTO>> getQuestionsByIdDeck(Pageable pageable, @PathVariable UUID idDeck){
        return ResponseEntity.ok(questionService.getQuestionByIdDeck(pageable, idDeck));
    }

    @GetMapping("/{idQuestion}")
    @ApiOperation("Return a Question by id")
    private ResponseEntity<QuestionDTO> getById(@PathVariable UUID idQuestion){
        return ResponseEntity.ok(questionService.getQuestionById(idQuestion));
    }
}

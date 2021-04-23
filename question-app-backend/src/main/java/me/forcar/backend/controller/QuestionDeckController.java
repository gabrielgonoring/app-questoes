package me.forcar.backend.controller;

import io.swagger.annotations.ApiOperation;
import me.forcar.backend.dto.question.QuestionDeckDTO;
import me.forcar.backend.dto.question.QuestionDeckSummaryDTO;
import me.forcar.backend.service.QuestionDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/question/deck")
@CrossOrigin//DELETE THIS WHEN WILL CONFIGURING THE CORS
//@Validated this annotation makes the getDecks method crash
public class QuestionDeckController {

    @Autowired
    private QuestionDeckService questionDeckService;

    @GetMapping("/list")
    @ApiOperation("Return a list of Question Decks by filters")
    private ResponseEntity<Page<QuestionDeckSummaryDTO>> getDecks(Pageable pageable){
        return ResponseEntity.ok(questionDeckService.getDecks(pageable));
    }

    @GetMapping("/{idQuestionDeck}")
    @ApiOperation("Return a Question Deck by id")
    public ResponseEntity<QuestionDeckDTO> getDeckById(@PathVariable UUID idQuestionDeck){
        return ResponseEntity.ok(questionDeckService.getDeckById(idQuestionDeck));
    }
}

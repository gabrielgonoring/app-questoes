package me.forcar.backend.jpa.repository.question;

import me.forcar.backend.jpa.po.question.QuestionPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface QuestionPORepository extends JpaRepository<QuestionPO, UUID> {

    Page<QuestionPO> findAllByQuestionDeckId(Pageable pageable, UUID questionDeckId);

    //SELECT DISTINCT q FROM QuestionPO q JOIN FETCH q.questionDeck a WHERE q.questionDeck.id = ?1
    @Query("SELECT q FROM QuestionPO q JOIN FETCH q.questionDeck JOIN FETCH q.alternatives a WHERE q.id = ?1")
    QuestionPO findFetchById(UUID idQuestion);
}

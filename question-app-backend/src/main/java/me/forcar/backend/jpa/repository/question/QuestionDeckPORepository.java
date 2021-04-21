package me.forcar.backend.jpa.repository.question;

import me.forcar.backend.jpa.po.question.QuestionDeckPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionDeckPORepository extends JpaRepository<QuestionDeckPO, UUID> {
}

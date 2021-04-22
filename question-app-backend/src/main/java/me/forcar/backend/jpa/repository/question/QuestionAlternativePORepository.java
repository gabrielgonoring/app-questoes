package me.forcar.backend.jpa.repository.question;

import me.forcar.backend.jpa.po.question.QuestionAlternativePO;
import me.forcar.backend.jpa.po.question.QuestionPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface QuestionAlternativePORepository extends JpaRepository<QuestionAlternativePO, UUID> {

}

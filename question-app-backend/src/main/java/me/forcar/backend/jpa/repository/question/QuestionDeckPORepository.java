package me.forcar.backend.jpa.repository.question;

import me.forcar.backend.jpa.po.question.QuestionDeckPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface QuestionDeckPORepository extends JpaRepository<QuestionDeckPO, UUID> {

    @Query("SELECT DISTINCT d FROM QuestionDeckPO d JOIN FETCH d.attributes a WHERE d.id = ?1")
    QuestionDeckPO findFetchAttributesById(UUID id);
}

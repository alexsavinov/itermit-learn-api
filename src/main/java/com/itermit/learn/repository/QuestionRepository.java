package com.itermit.learn.repository;

import com.itermit.learn.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(
            value = "select * from question where " +
                    "(:categoryId = 0 or :categoryId != 0 and question.category_id=:categoryId) and" +
                    "(:sourceId = 0 or :sourceId != 0 and question.source_id=:sourceId) " +
                    "order by RANDOM() limit :limit",
            nativeQuery = true
    )
    List<Question> findRandom(Long categoryId, Long sourceId, Integer limit);
}

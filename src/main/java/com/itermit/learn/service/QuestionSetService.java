package com.itermit.learn.service;

import com.itermit.learn.model.dto.request.CreateQuestionSetRequest;
import com.itermit.learn.model.dto.request.UpdateQuestionSetRequest;
import com.itermit.learn.model.entity.QuestionSet;

import java.util.List;


public interface QuestionSetService extends PageableQuestionSetService {

    QuestionSet findById(Long id);

    QuestionSet create(CreateQuestionSetRequest createRequest);

    QuestionSet update(UpdateQuestionSetRequest updateRequest);

    void delete(Long id);

    QuestionSet removeQuizzes(Long id, List<Long> ids);

    QuestionSet removeQuestions(Long id, List<Long> ids);

}

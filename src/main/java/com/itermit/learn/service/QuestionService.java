package com.itermit.learn.service;

import com.itermit.learn.model.dto.request.CreateQuestionRequest;
import com.itermit.learn.model.dto.request.UpdateQuestionRequest;
import com.itermit.learn.model.entity.Question;
import org.springframework.web.multipart.MultipartFile;


public interface QuestionService extends PageableQuestionService {

    Question findById(Long id);

    Question create(CreateQuestionRequest createRequest);

    Question update(UpdateQuestionRequest updateRequest);

    void delete(Long id);

    String saveImage(MultipartFile image);
}

package com.itermit.learn.service;

import com.itermit.learn.model.dto.request.CreateQuizRequest;
import com.itermit.learn.model.dto.request.UpdateQuizRequest;
import com.itermit.learn.model.entity.Quiz;
import org.springframework.web.multipart.MultipartFile;


public interface QuizService extends PageableQuizService {

    Quiz findById(Long id);

    Quiz findByQuizAnswerId(Long id);

    Quiz create(CreateQuizRequest createRequest);

    Quiz update(UpdateQuizRequest updateRequest);

    void delete(Long id);

    String saveImage(MultipartFile image);
}

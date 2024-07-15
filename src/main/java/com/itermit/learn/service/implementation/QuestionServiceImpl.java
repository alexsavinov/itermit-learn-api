package com.itermit.learn.service.implementation;

import com.itermit.learn.exception.ResourceNotFoundException;
import com.itermit.learn.model.dto.request.CreateQuestionRequest;
import com.itermit.learn.model.dto.request.UpdateQuestionRequest;
import com.itermit.learn.model.entity.Question;
import com.itermit.learn.repository.PageableQuestionRepository;
import com.itermit.learn.repository.QuestionRepository;
import com.itermit.learn.repository.specification.QuestionSpecs;
import com.itermit.learn.service.QuestionService;
import com.itermit.learn.service.mapper.QuestionMapper;
import com.itermit.learn.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final PageableQuestionRepository pageableQuestionRepository;
    private final QuestionMapper questionMapper;
    private final FileUtils fileUtils;

    @Override
    public Question findById(Long id) {
        log.debug("Looking for an question with id {}", id);

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requested resource not found (id = %s)"
                        .formatted(id)
                ));

        log.info("Retrieved an question with id {}", id);
        return question;
    }

    @Override
    public Page<Question> findAll(Pageable pageable, Map<String, String> params) {
        log.debug("Retrieving questions. Page request: {}", pageable);

        Page<Question> questions = pageableQuestionRepository.findAll(QuestionSpecs.filter(params), pageable);

        log.info("Retrieved {} questions of {} total", questions.getSize(), questions.getTotalElements());
        return questions;
    }

    @Transactional
    @Override
    public Question create(CreateQuestionRequest createRequest) {
        log.debug("Creating a new question");

        Question newQuestion = questionMapper.toQuestion(createRequest);

        Question createdQuestion = questionRepository.save(newQuestion);

        log.info("Created a new question with id {}", createdQuestion.getId());
        return createdQuestion;
    }

    @Transactional
    @Modifying
    @Override
    public Question update(UpdateQuestionRequest updateRequest) {
        log.debug("Updating question");

        Question foundQuestion = findById(updateRequest.getId());

        questionMapper.toQuestion(updateRequest, foundQuestion);

        Question updatedQuestion = questionRepository.save(foundQuestion);

        log.info("Updated an question with id {}", updatedQuestion.getId());
        return updatedQuestion;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Deleting question with id {}", id);

        Question foundQuestion = findById(id);

        questionRepository.delete(foundQuestion);

        log.info("Question with id {} is deleted", foundQuestion.getId());
    }

    @Override
    public String saveImage(MultipartFile image) {
        log.debug("Saving question image");

        String fileName = fileUtils.saveQuestionImage(image);

        log.info("Saved question image with filename: {}", fileName);

        return fileName;
    }
}

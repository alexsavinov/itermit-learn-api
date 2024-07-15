package com.itermit.learn.service.implementation;

import com.itermit.learn.exception.ResourceNotFoundException;
import com.itermit.learn.model.dto.request.CreateQuizRequest;
import com.itermit.learn.model.dto.request.UpdateQuizRequest;
import com.itermit.learn.model.entity.Quiz;
import com.itermit.learn.model.entity.QuizAnswer;
import com.itermit.learn.repository.*;
import com.itermit.learn.repository.specification.QuizSpecs;
import com.itermit.learn.service.CategoryService;
import com.itermit.learn.service.QuizService;
import com.itermit.learn.service.SourceService;
import com.itermit.learn.service.mapper.QuizMapper;
import com.itermit.learn.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final PageableQuizRepository pageableQuizRepository;
    private final QuizMapper quizMapper;
    private final FileUtils fileUtils;

    @Override
    public Quiz findById(Long id) {
        log.debug("Looking for an quiz with id {}", id);

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requested resource not found (id = %s)"
                        .formatted(id)
                ));

        log.info("Retrieved an quiz with id {}", id);
        return quiz;
    }

    @Override
    public Quiz findByQuizAnswerId(Long id) {
        log.debug("Looking for an quiz with QuizAnswer id {}", id);

        Quiz quiz = quizRepository.findByQuizAnswersContaining(QuizAnswer.builder().id(id).build())
                .orElseThrow(() -> new ResourceNotFoundException("Requested resource not found (QuizAnswer id = %s)"
                        .formatted(id)
                ));

        log.info("Retrieved an quiz with QuizAnswer id {}", id);
        return quiz;
    }

    @Override
    public Page<Quiz> findAll(Pageable pageable, Map<String, String> params) {
        log.debug("Retrieving quizzes. Page request: {}", pageable);

        Page<Quiz> quizzes = pageableQuizRepository.findAll(QuizSpecs.filter(params), pageable);

        log.info("Retrieved {} quizzes of {} total", quizzes.getSize(), quizzes.getTotalElements());
        return quizzes;
    }

    @Transactional
    @Override
    public Quiz create(CreateQuizRequest createRequest) {
        log.debug("Creating a new quiz");

        Quiz newQuiz = quizMapper.toQuiz(createRequest);

        Quiz createdQuiz = quizRepository.save(newQuiz);

        log.info("Created a new quiz with id {}", createdQuiz.getId());
        return createdQuiz;
    }

    @Transactional
    @Override
    public Quiz update(UpdateQuizRequest updateRequest) {
        log.debug("Updating quiz");

        Quiz foundQuiz = findById(updateRequest.getId());
        quizMapper.toQuiz(updateRequest, foundQuiz);

        Quiz updatedQuiz = quizRepository.save(foundQuiz);

        log.info("Updated an quiz with id {}", updatedQuiz.getId());
        return updatedQuiz;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Deleting quiz with id {}", id);

        Quiz foundQuiz = findById(id);

        quizRepository.delete(foundQuiz);

        log.info("Quiz with id {} is deleted", foundQuiz.getId());
    }

    @Override
    public String saveImage(MultipartFile image) {
        log.debug("Saving quiz image");

        String fileName = fileUtils.saveQuestionImage(image);

        log.info("Saved quiz image with filename: {}", fileName);

        return fileName;
    }
}

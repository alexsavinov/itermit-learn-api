package com.itermit.learn.service.implementation;

import com.itermit.learn.exception.ResourceNotFoundException;
import com.itermit.learn.model.dto.request.CreateQuestionSetRequest;
import com.itermit.learn.model.dto.request.UpdateQuestionSetRequest;
import com.itermit.learn.model.entity.QuestionSet;
import com.itermit.learn.repository.*;
import com.itermit.learn.repository.specification.QuestionSetSpecs;
import com.itermit.learn.service.QuestionSetService;
import com.itermit.learn.service.mapper.QuestionSetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionSetServiceImpl implements QuestionSetService {

    private final QuestionSetRepository questionSetRepository;
    private final PageableQuestionSetRepository pageableQuestionSetRepository;
    private final QuestionSetMapper questionSetMapper;


    @Override
    public QuestionSet findById(Long id) {
        log.debug("Looking for QuestionSet with id {}", id);

        QuestionSet questionSet = questionSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requested resource not found (id = %s)"
                        .formatted(id)
                ));

        log.info("Retrieved QuestionSet with id {}", id);
        return questionSet;
    }

    @Override
    public Page<QuestionSet> findAll(Pageable pageable, Map<String, String> params) {
        log.debug("Retrieving QuestionSets. Page request: {}", pageable);

        Page<QuestionSet> questionSets = pageableQuestionSetRepository.findAll(QuestionSetSpecs.filter(params), pageable);

        log.info("Retrieved {} QuestionSets of {} total", questionSets.getSize(), questionSets.getTotalElements());
        return questionSets;
    }

    @Transactional
    @Override
    public QuestionSet create(CreateQuestionSetRequest createRequest) {
        log.debug("Creating new QuestionSet");

        QuestionSet newQuestionSet = questionSetMapper.toQuestionSet(createRequest);
        QuestionSet createdQuestionSet = questionSetRepository.save(newQuestionSet);

        log.info("Created new QuestionSet with id {}", createdQuestionSet.getId());
        return createdQuestionSet;
    }

    @Transactional
    @Override
    public QuestionSet update(UpdateQuestionSetRequest updateRequest) {
        log.debug("Updating QuestionSet");

        Long id = updateRequest.getId();
        QuestionSet foundQuestionSet = findById(id);

        questionSetMapper.toQuestionSet(updateRequest, foundQuestionSet);

        try {
            QuestionSet updatedQuestionSet = questionSetRepository.save(foundQuestionSet);
            log.info("Updated QuestionSet with id {}", id);
            return updatedQuestionSet;
        } catch (JpaObjectRetrievalFailureException e) {
            log.error("Failed to update QuestionSet with id {}", id);
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Deleting QuestionSet with id {}", id);

        QuestionSet foundQuestionSet = findById(id);

        questionSetRepository.delete(foundQuestionSet);

        log.info("Question set with id {} is deleted", foundQuestionSet.getId());
    }

    @Transactional
    @Override
    public QuestionSet removeQuizzes(Long id, List<Long> ids) {
        log.debug("Removing Quizzes from the QuestionSet");

        QuestionSet foundQuestionSet = findById(id);

        foundQuestionSet.getQuizzes().removeIf(a -> ids.contains(a.getId()));

        QuestionSet updatedQuestionSet = questionSetRepository.save(foundQuestionSet);

        log.info("Removed Quizzes (id={}) from QuestionSet with id {}", ids, id);
        return updatedQuestionSet;
    }

    @Transactional
    @Override
    public QuestionSet removeQuestions(Long id, List<Long> ids) {
        log.debug("Removing Questions from the QuestionSet");

        QuestionSet foundQuestionSet = findById(id);

        foundQuestionSet.getQuestions().removeIf(a -> ids.contains(a.getId()));

        QuestionSet updatedQuestionSet = questionSetRepository.save(foundQuestionSet);

        log.info("Removed Questions (id={}) from QuestionSet with id {}", ids, id);
        return updatedQuestionSet;
    }
}

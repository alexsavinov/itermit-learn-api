package com.itermit.learn.service.implementation;

import com.itermit.learn.exception.ResourceNotFoundException;
import com.itermit.learn.model.dto.request.CreateSourceRequest;
import com.itermit.learn.model.dto.request.UpdateSourceRequest;
import com.itermit.learn.model.entity.Source;
import com.itermit.learn.repository.PageableSourceRepository;
import com.itermit.learn.repository.SourceRepository;
import com.itermit.learn.repository.specification.SourceSpecs;
import com.itermit.learn.service.SourceService;
import com.itermit.learn.service.mapper.SourceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService {

    private final PageableSourceRepository pageableSourceRepository;
    private final SourceRepository sourceRepository;
    private final SourceMapper sourceMapper;

    @Override
    public Source findById(Long id) {
        log.debug("Looking for an source with id {}", id);

        Source source = sourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requested resource not found (id = %s)"
                        .formatted(id)
                ));

        log.info("Retrieved an source with id {}", id);
        return source;
    }

    @Override
    public Page<Source> findAll(Pageable pageable, Map<String, String> params) {
        log.debug("Retrieving sources. Page request: {}", pageable);

        Page<Source> sources = pageableSourceRepository.findAll(SourceSpecs.filter(params), pageable);

        log.info("Retrieved {} sources of {} total", sources.getSize(), sources.getTotalElements());
        return sources;
    }

    @Transactional
    @Override
    public Source create(CreateSourceRequest createRequest) {
        log.debug("Creating a new source");

        Source newSource = sourceMapper.toSource(createRequest);
        Source createdSource = sourceRepository.save(newSource);

        log.info("Created a new source with id {}", createdSource.getId());
        return createdSource;
    }

    @Transactional
    @Override
    public Source update(UpdateSourceRequest updateRequest) {
        log.debug("Updating source");

        Source foundSource = findById(updateRequest.getId());
        sourceMapper.toSource(updateRequest, foundSource);

        Source updatedSource = sourceRepository.save(foundSource);

        log.info("Updated an source with id {}", updatedSource.getId());
        return updatedSource;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Deleting source with id {}", id);

        Source foundSource = findById(id);

        sourceRepository.delete(foundSource);

        log.info("Source with id {} is deleted", foundSource.getId());
    }
}

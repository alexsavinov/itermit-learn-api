package com.itermit.learn.service;

import com.itermit.learn.model.dto.request.CreateSourceRequest;
import com.itermit.learn.model.dto.request.UpdateSourceRequest;
import com.itermit.learn.model.entity.Source;


public interface SourceService extends PageableSourceService {

    Source findById(Long id);

    Source create(CreateSourceRequest createRequest);

    Source update(UpdateSourceRequest updateRequest);

    void delete(Long id);
}

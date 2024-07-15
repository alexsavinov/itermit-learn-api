package com.itermit.learn.service;

import com.itermit.learn.model.entity.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface PageableSourceService {

    Page<Source> findAll(Pageable pageable, Map<String, String> params);
}

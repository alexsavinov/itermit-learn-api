package com.itermit.learn.service;

import com.itermit.learn.model.dto.request.CreateArticleRequest;
import com.itermit.learn.model.dto.request.UpdateArticleRequest;
import com.itermit.learn.model.entity.Article;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService extends PageableArticleService {

    Article findById(Long id);

    Article create(CreateArticleRequest createRequest);

    Article update(UpdateArticleRequest updateRequest);

    void delete(Long id);

    String saveImage(MultipartFile avatar);
}

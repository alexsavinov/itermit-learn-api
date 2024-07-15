package com.itermit.learn.service.implementation;

import com.itermit.learn.exception.ResourceNotFoundException;
import com.itermit.learn.model.dto.request.CreateArticleRequest;
import com.itermit.learn.model.dto.request.UpdateArticleRequest;
import com.itermit.learn.repository.ArticleRepository;
import com.itermit.learn.repository.PageableArticleRepository;
import com.itermit.learn.model.entity.Article;
import com.itermit.learn.repository.specification.ArticleSpecs;
import com.itermit.learn.service.ArticleService;
import com.itermit.learn.service.mapper.ArticleMapper;
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
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final PageableArticleRepository pageableArticleRepository;
    private final ArticleMapper articleMapper;
    private final FileUtils fileUtils;

    @Override
    public Article findById(Long id) {
        log.debug("Looking for an article with id {}", id);

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requested resource not found (id = %s)"
                        .formatted(id)
                ));

        log.info("Retrieved an article with id {}", id);
        return article;
    }

    @Override
    public Page<Article> findAll(Pageable pageable, Map<String, String> params) {
        log.debug("Retrieving articles. Page request: {}", pageable);

        Page<Article> articles = pageableArticleRepository.findAll(ArticleSpecs.filter(params), pageable);

        log.info("Retrieved {} articles of {} total", articles.getSize(), articles.getTotalElements());
        return articles;
    }

    @Override
    public Article create(CreateArticleRequest createRequest) {
        log.debug("Creating a new article");

        Article newArticle = articleMapper.toArticle(createRequest);

        Article createdArticle = articleRepository.save(newArticle);

        log.info("Created a new article with id {}", createdArticle.getId());
        return createdArticle;
    }

    @Override
    public Article update(UpdateArticleRequest updateRequest) {
        log.debug("Updating article");

        Article foundArticle = findById(updateRequest.getId());

        articleMapper.toArticle(updateRequest, foundArticle);

        Article updatedArticle = articleRepository.save(foundArticle);

        log.info("Updated an article with id {}", updatedArticle.getId());
        return updatedArticle;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Deleting article with id {}", id);

        Article foundArticle = findById(id);

        articleRepository.delete(foundArticle);

        log.info("Article with id {} is deleted", foundArticle.getId());
    }

    @Override
    public String saveImage(MultipartFile image) {
        log.debug("Saving article image");

        String fileName = fileUtils.saveArticleImage(image);

        log.info("Saved article image with filename: {}", fileName);

        return fileName;
    }
}

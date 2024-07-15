package com.itermit.learn.model.dto;

import com.itermit.learn.model.entity.Article;
import com.itermit.learn.service.mapper.ArticleMapper;
import com.itermit.learn.controller.ArticleController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class ArticlesDto extends RepresentationModelAssemblerSupport<Article, ArticleDto> {

    private final ArticleMapper articleMapper;

    public ArticlesDto(ArticleMapper articleMapper) {
        super(ArticleController.class, ArticleDto.class);
        this.articleMapper = articleMapper;
    }

    @Override
    public ArticleDto toModel(Article entity) {
        ArticleDto articleDto = articleMapper.toBasicDto(entity);
        articleDto.add(linkTo(methodOn(ArticleController.class).getArticleById(articleDto.getId())).withSelfRel());
        return articleDto;
    }
}

package com.itermit.learn.service.mapper;

import com.itermit.learn.model.dto.*;
import com.itermit.learn.model.dto.request.CreateArticleRequest;
import com.itermit.learn.model.dto.request.UpdateArticleRequest;
import com.itermit.learn.model.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ArticleMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final UserMapper userMapper = new UserMapper();

    {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        Converter<User, UserDto> converterToDto = c -> userMapper.toBasicDto(c.getSource());
        Converter<UserDto, User> converterUser = c -> userMapper.toUser(c.getSource());

        /* DTO */
        modelMapper.emptyTypeMap(Article.class, ArticleDto.class).addMappings(m -> {
            m.skip(ArticleDto::setAuthor);
            m.when(Conditions.isNotNull()).using(converterToDto)
                    .map(Article::getAuthor, ArticleDto::setAuthor);
        }).implicitMappings();

        /* Update request */
        modelMapper.emptyTypeMap(UpdateArticleRequest.class, Article.class).addMappings(m -> {
            m.skip(Article::setAuthor);
            m.when(Conditions.isNotNull()).using(converterUser)
                    .map(UpdateArticleRequest::getAuthor, Article::setAuthor);
        }).implicitMappings();
    }

    public ArticleDto toDto(Article article) {
        return modelMapper.map(article, ArticleDto.class);
    }

    public ArticleDto toIdDto(Article article) {
        return ArticleDto.builder().id(article.getId()).build();
    }

    public ArticleDto toBasicDto(Article article) {
        ArticleDto dto = modelMapper.map(article, ArticleDto.class);
        dto.setContent(null);
        dto.setDescription(null);

        return dto;
    }

    public Article toArticle(CreateArticleRequest request) {
        return modelMapper.map(request, Article.class);
    }

    public void toArticle(UpdateArticleRequest request, Article article) {
        modelMapper.map(request, article);
    }
}

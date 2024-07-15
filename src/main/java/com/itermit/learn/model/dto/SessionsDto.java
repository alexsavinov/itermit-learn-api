package com.itermit.learn.model.dto;

import com.itermit.learn.controller.SessionController;
import com.itermit.learn.model.entity.Session;
import com.itermit.learn.service.mapper.SessionMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class SessionsDto extends RepresentationModelAssemblerSupport<Session, SessionDto> {

    private final SessionMapper sessionsMapper;

    public SessionsDto(SessionMapper sessionsMapper) {
        super(SessionController.class, SessionDto.class);
        this.sessionsMapper = sessionsMapper;
    }

    @Override
    public @NotNull SessionDto toModel(@NotNull Session entity) {
        SessionDto sessionsDto = sessionsMapper.toBasicDto(entity);
        sessionsDto.add(linkTo(methodOn(SessionController.class).getSessionById(sessionsDto.getId())).withSelfRel());
        return sessionsDto;
    }
}

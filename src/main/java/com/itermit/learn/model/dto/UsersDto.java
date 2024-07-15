package com.itermit.learn.model.dto;

import com.itermit.learn.model.entity.User;
import com.itermit.learn.service.mapper.UserMapper;
import com.itermit.learn.controller.UserController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class UsersDto extends RepresentationModelAssemblerSupport<User, UserDto> {

    private final UserMapper userMapper;

    public UsersDto(UserMapper userMapper) {
        super(UserController.class, UserDto.class);
        this.userMapper = userMapper;
    }

    @Override
    public UserDto toModel(User entity) {
        UserDto userDto = userMapper.toDto(entity);
        userDto.add(linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel());
        return userDto;
    }
}

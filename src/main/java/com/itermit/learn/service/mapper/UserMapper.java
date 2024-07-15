package com.itermit.learn.service.mapper;

import com.itermit.learn.model.dto.UserDto;
import com.itermit.learn.model.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public UserDto toDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .toList();

        userDto.setRoles(roles);

        return userDto;
    }

    public UserDto toIdDto(User user) {
        return UserDto.builder().id(user.getId()).build();
    }

    public UserDto toBasicDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public User toUser(UserDto user) {
        return modelMapper.map(user, User.class);
    }
}

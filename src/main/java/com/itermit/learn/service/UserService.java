package com.itermit.learn.service;

import com.itermit.learn.model.dto.request.CreateUserRequest;
import com.itermit.learn.model.dto.request.UpdatePasswordRequest;
import com.itermit.learn.model.dto.request.UpdateUserRequest;
import com.itermit.learn.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends PageableUserService {

    User findById(Long id);

    User findByName(String name);

    User findByUsername(String name);

    User findByEmail(String name);

    User create(CreateUserRequest createRequest);

    User update(UpdateUserRequest updateRequest);

    void updatePassword(UpdatePasswordRequest updateRequest);

    String updateAvatar(Long id, MultipartFile avatar);

    void delete(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void checkIdOfCurrentUser(Long id);
}

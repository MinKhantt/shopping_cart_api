package com.example.shoppingcartapi.service.user;

import com.example.shoppingcartapi.dto.request.CreateUserRequest;
import com.example.shoppingcartapi.dto.request.UserUpdateRequest;
import com.example.shoppingcartapi.model.User;

public interface IUserService {

    User getUserById(Long userId);

    User createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, long userId);

    void deleteUser(Long userId);
}

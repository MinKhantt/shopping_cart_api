package com.example.shoppingcartapi.service.user;

import com.example.shoppingcartapi.dto.UserDto;
import com.example.shoppingcartapi.dto.request.CreateUserRequest;
import com.example.shoppingcartapi.dto.request.UserUpdateRequest;
import com.example.shoppingcartapi.model.User;

import java.util.List;

public interface IUserService {

    UserDto createUser(CreateUserRequest request);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto getUserByEmail(String email);

    UserDto updateUser(UserUpdateRequest request, long userId);

    void deleteUser(Long userId);

    UserDto getAuthenticatedUser();
}

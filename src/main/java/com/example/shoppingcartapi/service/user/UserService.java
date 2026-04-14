package com.example.shoppingcartapi.service.user;

import com.example.shoppingcartapi.dto.UserDto;
import com.example.shoppingcartapi.dto.request.CreateUserRequest;
import com.example.shoppingcartapi.dto.request.UserUpdateRequest;
import com.example.shoppingcartapi.exception.AlreadyExistsException;
import com.example.shoppingcartapi.exception.ResourceNotFoundException;
import com.example.shoppingcartapi.mapper.UserMapper;
import com.example.shoppingcartapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(
                    "User " + request.getEmail() + " already exists!"
            );
        }

        var user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto getUserById(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User " + userId + " not found!"));

        return userMapper.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDto)
                .toList();
    }

    @Override
    public UserDto getUserByEmail(String email) {
        var user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new ResourceNotFoundException("User With Email: " + email + " not found!"));

        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserUpdateRequest request, long userId) {
        var existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User " + userId + " not found!"));

//        existingUser.setFirstName(request.getFirstName());
//        existingUser.setLastName(request.getLastName());
        userMapper.updateUserFromRequest(request, existingUser);
        userRepository.save(existingUser);
        return userMapper.userToUserDto(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                        .ifPresentOrElse(userRepository :: delete, () -> {
                            throw new ResourceNotFoundException("User not found");
                        });
    }

    @Override
    public UserDto getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        var user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));

        return userMapper.userToUserDto(user);
    }
}

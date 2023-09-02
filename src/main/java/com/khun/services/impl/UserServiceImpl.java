package com.khun.services.impl;

import com.khun.dto.UserDto;
import com.khun.dto.UserReqDto;
import com.khun.entity.User;
import com.khun.repo.UserRepository;
import com.khun.services.UserService;

import java.lang.reflect.Type;

import com.khun.utils.Role;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll().stream().filter(user -> Role.USER.getValue() == user.getRole()).toList();
        Type listType = new TypeToken<List<UserDto>>() {
        }.getType();
        return modelMapper.map(users, listType);
    }

    @Override
    public Optional<User> getOneById(String id) {
        return userRepository.findById(id);
    }


    @Override
    public boolean save(UserReqDto userReqDto) {
        User user = modelMapper.map(userReqDto, User.class);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean update(UserReqDto userReqDto, boolean isOldPass) {
        Optional<User> optionalUser = userRepository.findById(userReqDto.getId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            modelMapper.map(userReqDto, existingUser);
            userRepository.save(existingUser);
            return true;
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userReqDto.getId());
        }
    }

    @Override
    public Long next() {
        return userRepository.count();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public void disableOrActiveUser(String userId, int userStatus) {
        userRepository.disableOrActivateUser(userId, userStatus);
    }


}

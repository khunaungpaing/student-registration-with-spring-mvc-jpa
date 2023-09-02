package com.khun.services;

import com.khun.dto.UserDto;
import com.khun.dto.UserReqDto;
import com.khun.entity.User;
import com.khun.utils.Predictable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserService extends Predictable {
    List<UserDto> getAllUsers();

    Optional<User> getOneById(String id);

    boolean save(UserReqDto userReqDto) throws SQLException;

    boolean update(UserReqDto userReqDto, boolean isOldPass) throws SQLException;

    User getUserByEmail(String email);

    void disableOrActiveUser(String userId, int userStatus);
}

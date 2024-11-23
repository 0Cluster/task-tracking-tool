package com.cluster.tasktrackingtool.config;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cluster.tasktrackingtool.dto.SignupRequest;
import com.cluster.tasktrackingtool.dto.UserDTO;
import com.cluster.tasktrackingtool.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "password", ignore = true)
  User signupRequestToUser(SignupRequest signupRequest);

  UserDTO userToUserDto(User user);
}

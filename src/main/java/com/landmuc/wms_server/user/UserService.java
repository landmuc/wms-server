package com.landmuc.wms_server.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.landmuc.wms_server.domain.exception.UserNotFoundException;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  private UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findUserById(UUID userId) {
    UserEntity userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("No user found with id: " + userId));

    return userEntity.toUser();
  }

}

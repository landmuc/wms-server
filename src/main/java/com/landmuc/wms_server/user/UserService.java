package com.landmuc.wms_server.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  private UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserById(UUID requestedId) {
    Optional<UserEntity> userEntity = userRepository.findById(requestedId);

    if (userEntity.isEmpty()) {
      return null;
    }
    return userEntity.get().toUser();
  }

}

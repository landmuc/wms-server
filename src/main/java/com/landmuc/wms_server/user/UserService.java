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

  public User findUserById(UUID requestedId) {
    Optional<UserEntity> optionalUserEntity = userRepository.findById(requestedId);

    if (optionalUserEntity.isEmpty()) {
      return null;
    }
    return optionalUserEntity.get().toUser();
  }

}

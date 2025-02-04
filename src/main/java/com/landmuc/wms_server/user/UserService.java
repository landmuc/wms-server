package com.landmuc.wms_server.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.landmuc.wms_server.domain.exception.UserNotFoundException;

@Service
@PropertySource("classpath:messages.properties")
public class UserService {
  @Value("${exception.user}")
  private String exceptionUser;

  private final UserRepository userRepository;

  @Autowired
  private UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findUserById(UUID userId) {
    UserEntity userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(exceptionUser + userId));

    return userEntity.toUser();
  }

}

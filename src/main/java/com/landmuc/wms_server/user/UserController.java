package com.landmuc.wms_server.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.landmuc.wms_server.domain.exception.UserNotFoundException;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  private UserController(UserService userService) {
    this.userService = userService;
  };

  @GetMapping("/{userId}")
  private ResponseEntity<User> findUserById(@PathVariable UUID userId) {
    User user = userService.findUserById(userId);

    return ResponseEntity.ok(user);
  }

  @ExceptionHandler(UserNotFoundException.class)
  private ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", exception.getMessage()));
  }
}

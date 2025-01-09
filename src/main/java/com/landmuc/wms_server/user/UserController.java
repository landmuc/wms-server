package com.landmuc.wms_server.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  private UserController(UserService userService) {
    this.userService = userService;
  };

  @GetMapping("/{requestedId}")
  private ResponseEntity<User> getUserById(@PathVariable UUID requestedId) {
    User user = userService.getUserById(requestedId);

    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(user);
  }

}

package com.hello.java.web.controller;

import com.hello.java.domain.user.User;
import com.hello.java.service.UserService;
import com.hello.java.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public Long save(@RequestBody UserSaveRequestDto requestDto) {
        return userService.join(requestDto.toEntity());
    }

    @GetMapping("/user/{username}")
    public User findByName(@PathVariable("username") String username) {
        return userService.findByName(username);
    }

}

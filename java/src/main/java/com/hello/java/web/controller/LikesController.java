package com.hello.java.web.controller;

import com.hello.java.service.LikesService;
import com.hello.java.service.UserService;
import com.hello.java.web.dto.LikesRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LikesController {
    private final LikesService likesService;
    private final UserService userService;

    @PostMapping("/likes")
    public void addLikes(@RequestBody LikesRequestDto requestDto) {
        likesService.addLikes(userService.findById(requestDto.getUserId()), requestDto.getBoardId());
    }
}

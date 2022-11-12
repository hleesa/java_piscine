package com.hello.java.service;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.user.User;
import com.hello.java.web.dto.BoardSaveRequestDto;
import com.hello.java.web.dto.LikesRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class LikesServiceTest {

    @Autowired
    LikesService likesService;
    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;


    @Test
    public void 좋아요가_변한다() {
        //given
        /**
         * user 생성
         */
        String username1 = "salee2";
        String password1 = "7513";
        User user1 = User.builder()
                .username(username1)
                .password(password1)
                .build();
        userService.join(user1);
        String username2 = "himjeong";
        String password2 = "010";
        User user2 = User.builder()
                .username(username2)
                .password(password2)
                .build();
        userService.join(user2);
        /**
         * board 셍성
         */
        String title = "42gg";
        String content = "ping-ping";
        String tag = "game";
        BoardSaveRequestDto boardSaveRequestDto1 = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .userId(user1.getId())
                .build();

        //when
        Board savedBoard1 = boardService.save(boardSaveRequestDto1);

        //then
        likesService.addLikes(user1, savedBoard1.getId());
        likesService.addLikes(user2, savedBoard1.getId());
        assertThat(savedBoard1.getLikes().size()).isEqualTo(2);
        likesService.addLikes(user2, savedBoard1.getId());
        assertThat(savedBoard1.getLikes().size()).isEqualTo(1);
    }

}
package com.hello.java.service;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.board.BoardRepository;
import com.hello.java.domain.user.User;
import com.hello.java.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class LikesServiceTest {

    @Autowired
    LikesService likesService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 좋아요가_변한다() {
        //given
        String username1 = "salee2";
        String password1 = "7513";
        User user1 = User.builder()
                .username(username1)
                .password(password1)
                .build();
        userRepository.save(user1);
        String username2 = "himjeong";
        String password2 = "010";
        User user2 = User.builder()
                .username(username1)
                .password(password1)
                .build();
        userRepository.save(user2);

        String title = "42gg";
        String content = "ping-ping";
        String tag = "game";
        Board board = Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .user(user1)
                .build();
        Board savedBoard = boardRepository.save(board);
        //when
        likesService.addLikes(user1, savedBoard.getId());
        likesService.addLikes(user2, savedBoard.getId());
        //then
        assertThat(savedBoard.getLikes().size()).isEqualTo(2);
        likesService.addLikes(user2, savedBoard.getId());
        assertThat(savedBoard.getLikes().size()).isEqualTo(1);
    }

}
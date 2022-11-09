package com.hello.java.service;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.board.BoardRepository;
import com.hello.java.domain.user.User;
import com.hello.java.domain.user.UserRepository;
import com.hello.java.web.dto.BoardSaveRequestDto;
import com.hello.java.web.dto.BoardUpdateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;

    @Test
    public void 게시글이_저장된다() {

        // given
        /**
         * user 생성 및 저장
         */
        String username = "salee2";
        String password = "7513";
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        userService.join(user);
        /**
         * board 셍성 및 저장
         */
        String title = "42gg";
        String content = "ping-ping";
        String tag = "game";
        BoardSaveRequestDto boardSaveRequestDto = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .build();
        boardService.save(user.getId(), boardSaveRequestDto.toEntity());
        boardService.save(user.getId(), boardSaveRequestDto.toEntity());

        // when
        List<Board> boardList = boardService.findBoards().getBoardList();

        //then
        Board findBoard = boardList.get(0);
        assertThat(findBoard.getTitle()).isEqualTo(title);
        assertThat(findBoard.getContent()).isEqualTo(content);
        assertThat(findBoard.getLikes()).isEqualTo(0L);
        assertThat(findBoard.getTag()).isEqualTo(tag);
        assertThat(boardList.size()).isEqualTo(2);
    }

    @Test
    public void 게시글이_수정되다() {

        // given
        Board board = BoardSaveRequestDto.builder()
                .title("42gg")
                .content("salee2")
                .tag("#42gg")
                .build()
                .toEntity();

        boardRepository.save(board);

        // when
        List<Board> findBoards = boardRepository.findAll();

        String title = "42seoul";
        String content = "cadet";
        String tag = "#pipex";

        Board updateBoard = BoardUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .build()
                .toEntity();
        boardService.update(findBoards.get(0).getId(), updateBoard);

        //then
        Board findBoard = findBoards.get(0);
        assertThat(findBoard.getTitle()).isEqualTo(title);
        assertThat(findBoard.getContent()).isEqualTo(content);
        assertThat(findBoard.getLikes()).isEqualTo(0);
        assertThat(findBoard.getViews()).isEqualTo(0);
        assertThat(findBoard.getTag()).isEqualTo(tag);
    }

    @Test
    public void 좋아요가_변경된다() {

        String title = "42gg";
        String content = "salee";

        boardRepository.save(BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build()
                .toEntity());

        // when
        List<Board> findBoards = boardRepository.findAll();

        //then
        Long deltaLikes1 = 10L;
        Board findBoard = findBoards.get(0);

        boardService.updateLikes(findBoard.getId(), Boolean.TRUE);
        assertThat(findBoard.getLikes()).isEqualTo(1);
        boardService.updateLikes(findBoard.getId(), Boolean.FALSE);
        assertThat(findBoard.getLikes()).isEqualTo(0);
    }

    @Test
    public void 조회수가_올라간다() {

        String title = "42gg";
        String content = "salee";

        boardRepository.save(BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build()
                .toEntity());

        // when
        List<Board> findBoards = boardRepository.findAll();

        //then
        Board findBoard = findBoards.get(0);

        assertThat(findBoard.getViews()).isEqualTo(0);
        for (int i = 0; i < 42; ++i) {
            findBoard.updateViews();
        }
        assertThat(findBoard.getViews()).isEqualTo(42);
    }

    @Test
    public void 유저가작성한_게시글이_조회된다() {

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
        BoardSaveRequestDto boardSaveRequestDto = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .build();
        //when
        Board board1 = boardService.save(user1.getId(), boardSaveRequestDto.toEntity());

        Board board2 = boardService.save(user2.getId(), boardSaveRequestDto.toEntity());
        Board board3 = boardService.save(user2.getId(), boardSaveRequestDto.toEntity());

        //then
        List<Board> findBoards1 = boardService.findBoardsByUsername(user1.getUsername());
        List<Board> findBoards2 = boardService.findBoardsByUsername(user2.getUsername());

        assertThat(findBoards1.size()).isEqualTo(1);
        assertThat(findBoards2.size()).isEqualTo(2);
    }
}
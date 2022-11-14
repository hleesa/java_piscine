package com.hello.java.service;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.board.BoardRepository;
import com.hello.java.domain.user.User;
import com.hello.java.domain.user.UserRepository;
import com.hello.java.web.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        String username = "salee2";
        String password = "7513";
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        userRepository.save(user);

        String title = "42gg";
        String content = "ping-ping";
        String tag = "game";
        Board board = Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .user(user)
                .build();

        boardRepository.save(board);

        // when
        List<Board> boardList = boardRepository.findAll();

        //then
        Board findBoard = boardList.get(0);
        assertThat(findBoard.getTitle()).isEqualTo(title);
        assertThat(findBoard.getContent()).isEqualTo(content);
        assertThat(findBoard.getTag()).isEqualTo(tag);
        assertThat(boardList.size()).isEqualTo(1);
    }

    @Test
    public void 게시글이_수정되다() {
        // given
        String username = "salee2";
        String password = "7513";
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        userRepository.save(user);

        Board board = Board.builder()
                .title("42gg")
                .content("salee2")
                .tag("#42gg")
                .user(user)
                .build();

        Board savedBoard = boardRepository.save(board);

        // when
        List<Board> boardList = boardRepository.findAll();

        String title = "42seoul";
        String content = "cadet";
        String tag = "#pipex";
        Long views = 5L;

        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .views(views)
                .boardId(boardList.get(0).getId())
                .build();
        Long updateBoardId = boardService.update(boardUpdateRequestDto);

        //then
        Board findBoard = boardRepository.findById(updateBoardId).orElseThrow();
        assertThat(findBoard.getTitle()).isEqualTo(title);
        assertThat(findBoard.getContent()).isEqualTo(content);
        assertThat(findBoard.getViews()).isEqualTo(views);
        assertThat(findBoard.getTag()).isEqualTo(tag);
    }

    @Test
    public void 조회수가_올라간다() {

        //given
        String username = "salee2";
        String password = "7513";
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        userRepository.save(user);

        String title = "42gg";
        String content = "salee";
        String tag = "#42gg";
        Board board = Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .views(0L)
                .user(user)
                .build();

        boardRepository.save(board);
        // when
        List<Board> boardList = boardRepository.findAll();

        //then
        Board findBoard = boardList.get(0);
        long views = 42;
        assertThat(findBoard.getViews()).isEqualTo(0);
        for (long i = 0; i < views; ++i) {
            boardService.findOne(findBoard.getId());
        }
        assertThat(findBoard.getViews()).isEqualTo(views);
    }

    @Test
    public void 유저가작성한_게시글이_조회된다() {

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
                .username(username2)
                .password(password2)
                .build();
        userRepository.save(user2);

        String title = "42gg";
        String content = "ping-ping";
        String tag = "game";

        Board board1 = Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .views(0L)
                .user(user1)
                .build();
        Board board2 = Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .views(0L)
                .user(user2)
                .build();
        Board board22 = Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .views(0L)
                .user(user2)
                .build();
        //when
        boardRepository.save(board1);

        boardRepository.save(board2);
        boardRepository.save(board22);

        //then
        BoardListResponseDto boardListDto1 = boardService.findBoardsByUsername(user1.getUsername());
        BoardListResponseDto boardListDto2 = boardService.findBoardsByUsername(user2.getUsername());
        assertThat(boardListDto1.getBoardDtoList().size()).isEqualTo(1);
        assertThat(boardListDto2.getBoardDtoList().size()).isEqualTo(2);
    }

    @Test
    public void 유저가_작성한글이_삭제된다() {

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
                .username(username2)
                .password(password2)
                .build();
        userRepository.save(user2);

        String title = "42gg";
        String content = "ping-ping";
        String tag = "game";

        Board board1 = Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .views(0L)
                .user(user1)
                .build();
        Board board2 = Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .views(0L)
                .user(user2)
                .build();
        boardRepository.save(board1);
        boardRepository.save(board2);

        //when
        BoardDeleteRequestDto user1DeleteBoard2 = BoardDeleteRequestDto.builder()
                .userId(user1.getId())
                .boardId(board2.getId())
                .build();
        BoardDeleteRequestDto user1DeleteBoard1 = BoardDeleteRequestDto.builder()
                .userId(user1.getId())
                .boardId(board1.getId())
                .build();

        List<Board> boardList = boardRepository.findAll();

        //then
        boardService.delete(user1DeleteBoard2);
        assertThat(boardList.size()).isEqualTo(2);
        boardService.delete(user1DeleteBoard1);
        boardList = boardRepository.findAll();
        assertThat(boardList.size()).isEqualTo(1);
    }

    @Test
    public void 유저가_작성한글이_페이지로_조회된다() {

        //given
        String username = "salee2";
        String password = "7513";
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        userRepository.save(user);

        String title = "42gg";
        String content = "ping-ping";
        String tag = "game";
        Board board = Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .views(0L)
                .user(user)
                .build();
        //when
        Board firstBoard = boardRepository.save(board);
        int totalBoards = 42;
        for (int i = 1; i < totalBoards; ++i) {
            boardRepository.save(Board.builder()
                    .title(title)
                    .content(content)
                    .tag(tag)
                    .views(0L)
                    .user(user)
                    .build());
        }

        //then
        int page = 1;
        int size = 5;
        Pageable pageable1 = PageRequest.of(page, size);
        Page<Board> boardList1 = boardService.findPageByUsername(username, pageable1);
        assertThat(boardList1.getContent().size()).isEqualTo(size);
        assertThat(boardList1.getContent().get(0).getId()).isEqualTo(size*page + firstBoard.getId());
        assertThat(boardList1.getTotalElements()).isEqualTo(totalBoards);
        assertThat(boardList1.getTotalPages()).isEqualTo((totalBoards+size-1)/size );
    }
}
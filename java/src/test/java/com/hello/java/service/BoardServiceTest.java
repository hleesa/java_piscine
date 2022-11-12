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
                .userId(user.getId())
                .build();
        boardService.save(boardSaveRequestDto);
        boardService.save(boardSaveRequestDto);

        // when

        List<BoardResponseDto> boardDtoList = boardService.finaAll().getBoardDtoList();

        //then
        BoardResponseDto boardResponseDto = boardDtoList.get(0);
        assertThat(boardResponseDto.getTitle()).isEqualTo(title);
        assertThat(boardResponseDto.getContent()).isEqualTo(content);
        assertThat(boardResponseDto.getTag()).isEqualTo(tag);
        assertThat(boardDtoList.size()).isEqualTo(2);
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
        userService.join(user);

        Board board = BoardSaveRequestDto.builder()
                .title("42gg")
                .content("salee2")
                .tag("#42gg")
                .userId(user.getId())
                .build()
                .toEntity();

        boardRepository.save(board);

        // when
        List<Board> findBoards = boardRepository.findAll();

        String title = "42seoul";
        String content = "cadet";
        String tag = "#pipex";

        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .boardId(findBoards.get(0).getId())
                .build();
        boardService.update(boardUpdateRequestDto);

        //then
        Board findBoard = findBoards.get(0);
        assertThat(findBoard.getTitle()).isEqualTo(title);
        assertThat(findBoard.getContent()).isEqualTo(content);
        assertThat(findBoard.getViews()).isEqualTo(0);
        assertThat(findBoard.getTag()).isEqualTo(tag);
    }

    /*
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
     */
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
        BoardSaveRequestDto boardSaveRequestDto1 = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .userId(user1.getId())
                .build();
        BoardSaveRequestDto boardSaveRequestDto2 = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .userId(user2.getId())
                .build();
        //when
        boardService.save(boardSaveRequestDto1);

        boardService.save(boardSaveRequestDto2);
        boardService.save(boardSaveRequestDto2);

        //then
        BoardListResponseDto boardListDto1 = boardService.findBoardsByUsername(user1.getUsername());
        BoardListResponseDto boardListDto2 = boardService.findBoardsByUsername(user2.getUsername());
        assertThat(boardListDto1.getBoardDtoList().size()).isEqualTo(1);
        assertThat(boardListDto2.getBoardDtoList().size()).isEqualTo(2);
    }

    @Test
    public void 유저가_작성한글이_삭제된다() {

        //given
        /**
         * user 생성 및 저장
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
         * board 셍성 및 저장
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
        BoardSaveRequestDto boardSaveRequestDto2 = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .userId(user2.getId())
                .build();
        Board board1 = boardService.save(boardSaveRequestDto1);
        Board board2 = boardService.save(boardSaveRequestDto2);
        //when
        BoardDeleteRequestDto user1Board2DelDto = BoardDeleteRequestDto.builder()
                .userId(user1.getId())
                .boardId(board2.getId())
                .build();
        BoardDeleteRequestDto user1Board1DelDto = BoardDeleteRequestDto.builder()
                .userId(user1.getId())
                .boardId(board1.getId())
                .build();

        BoardListResponseDto boardDtoList = boardService.findBoardsByUsername(user1.getUsername());

        //then
        boardService.delete(user1Board2DelDto);
        assertThat(boardDtoList.getSize()).isEqualTo(1);
        assertThat(boardDtoList.getSize()).isEqualTo(1);
        boardService.delete(user1Board1DelDto);
        boardDtoList = boardService.findBoardsByUsername(user1.getUsername());
        assertThat(boardDtoList.getSize()).isEqualTo(0);
    }

    @Test
    public void 유저가_작성한글이_페이지로_조회된다() {

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
                .userId(user1.getId())
                .build();
        //when
        Board firstBoard = boardService.save(boardSaveRequestDto);
        int totalBoards = 42;
        for(int i=1; i <totalBoards; ++i)
            boardService.save(boardSaveRequestDto);

        //then
        int page = 1;
        int size = 5;
        Pageable pageable1 = PageRequest.of(page, size);
        Page<Board> boardList1 = boardService.findPageByUsername(username1, pageable1);
        assertThat(boardList1.getContent().size()).isEqualTo(size);
        assertThat(boardList1.getContent().get(0).getId()).isEqualTo(size*page + firstBoard.getId());
        assertThat(boardList1.getTotalElements()).isEqualTo(totalBoards);
        assertThat(boardList1.getTotalPages()).isEqualTo((totalBoards+size-1)/size );
    }
}
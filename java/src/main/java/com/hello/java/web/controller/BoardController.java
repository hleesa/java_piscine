package com.hello.java.web.controller;


import com.hello.java.domain.board.Board;
import com.hello.java.service.BoardService;
import com.hello.java.service.UserService;
import com.hello.java.web.dto.BoardDeleteRequestDto;
import com.hello.java.web.dto.BoardSaveRequestDto;
import com.hello.java.web.dto.BoardUpdateRequestDto;
import com.hello.java.web.dto.BoardListResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/board")
    public Board save(@RequestBody BoardSaveRequestDto requestDto) {
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .tag(requestDto.getTag())
                .likes(requestDto.getLikes())
                .views(requestDto.getViews())
                .user(userService.findById(requestDto.getUserId()))
                .build();

        return boardService.save(requestDto.getUserId(), board);
    }

    @GetMapping("/board/{boardId}")
    public Board findBoard(@PathVariable("boardId") Long boardId) {
        boardService.updateViews(boardId);
        return boardService.findOne(boardId).orElseThrow();
    }

    @GetMapping("/board")
    public BoardListResponseDto findBoards() {
        return boardService.findBoards();
    }

    @PutMapping("/board/{boardId}")
    public Long updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        return boardService.update(boardId, boardUpdateRequestDto.toEntity());
    }

    @DeleteMapping("/board")
    public void deleteBoard(@RequestBody BoardDeleteRequestDto requestDto) {
        boardService.delete(requestDto);
    }

    @PutMapping("/board/like/{boardId}")
    public void updateLikes(@PathVariable("boardId") Long boardId, @RequestParam Boolean isLike) {
        boardService.updateLikes(boardId, isLike);
    }

    @GetMapping("/boards/{username}")
    public List<Board> findBoardsByUsername(@PathVariable("username") String username) {
        return boardService.findBoardsByUsername(username);
    }


}

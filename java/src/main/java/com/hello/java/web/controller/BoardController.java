package com.hello.java.web.controller;


import com.hello.java.domain.board.Board;
import com.hello.java.service.BoardService;
import com.hello.java.web.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/board")
    public Board save(@RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(requestDto);
    }

    @GetMapping("/board/{boardId}")
    public BoardResponseDto findOne(@PathVariable("boardId") Long boardId) {
        return boardService.findOne(boardId);
    }

    @GetMapping("/board")
    public BoardListResponseDto findAll() {
        return boardService.finaAll();
    }

    @PutMapping("/board/{boardId}")
    public Long update(@PathVariable("boardId") Long boardId, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        boardUpdateRequestDto.setBoardId(boardId);
        return boardService.update(boardUpdateRequestDto);
    }

    @DeleteMapping("/board")
    public void delete(@RequestBody BoardDeleteRequestDto requestDto) {
        boardService.delete(requestDto);
    }

    @GetMapping("/boards/{username}")
    public BoardListResponseDto findAllByUsername(@PathVariable("username") String username) {
        return boardService.findBoardsByUsername(username);
    }
    @GetMapping("/boards/page/{username}")
    public Page<Board> findPageByUsername(@PathVariable("username") String username, Pageable pageable) {
        return boardService.findPageByUsername(username, pageable);
    }
}

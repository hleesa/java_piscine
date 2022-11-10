package com.hello.java.web.controller;


import com.hello.java.domain.board.Board;
import com.hello.java.service.BoardService;
import com.hello.java.web.dto.BoardDeleteRequestDto;
import com.hello.java.web.dto.BoardSaveRequestDto;
import com.hello.java.web.dto.BoardUpdateRequestDto;
import com.hello.java.web.dto.BoardListResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/board")
    public Board save(@RequestBody BoardSaveRequestDto requestDto) {
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .tag(requestDto.getTag())
                .likes(requestDto.getLikes())
                .views(requestDto.getViews())
                .build();
        return boardService.save(requestDto.getUserId(), board);
    }

    @GetMapping("/board/{boardId}")
    public Board findOne(@PathVariable("boardId") Long boardId) {
        boardService.updateViews(boardId);
        return boardService.findOne(boardId).orElseThrow();
    }

    @GetMapping("/board")
    public BoardListResponseDto findAll() {
        return boardService.finaAll();
    }

    @PutMapping("/board/{boardId}")
    public Long update(@PathVariable("boardId") Long boardId, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        return boardService.update(boardId, boardUpdateRequestDto.toEntity());
    }

    @DeleteMapping("/board")
    public void delete(@RequestBody BoardDeleteRequestDto requestDto) {
        boardService.delete(requestDto);
    }

    @PutMapping("/board/like/{boardId}")
    public void updateLikes(@PathVariable("boardId") Long boardId, @RequestParam Boolean isLike) {
        boardService.updateLikes(boardId, isLike);
    }

    @GetMapping("/boards/{username}")
    public List<Board> findAllByUsername(@PathVariable("username") String username) {
        return boardService.findAllByUsername(username);
    }
    @GetMapping("/boards/page/{username}")
    public Page<Board> findPageByUsername(@PathVariable("username") String username, Pageable pageable) {
        return boardService.findPageByUsername(username, pageable);
    }

}

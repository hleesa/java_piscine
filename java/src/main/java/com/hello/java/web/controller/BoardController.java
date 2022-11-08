package com.hello.java.web.controller;


import com.hello.java.domain.board.Board;
import com.hello.java.service.BoardService;
import com.hello.java.web.dto.BoardSaveRequestDto;
import com.hello.java.web.dto.BoardUpdateRequestDto;
import com.hello.java.web.dto.BoardListResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/board")
    public Board saveBoard(@RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(requestDto.toEntity());
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

    @DeleteMapping("/board/{boardId}")
    public void deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);
    }

    @PutMapping("/board/like/{boardId}")
    public void updateLikes(@PathVariable("boardId") Long boardId, @RequestParam Boolean isLike) {
        boardService.updateLikes(boardId, isLike);
    }
}

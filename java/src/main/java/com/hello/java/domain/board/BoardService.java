package com.hello.java.domain.board;

import com.hello.java.domain.board.dto.BoardListResponseDto;
import com.hello.java.domain.board.dto.BoardSaveRequestDto;
import com.hello.java.domain.board.dto.BoardUpdateDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity());
    }

    @Transactional
    public Long updateBoard(Long id, BoardUpdateDto boardUpdateDto) {
        Board board = findOne(id).orElseThrow();
        board.update(boardUpdateDto.getTitle(), boardUpdateDto.getContent());
        return id;
    }

    public Optional<Board> findOne(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public BoardListResponseDto findBoards() {

        List<Board> boards = boardRepository.findAll();
        BoardListResponseDto boardBoardListResponseDto = BoardListResponseDto.builder()
                .boards(boards)
                .total(boards.size())
                .build();
        return boardBoardListResponseDto;
    }

    @Transactional
    public void delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        boardRepository.delete(board);
    }

    public void updateLike(Long id, Long likeOffset) {
        Board findBoard = findOne(id).orElseThrow();
        findBoard.updateLike(likeOffset);
    }
}
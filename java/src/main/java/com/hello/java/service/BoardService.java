package com.hello.java.service;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.board.BoardRepository;
import com.hello.java.domain.user.User;
import com.hello.java.domain.user.UserRepository;
import com.hello.java.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

//    public Board save(Long userId, Board board) {
//        User user = userRepository.findById(userId).orElseThrow();
//        board.setUser(user);
//        boardRepository.save(board);
//        return board;
//    }

    public Board save(BoardSaveRequestDto requestDto) {
        Board board = requestDto.toEntity();
        board.setUser(userRepository.findById(requestDto.getUserId()).orElseThrow());
        boardRepository.save(board);
        return board;
    }


//    public Long update(Long id, Board newBoard) {
//        Board oldBoard = findOne(id).toEntity();
//        oldBoard.update(newBoard);
//        return id;
//    }

    public Long update(BoardUpdateRequestDto requestDto) {

        Board oldBoard = boardRepository.findById(requestDto.getBoardId()).orElseThrow();
        Board newBoard = requestDto.toEntity();
        oldBoard.update(newBoard);
        return oldBoard.getId();
    }


    @Transactional(readOnly = true)
    public BoardResponseDto findOne(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        return new BoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    public BoardListResponseDto finaAll() {

        List<Board> boardList = boardRepository.findAll();
        BoardListResponseDto boardListResponseDto = BoardListResponseDto.builder()
                .boardDtoList(boardList)
                .size(boardList.size())
                .build();
        return boardListResponseDto;
    }

    public void delete(BoardDeleteRequestDto requestDto) {
        Long boardId = requestDto.getBoardId();
        Long userId = requestDto.getUserId();
        Board board = boardRepository.findById(boardId).orElseThrow();
        if (board.getUser().getId() == userId)
            boardRepository.delete(board);
    }

    public void updateViews(Long id) {
        Board findBoard = findOne(id).toEntity();
        findBoard.updateViews();
    }

    @Transactional(readOnly = true)
    public BoardListResponseDto findBoardsByUsername(String username) {
        List<Board> boardList = boardRepository.findBoardsByUserUsername(username);
        // repository에서 BoardListDto반환 가능?
        return BoardListResponseDto.builder()
                .boardDtoList(boardList)
                .size(boardList.size())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<Board> findPageByUsername(String username, Pageable pageable) {
        return boardRepository.findAllByUserUsername(username, pageable);
    }
}

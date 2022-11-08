package com.hello.java.service;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.board.BoardRepository;
import com.hello.java.domain.user.User;
import com.hello.java.domain.user.UserRepository;
import com.hello.java.web.dto.BoardListResponseDto;
import lombok.RequiredArgsConstructor;
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
    private final UserService userService;

//    @Transactional
//    public Board save(Board board) {
//        return boardRepository.save(board);
//    }

    public Board save(Long userId, Board board) {
        User user = userRepository.findById(userId).orElseThrow();
        board.setUser(user);
        boardRepository.save(board);
        return board;
    }

    public Long update(Long id, Board newBoard) {
        Board oldBoard = findOne(id).orElseThrow();
        oldBoard.update(newBoard);
        return id;
    }

    @Transactional(readOnly = true)
    public Optional<Board> findOne(Long boardId) {
        return boardRepository.findById(boardId);
    }

    @Transactional(readOnly = true)
    public BoardListResponseDto findBoards() {

        List<Board> boards = boardRepository.findAll();
        BoardListResponseDto boardBoardListResponseDto = BoardListResponseDto.builder()
                .boards(boards)
                .total(boards.size())
                .build();
        return boardBoardListResponseDto;
    }

    public void delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        boardRepository.delete(board);
    }

    public void updateLikes(Long id, Boolean isLike) {
        Board findBoard = findOne(id).orElseThrow();
        findBoard.updateLike(isLike);
    }

    public void updateViews(Long id) {
        Board findBoard = findOne(id).orElseThrow();
        findBoard.updateViews();
    }

    @Transactional(readOnly = true)
    public List<Board> findBoardsByAuthor(String author) {
        List<Board> boards = boardRepository.findBoardsByAuthor(author);
        return boards;
    }
}

package com.hello.java.service;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.board.BoardRepository;
import com.hello.java.domain.user.User;
import com.hello.java.domain.userboard.Likes;
import com.hello.java.domain.userboard.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;

    public void addLikes(User user, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if (likesRepository.findByUserAndBoard(user, board).isEmpty()) {
            likesRepository.save(new Likes(user, board));
        } else {
            Likes likes = likesRepository.findByUserAndBoard(user, board).orElseThrow();
            likesRepository.delete(likes);
        }
    }
}

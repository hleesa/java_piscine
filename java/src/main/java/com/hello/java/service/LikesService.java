package com.hello.java.service;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.board.BoardRepository;
import com.hello.java.domain.user.User;
import com.hello.java.domain.userboard.Likes;
import com.hello.java.domain.userboard.LikesRepository;
import com.hello.java.web.dto.LikesListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;

    public void addLikes(User user, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        Likes likes = likesRepository.findByUserAndBoard(user, board).orElse(null);
        if (likes == null) {
            board.addLikes(new Likes(user, board));
        } else {
            likesRepository.delete(likes);
            board.getLikes().remove(likes);
        }
    }

    public LikesListResponseDto findLikesByUsername(String username) {
        List<Likes> likesList = likesRepository.findLikesByUserUsername(username);
        return LikesListResponseDto.from(likesList);
    }

}


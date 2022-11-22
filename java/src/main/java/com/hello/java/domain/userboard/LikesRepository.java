package com.hello.java.domain.userboard;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserAndBoard(User user, Board board);
    List<Likes> findLikesByUserUsername(String username);
}

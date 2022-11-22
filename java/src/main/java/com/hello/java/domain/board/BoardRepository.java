package com.hello.java.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findBoardsByUserUsername(String username);
    Page<Board> findAllByUserUsername(String username, Pageable pageable);
}

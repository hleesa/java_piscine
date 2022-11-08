package com.hello.java.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findBoardsByAuthor(String author);
}

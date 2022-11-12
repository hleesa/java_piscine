package com.hello.java.domain.board;

import com.hello.java.web.dto.BoardSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 게시글저장_불러오기() {

        //given
        String title1 = "42gg";
        String content1 = "salee";

        boardRepository.save(BoardSaveRequestDto.builder()
                .title(title1)
                .content(content1)
                .build().toEntity());

        String title2 = "42seoul";
        String content2 = "salee2";

        boardRepository.save(BoardSaveRequestDto.builder()
                .title(title2)
                .content(content2)
                .build().toEntity());

        //when

        List<Board> boardList = boardRepository.findAll();

        //then
        Board board1 = boardList.get(0);
        assertThat(board1.getTitle()).isEqualTo(title1);
        assertThat(board1.getContent()).isEqualTo(content1);

        Board board2 = boardList.get(1);
        assertThat(board2.getViews()).isEqualTo(0L);
//        assertThat(board2.getLikes()).isEqualTo(0L);
    }
}
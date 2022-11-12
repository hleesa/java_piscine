package com.hello.java.web.dto;

import com.hello.java.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {

    private Long boardId;
    private String title;
    private String content;
    private String tag;
    private Long userId;

    @Builder
    public BoardUpdateRequestDto(Long boardId, String title, String content, String tag) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.tag = tag;
    }
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .build();
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

}

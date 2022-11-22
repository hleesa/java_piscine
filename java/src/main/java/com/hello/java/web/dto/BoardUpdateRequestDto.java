package com.hello.java.web.dto;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.user.User;
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
    private Long views;
    private User user;

    @Builder
    public BoardUpdateRequestDto(Long boardId, String title, String content, String tag, Long views, User user) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.views = views;
        this.user = user;
    }
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .views(views)
                .user(user)
                .build();
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

}

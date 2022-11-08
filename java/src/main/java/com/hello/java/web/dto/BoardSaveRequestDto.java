package com.hello.java.web.dto;


import com.hello.java.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String title;
    private String content;
    private String tag;
    private Long likes = 0L;
    private Long views = 0L;

    @Builder
    public BoardSaveRequestDto(String title, String content, String tag) {
        this.title = title;
        this.content = content;
        this.likes = 0L;
        this.views = 0L;
        this.tag = tag;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .likes(likes)
                .views(views)
                .tag(tag)
                .build();
    }
}

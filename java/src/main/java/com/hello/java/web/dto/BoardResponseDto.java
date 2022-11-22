package com.hello.java.web.dto;


import com.hello.java.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String tag;
    private Long views;
    private String username;
    private int likes;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.tag = board.getTag();
        this.views = board.getViews();
        this.username = board.getUser().getUsername();
        this.likes = board.getLikes().size();
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .views(views)
                .tag(tag)
                .build();
    }

}

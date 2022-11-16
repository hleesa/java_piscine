package com.hello.java.web.dto;

import com.hello.java.domain.board.Board;
import com.hello.java.domain.user.User;
import com.hello.java.domain.userboard.Likes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesResponseDto {

    private String username;
    private String title;
    private String content;
    private String tag;
    private Long views;
    @Builder
    public LikesResponseDto(User user, Board board) {
        this.username = user.getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
    }

    public LikesResponseDto(Likes likes) {
        this.username = likes.getUser().getUsername();
        this.title = likes.getBoard().getTitle();

    }

}

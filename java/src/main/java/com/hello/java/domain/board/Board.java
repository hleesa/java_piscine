package com.hello.java.domain.board;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hello.java.domain.BaseTimeEntity;
import com.hello.java.domain.user.User;
import com.hello.java.web.dto.BoardUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String title;
    private String content;
    private String tag;
    private Long likes;
    private Long views;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Board(String title, String content, Long likes, Long views, String tag, User user) {
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.views = views;
        this.tag = tag;
        this.user = user;
    }

    public void update(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.tag = board.getTag();
    }

    public void updateLike(Boolean isLike) {
        if (isLike) {
            ++this.likes;
        } else {
            --this.likes;
        }
    }

    public void updateViews() {
        ++this.views;
    }
}

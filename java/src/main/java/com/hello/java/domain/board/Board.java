package com.hello.java.domain.board;

import com.hello.java.domain.BaseTimeEntity;
import com.hello.java.domain.user.User;
import com.hello.java.domain.userboard.Likes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Long views;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    Set<Likes> likes = new HashSet<>();

    @Builder
    public Board(Long id, String title, String content, Long views, String tag, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.tag = tag;
        this.user = user;
    }

    public void update(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.tag = board.getTag();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateViews() {
        ++this.views;
    }

    public void addLikes(Likes likes) {
        this.likes.add(likes);
        likes.setBoard(this);
    }
}

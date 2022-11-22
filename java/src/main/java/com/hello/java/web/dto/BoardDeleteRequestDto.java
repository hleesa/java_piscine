package com.hello.java.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardDeleteRequestDto {
    private Long userId;
    private Long boardId;

    @Builder
    public BoardDeleteRequestDto(Long userId, Long boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }
}

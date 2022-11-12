package com.hello.java.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesRequestDto {
    private Long boardId;
    private Long userId;

    @Builder
    public LikesRequestDto(Long boardId, Long userId) {
        this.boardId = boardId;
        this.userId = userId;
    }
}

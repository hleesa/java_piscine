package com.hello.java.web.dto;

import com.hello.java.domain.userboard.Likes;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LikesListResponseDto {

    private List<LikesResponseDto> likesDtoList;
    private int size;

    public LikesListResponseDto() {
        this.likesDtoList = new ArrayList<>();
    }

    public LikesListResponseDto(List<Likes> likesList, int size) {
        this.likesDtoList = likesList.stream().map(LikesResponseDto::new).collect(Collectors.toList());
        this.size = size;
    }

    public static LikesListResponseDto from(List<Likes> likesList) {
        return new LikesListResponseDto(likesList, likesList.size());
    }
}

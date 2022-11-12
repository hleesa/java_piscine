package com.hello.java.web.dto;

import com.hello.java.domain.board.Board;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardListResponseDto{

    private List<BoardResponseDto> boardDtoList;
    private int size;

    public BoardListResponseDto() {
        this.boardDtoList = new ArrayList<BoardResponseDto>();
    }

    @Builder
    public BoardListResponseDto(List<Board> boardDtoList, int size) {
        this.boardDtoList = boardDtoList.stream().map(BoardResponseDto::new).collect(Collectors.toList());
        this.size = size;
    }
    static public BoardListResponseDto from(List<Board> boardDtoList) {
        return BoardListResponseDto.builder()
                .boardDtoList(boardDtoList)
                .size(boardDtoList.size())
                .build();
    }


}

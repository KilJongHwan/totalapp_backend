package com.kh.totalapp.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDTO {
    private Long boardId;
    private String email;
    private Long categoryId;
    private String title;
    private String content;
    private String img;
    private LocalDateTime regDate;
}

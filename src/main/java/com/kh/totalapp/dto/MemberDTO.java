package com.kh.totalapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDTO {
    private String email;
    private String pwd;
    private String name;
    private String image;
    private LocalDateTime regDate;
}

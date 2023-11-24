package com.kh.totalapp.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
    @Column(unique = true)
    private String email;
    private String image;
    private LocalDateTime regDate;
    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now();
    }
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Board> boards;
}

package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목 필수 입력요망")
    @NotNull
    @Size(min = 2, max = 30,  message = "must be between 2 and 30 characters")
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id") // Board에 있는 2번째 컬럼이 user_id임.
    @JsonIgnore
    private User user;
}

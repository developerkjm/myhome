package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // roleRepository 이요해서 조회하게 된다면 User의 컬럼이름이 ""안에 들어가야 한다.
    // 동일한 이름을 넣으면 설정에 맞게 알아서 가져온다.
    // 이것이 양방향 매핑이다.
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;
}

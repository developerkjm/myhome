package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;


    @Version
    private Long version=0L;

    // many to many 매핑을 할것이다(join 하려고) List를 이용하는 것이 편함.
    // List 이용해서 권한과 매핑을 해보자.
    // 멤버변수를 생성하게 되면 userRepository를 조회하는데 이 유저에 해당하는 권한이 조회돼서  roles에 담기게 된다.
    // 구글에 many to many 검색해서 jpa에 적용되는 것을 Baeldung에서 알아서 잘 가지고 오자.
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    // 이렇게 내버려두면 roles를 기본적으로 기본값이 null이 되기 때문에 초기화 해주자.
    // private List<Role> roles;
    private List<Role> roles = new ArrayList<>();

    // 사용자 입장에서 게시글을 가져올 때 oneToMany : 가장많이 사용되는 join. ManyToOne 작성하는 쪽(BOard 클래스)에서 소유하느 쪽의 매핑정보를 적어놓음.
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false) // 모든 옵션 사용.
//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = false) // 모든 옵션 사용.

    // fetch:  User 조회시에 필요한 정보를 바로(EAGERT) 가지고 올건지 나중에(LAZY: 기본값) 가지고 올건지 조회함.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

}

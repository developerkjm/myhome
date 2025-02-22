package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);

    //  containing 키워드를 이용하여 jpa 레포지토리를 이용하자. (제목과 내용을 이용한 검색 페이징 부분)
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}

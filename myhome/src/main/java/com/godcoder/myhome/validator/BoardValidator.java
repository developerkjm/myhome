package com.godcoder.myhome.validator;

// import 많으니까 잘 선택해야함.
import com.godcoder.myhome.model.Board;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;


@Component
public class BoardValidator implements Validator {

    // Validator 상속받으면 아래의 2개의 메서드를 구현 해야 한다.

    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board board = (Board) obj;
        if(StringUtils.isEmpty(board.getContent())) {
            errors.rejectValue("content", "key", "내용을 입력하세요"); // content에 key가 없ㅈ으면 내용을입력하라고 함.
        }
        /*
        if (StringUtils.isEmpty(board.getTitle())) {
            errors.rejectValue("title", "key", "title을 입력하세요"); // content에 key가 없ㅈ으면 내용을입력하라고 함.
        }
        */
    }
}

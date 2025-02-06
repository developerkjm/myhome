package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.validator.BoardValidator;
import jakarta.validation.Valid;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Validated
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;
    @GetMapping("/list")
    public  String list(Model model) {
        List<Board> boards =  boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null) {
            model.addAttribute("board", new Board());
        } else {
            // 뒤에 orElse를 안적으면 빨갛게 에러가 난다. 왜냐면 값이 없을 수도 있기때문이다
            // 그래서 뒤에 orElse(null)을 줘서 null일 경우에 옵션값을 줬다.
            Board board = boardRepository.findById(id).orElse(null);
//            model.addAttribute("board", new Board());
            model.addAttribute("board", board);

        }
        return  "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid @ModelAttribute Board board, BindingResult bindingResult, Model model) {

        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()) {
            return "board/form";
        }

        boardRepository.save(board);
        return "redirect:/board/list";
    }

/*
        // 수동으로 유효성 검사
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator()) // EL 없이 메시지 처리
                .buildValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Board>> violations = validator.validate(board);

        // ConstraintViolation을 FieldError로 변환하여 BindingResult에 추가
        for (ConstraintViolation<Board> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();  // 오류가 발생한 필드 이름
            String message = violation.getMessage();  // 오류 메시지
            bindingResult.addError(new FieldError("board", fieldName, message));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board/form";
        }
*/
}

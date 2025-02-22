package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.service.BoardService;
import com.godcoder.myhome.validator.BoardValidator;
import jakarta.validation.Valid;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private BoardService boardService;


    @GetMapping("/list")
    public  String list(Model model,
                        @PageableDefault(size=2) Pageable pageable,
                        @RequestParam(required = false, defaultValue = "") String searchText) {
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        // 시작페이지가 0보다 적으면 안되므로 Math 클래스를 이용하여 최소값을 정해줌.
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);

        }
        return  "board/form";
    }

    @PostMapping("/form")
    public String postForm(
            @Valid @ModelAttribute Board board,
            Authentication authentication,
            BindingResult bindingResult,
            Model model) {

        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()) {
            return "board/form";
        }
        // => 사용자 정보 가져오기
        // 만약에 이 페이지에서 인증한 user 정보를 전달하게 되면 post 요청하느 부분을 다른 사용자가 분석해서 다른사람 id도 전달할 수도 있다?
        // 그렇게 되면 본인이 작성한 것이 아닌데도 들어가는 불상사가 생기게 될 것이다.
        // 그러면 서버쪽의 인증정보를 활용해서 담아줘야한다.
        // 인증정보는 스프링 시큐리티에서 활용되지만 그중에 하나가 Authentication이라는 클래스를 받을 수가 있다.
        // 사용자 정보를 받아서 이것을 바탕으로 이 것을 참조해서 값을 처리할 수 있다.
        String username = authentication.getName(); // 사용자 이름 가져옴.
        boardService.save(username, board);
        //boardRepository.save(board);

        // SecurityContextHoler 방법도 있다.
        // 위의 방법은 Controller에서 DI? 방법으로 파라메터로 가져오는 방법이고 이 방법은 controller 외에
        // 서비스 같은 곳이나 다른 클래스에서 인증작업할 때 이런식으로 가져올 수 있다.
        // 이것말고도 다른 방법도 있다.
        // Authentication a = SecurityContextHolder.getContext().getAuthentication(); // 이렇게도 authentication을 얻을 수 있다.


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

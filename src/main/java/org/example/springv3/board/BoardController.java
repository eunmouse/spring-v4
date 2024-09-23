package org.example.springv3.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springv3.core.error.ex.Exception404;
import org.example.springv3.core.error.ex.ExceptionApi404;
import org.example.springv3.core.util.Resp;
import org.example.springv3.user.User;
import org.example.springv3.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardService boardService;

    // localhost:8080?title=제목
    @GetMapping("/")
    public String list(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            HttpServletRequest request) {

        BoardResponse.PageDTO pageDTO = boardService.게시글목록보기(title, page);
        request.setAttribute("model", pageDTO);
        return "board/list";
    }


    // PostMappign -> DeleteMapping 으로 바꾸면 "/api/board/{id}/delete" 에서 delete 동사 지우면 됨
    @DeleteMapping("/api/board/{id}")
    public String removeBoard(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.게시글삭제하기(id, sessionUser);
        return "redirect:/";
    }


//    @GetMapping("/api/board/save-form")
//    public String saveForm() {
//        return "board/save-form";
//    } // 화면을 돌려줄 필요가 없다. 데이터만 돌려주면 되니까 지움.


//    @PostMapping("/api/board/save")
//    public String save(@Valid BoardRequest.SaveDTO saveDTO, Errors errors) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
//
//        boardService.게시글쓰기(saveDTO, sessionUser);
//
//        return "redirect:/";
//    }
    // 종국에는 아래의 모습으로 바꿔야함
    @PostMapping("/api/board") // 이제는 DELETE, PUT 쓸테니 save 지워
    public ResponseEntity<?> save(@Valid @RequestBody BoardRequest.SaveDTO saveDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        BoardResponse.DTO model = boardService.게시글쓰기(saveDTO, sessionUser);

        return ResponseEntity.ok(Resp.ok(model));
    }

    @GetMapping("/api/board/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DTO model = boardService.게시글수정화면(id, sessionUser);
        request.setAttribute("model", model);
        return "board/update-form";
    }

    @PostMapping("/api/board/{id}/update")
    public String update(@PathVariable("id") int id, @Valid BoardRequest.UpdateDTO updateDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.게시글수정(id, updateDTO, sessionUser);
        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        BoardResponse.DetailDTO model = boardService.게시글상세보기(sessionUser, id);
        request.setAttribute("model", model);

        return "board/detail";
    }
}

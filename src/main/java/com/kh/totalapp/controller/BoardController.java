package com.kh.totalapp.controller;


import com.kh.totalapp.dto.BoardDTO;
import com.kh.totalapp.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kh.totalapp.utils.Common.CORS_ORIGIN;

@Slf4j
@CrossOrigin(origins = CORS_ORIGIN)
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 등록
    @PostMapping("/new")
    public ResponseEntity<Boolean> boardRegister(@RequestBody BoardDTO boardDto) {
        boolean isTrue = boardService.saveBoard(boardDto);
        return ResponseEntity.ok(isTrue);
    }
    // 게시글 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<Boolean> boardModify(@PathVariable Long id, @RequestBody BoardDTO boardDto) {
        boolean isTrue = boardService.modifyBoard(id, boardDto);
        return ResponseEntity.ok(isTrue);
    }
    // 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> boardDelete(@PathVariable Long id) {
        boolean isTrue = boardService.deleteBord(id);
        return ResponseEntity.ok(isTrue);
    }
    // 게시글 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<BoardDTO>> boardList() {
        List<BoardDTO> list = boardService.getBoardList();
        return ResponseEntity.ok(list);
    }
    // 게시글 목록 페이징
    @GetMapping("/list/page")
    public ResponseEntity<List<BoardDTO>> boardList(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        List<BoardDTO> list = boardService.getBoardList(page, size);
        return ResponseEntity.ok(list);
    }
    // 게시글 상세 조회
    @GetMapping("/detail/{id}")
    public ResponseEntity<BoardDTO> boardDetail(@PathVariable Long id) {
        BoardDTO boardDto = boardService.getBoardDetail(id);
        return ResponseEntity.ok(boardDto);
    }
    // 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<List<BoardDTO>> boardSearch(@RequestParam String keyword) {
        List<BoardDTO> list = boardService.searchBoard(keyword);
        return ResponseEntity.ok(list);
    }

    // 페이지 수 조회
    @GetMapping("/count")
    public ResponseEntity<Integer> listBoards(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Integer pageCnt = boardService.getBoards(pageRequest);
        return ResponseEntity.ok(pageCnt);
    }
}

package com.kh.totalapp.controller;


import com.kh.totalapp.dto.CategoryDTO;
import com.kh.totalapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kh.totalapp.utils.Common.CORS_ORIGIN;

@Slf4j
//@CrossOrigin(origins = CORS_ORIGIN)
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    // 카테고리 등록
    @PostMapping("/new")
    public ResponseEntity<Boolean> categoryRegister(@RequestBody CategoryDTO categoryDTO) {
        boolean isTrue = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.ok(true);
    }
    // 카테고리 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<Boolean> categoryModify(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        boolean isTrue = categoryService.modifyCategory(id, categoryDTO);
        return ResponseEntity.ok(true);
    }
    // 카테고리 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> categoryDelete(@PathVariable Long id) {
        boolean isTrue = categoryService.deleteCategory(id);
        return ResponseEntity.ok(true);
    }
    // 카테고리 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<CategoryDTO>> categoryList() {
        List<CategoryDTO> list = categoryService.getCategoryList();
        return ResponseEntity.ok(list);
    }
}

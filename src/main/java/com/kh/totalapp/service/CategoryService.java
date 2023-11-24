package com.kh.totalapp.service;


import com.kh.totalapp.dto.CategoryDTO;
import com.kh.totalapp.entity.Category;
import com.kh.totalapp.entity.Member;
import com.kh.totalapp.repository.CategoryRepository;
import com.kh.totalapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    // 카테고리 등록
    public boolean saveCategory(CategoryDTO categoryDTO){
        try {
            Category category = new Category();
            Member member = memberRepository.findByEmail(categoryDTO.getEmail()).orElseThrow(
                    ()-> new RuntimeException("해당 회원이 존재 하지 않습니다.")
            );
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setMember(member);
            categoryRepository.save(category);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // 카테고리 수정
    public boolean modifyCategory(Long id, CategoryDTO categoryDTO){
        try {
            Category category = categoryRepository.findById(id).orElseThrow(
                    ()-> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
            );
            Member member = memberRepository.findByEmail(categoryDTO.getEmail()).orElseThrow(
                    ()-> new RuntimeException("해당 회원이 존재하지 않습니다")
            );
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setCategoryId(categoryDTO.getCategoryId());
            category.setMember(member);
            categoryRepository.save(category);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // 카테고리 삭제
    public boolean deleteCategory(Long id){
        try {
            Category category = categoryRepository.findById(id).orElseThrow(
                    ()-> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
            );
            categoryRepository.delete(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 카테고리 목록 조회
    public List<CategoryDTO> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(Category category : categories) {
            categoryDTOS.add(convertEntityToDto(category));
        }
        return categoryDTOS;
    }

    // 엔티티를 DTO로 변환하는 메서드
    private CategoryDTO convertEntityToDto(Category category) {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setEmail(category.getMember().getEmail());
        return categoryDto;
    }
}

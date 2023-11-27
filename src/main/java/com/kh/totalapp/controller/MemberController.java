package com.kh.totalapp.controller;

import com.kh.totalapp.dto.MemberReqDTO;
import com.kh.totalapp.dto.MemberResDTO;
import com.kh.totalapp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kh.totalapp.utils.Common.CORS_ORIGIN;

@Slf4j
//@CrossOrigin(origins = CORS_ORIGIN)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    // 회원 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberResDTO>> memberList() {
        List<MemberResDTO> list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }
    // 총 페이지 수
    @GetMapping("/list/count")
    public ResponseEntity<Integer> memberCount(@RequestParam(defaultValue = "20") int page,
                                               @RequestParam(defaultValue = "0") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        int pageCnt = memberService.getMemberPage(pageRequest);
        return ResponseEntity.ok(pageCnt);
    }

    // 회원 조회 페이지네이션
    @GetMapping("/list/page")
    public ResponseEntity<List<MemberResDTO>> memberList(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "20") int size) {
        List<MemberResDTO> list = memberService.getMemberList(page, size);
        return ResponseEntity.ok(list);
    }
    // 회원 상세 조회
    @GetMapping("/detail/{email}")
    public ResponseEntity<MemberResDTO> memberDetail(@PathVariable String email) {
        MemberResDTO memberDto = memberService.getMemberDetail(email);
        return ResponseEntity.ok(memberDto);
    }
    // 회원 수정
    @PutMapping("/modify")
    public ResponseEntity<Boolean> memberModify(@RequestBody MemberReqDTO memberDto) {
        log.info("memberDto: {}", memberDto.getEmail());
        boolean isTrue = memberService.modifyMember(memberDto);
        return ResponseEntity.ok(isTrue);
    }
    // 회원 삭제
    @DeleteMapping("/del/{email}")
    public ResponseEntity<Boolean> memberDelete(@PathVariable String email) {
        boolean isTrue = memberService.deleteMember(email);
        return ResponseEntity.ok(isTrue);
    }

}
package com.kh.totalapp.controller;


import com.kh.totalapp.dto.MemberReqDTO;
import com.kh.totalapp.dto.MemberResDTO;
import com.kh.totalapp.dto.TokenDTO;
import com.kh.totalapp.service.AuthService;
import com.kh.totalapp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResDTO> signup(@RequestBody MemberReqDTO requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberReqDTO requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
    // 회원 존재 여부 확인
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> memberExists(@PathVariable String email) {
        log.info("email: {}", email);
        boolean isTrue = memberService.isMember(email);
        return ResponseEntity.ok(!isTrue);
    }
}

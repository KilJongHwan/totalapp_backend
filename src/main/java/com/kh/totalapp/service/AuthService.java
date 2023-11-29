package com.kh.totalapp.service;

import com.kh.totalapp.dto.MemberReqDTO;
import com.kh.totalapp.dto.MemberResDTO;
import com.kh.totalapp.dto.TokenDTO;
import com.kh.totalapp.entity.Member;
import com.kh.totalapp.jwt.TokenProvider;
import com.kh.totalapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder; // 인증을 담당하는 클래스
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    public MemberResDTO signup(MemberReqDTO requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Member member = requestDto.toEntity(passwordEncoder);
        return MemberResDTO.of(memberRepository.save(member));
    }
    public TokenDTO login(MemberReqDTO requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        log.info("authenticationToken: {}", authenticationToken);

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        log.info("authentication: {}", authentication);

        return tokenProvider.generateTokenDTO(authentication);
    }
    public String createAccessToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 토큰");
        }
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        return tokenProvider.generateAccessToken(authentication);
    }
}

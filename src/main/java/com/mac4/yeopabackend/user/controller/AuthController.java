package com.mac4.yeopabackend.user.controller;

import com.mac4.yeopabackend.common.response.ApiResponse;
import com.mac4.yeopabackend.user.dto.request.LoginRequestDto;
import com.mac4.yeopabackend.user.dto.request.SignUpRequestDto;
import com.mac4.yeopabackend.user.dto.response.TokenResponseDto;
import com.mac4.yeopabackend.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<TokenResponseDto> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        TokenResponseDto token = authService.signUp(signUpRequestDto);
        return ApiResponse.success(token);
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        TokenResponseDto token = authService.login(loginRequestDto);
        return ApiResponse.success(token);
    }
}

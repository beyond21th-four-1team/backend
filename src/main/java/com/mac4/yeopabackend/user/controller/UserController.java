package com.mac4.yeopabackend.user.controller;

import com.mac4.yeopabackend.common.exception.BusinessException;
import com.mac4.yeopabackend.common.exception.ErrorCode;
import com.mac4.yeopabackend.common.response.ApiResponse;
import com.mac4.yeopabackend.common.security.CustomUser;
import com.mac4.yeopabackend.post.dto.MypageResponse;
import com.mac4.yeopabackend.post.dto.PostResponse;
import com.mac4.yeopabackend.post.service.PostService;
import com.mac4.yeopabackend.user.dto.request.ModifyRequestDto;
import com.mac4.yeopabackend.user.dto.response.MyPageResponseDto;
import com.mac4.yeopabackend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestHeader("Authorization") String authorization){
        userService.logout(authorization);
        return ApiResponse.success();
    }

    @GetMapping("/mypage")
    public ApiResponse<MyPageResponseDto> mypage(@AuthenticationPrincipal CustomUser user){

        MyPageResponseDto myPage = userService.mypage(user.getId());

        return ApiResponse.success(myPage);
    }

    @PatchMapping("/description")
    public ApiResponse<Void> modifyDescription(@AuthenticationPrincipal CustomUser user,
                                               @RequestBody @Valid ModifyRequestDto request) {
        if (user == null) throw new BusinessException(ErrorCode.AUTH_UNAUTHORIZED);

        userService.modifyDescription(user.getId(),request.description());

        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<List<MypageResponse>> getPost(@AuthenticationPrincipal CustomUser user){

        if(user == null) throw new BusinessException(ErrorCode.AUTH_UNAUTHORIZED);
        if(postService.getMyPost(user.getId()).isEmpty()) throw new BusinessException(ErrorCode.POST_USER_NOTFOUND);
        return ApiResponse.success(postService.getMyPost(user.getId()));
    }
}

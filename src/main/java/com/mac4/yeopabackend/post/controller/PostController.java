package com.mac4.yeopabackend.post.controller;

import com.mac4.yeopabackend.common.exception.BusinessException;
import com.mac4.yeopabackend.common.exception.ErrorCode;
import com.mac4.yeopabackend.common.response.ApiResponse;
import com.mac4.yeopabackend.common.security.CustomUser;
import com.mac4.yeopabackend.post.domain.Post;
import com.mac4.yeopabackend.post.dto.MypageResponse;
import com.mac4.yeopabackend.post.dto.PostRequest;
import com.mac4.yeopabackend.post.dto.PostResponse;
import com.mac4.yeopabackend.post.service.FileService;
import com.mac4.yeopabackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import com.mac4.yeopabackend.post.dto.FileInfo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> creatPost (
            @AuthenticationPrincipal CustomUser user,
            @ModelAttribute PostRequest request) throws IOException {
        if(user == null) throw new BusinessException(ErrorCode.AUTH_UNAUTHORIZED);
        MultipartFile file = request.getFile();
        FileInfo fileName = fileService.uploadFile(file);
        postService.create(user.getId(),user.getUsername(), request, fileName.objectKey(),fileName.originalName());

        return ApiResponse.success();
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
        return ApiResponse.success(postService.getPost(id));
    }

    @GetMapping
    public ApiResponse<List<MypageResponse>>getAllPostOrderByDECS(){
        return ApiResponse.success(postService.getAllPost());
    }

}

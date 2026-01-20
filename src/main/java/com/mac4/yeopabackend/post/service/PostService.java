package com.mac4.yeopabackend.post.service;

import com.mac4.yeopabackend.post.domain.Post;
import com.mac4.yeopabackend.post.dto.MypageResponse;
import com.mac4.yeopabackend.post.repository.PostRepository;
import com.mac4.yeopabackend.post.dto.PostRequest;
import com.mac4.yeopabackend.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Value("${s3.endpoint}")
    private String endpoint;

    public void create(Long userId, String nickname, PostRequest postRequest, String objectKey, String originalName){
        String image = endpoint + "/yeopa/" + objectKey + originalName;
        postRepository.save(Post.from(userId,nickname,postRequest,image,objectKey,originalName));
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post Not Found"));

        return new PostResponse(post.getUserId(),post.getNickname(),post.getTitle(),post.getLocation()
        ,post.getText(),post.getImage(),post.getSingleText(),post.getCreatedAt());
    }

    public List<MypageResponse> getAllPost(){
        return  postRepository.findAllByOrderByCreatedAtDesc().stream().map(post -> new MypageResponse(
                post.id(),
                post.image(),
                post.title(),
                post.singleText(),
                post.createdAt(),
                post.location()
                ))
                .toList();

    }

    public List<MypageResponse> getMyPost(Long id){
        return  postRepository.findAllByUserId(id).stream().map(post -> new MypageResponse(
                        post.id(),
                        post.image(),
                        post.title(),
                        post.singleText(),
                        post.createdAt(),
                        post.location()
                ))
                .toList();
    }

}

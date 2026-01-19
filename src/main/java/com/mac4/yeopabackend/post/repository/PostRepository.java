package com.mac4.yeopabackend.post.repository;

import com.mac4.yeopabackend.post.domain.Post;
import com.mac4.yeopabackend.post.dto.MypageResponse;
import com.mac4.yeopabackend.post.dto.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<MypageResponse> findAllByUserId(Long userId);

}

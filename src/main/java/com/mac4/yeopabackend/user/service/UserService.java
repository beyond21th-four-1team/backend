package com.mac4.yeopabackend.user.service;

import com.mac4.yeopabackend.common.exception.BusinessException;
import com.mac4.yeopabackend.common.exception.ErrorCode;
import com.mac4.yeopabackend.common.jwt.TokenBlacklistStore;
import com.mac4.yeopabackend.user.domain.User;
import com.mac4.yeopabackend.user.dto.response.MyPageResponseDto;
import com.mac4.yeopabackend.user.repository.UserRepository;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final TokenBlacklistStore tokenBlacklistStore;
    private final UserRepository userRepository;

    @Transactional
    public void logout(String authorization) {
        String token = extractToken(authorization);
        tokenBlacklistStore.blacklist(token);
    }

    // accessToken 헤더 제거
    private String extractToken(String authorization){
        if(authorization == null || !authorization.startsWith("Bearer ")){
            throw new BusinessException(ErrorCode.AUTH_UNAUTHORIZED);
        }
        return authorization.substring(7);
    }

    public MyPageResponseDto mypage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new  MyPageResponseDto(
                user.getEmail(),
                user.getUsername(),
                user.getDescription()
        );
    }

    @Transactional
    public void modifyDescription(Long id, String description) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.modifyDescription(description);

        userRepository.save(user);
    }
}

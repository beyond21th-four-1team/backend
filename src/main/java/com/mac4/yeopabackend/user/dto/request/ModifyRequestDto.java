package com.mac4.yeopabackend.user.dto.request;

import jakarta.validation.constraints.Size;

public record ModifyRequestDto(

        @Size(max = 255, message = "한 줄 소개는 255자 이하여야 합니다.")
        String description
) {
}

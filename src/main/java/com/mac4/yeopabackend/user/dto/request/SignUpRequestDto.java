package com.mac4.yeopabackend.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequestDto(
        @NotBlank(message = "email은 필수입니다.")
        @Size(max = 100, message = "email은 100자 이하여야 합니다.")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "email 형식이 올바르지 않습니다."
        )
        String email,

        @NotBlank(message = "name은 필수입니다.")
        @Size(min=2,max = 50, message = "name은 2~50자여야 합니다.")
        @Pattern(
                regexp = "^[가-힣]+$",
                message = "name은 한글만 입력할 수 있으며 공백, 숫자, 특수문자는 허용되지 않습니다."
        )
        String username,

        @NotBlank(message = "password는 필수입니다.")
        @Size(min = 8, max = 20, message = "password는 8~20자여야 합니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)\\S{8,20}$",
                message = "password는 영문과 숫자를 모두 포함해야 하며, 공백 없이 특수문자 사용이 가능합니다."
        )
        String password,

        @Size(max = 255, message = "한 줄 소개는 255자 이하여야 합니다.")
        String description

) {
}

package com.mac4.yeopabackend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 1. 공통 에러
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "C001", "입력값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 에러입니다."),

    // TODO(클로이) : 이 곳에서 각자 도메인에서 발생할 에러를 HttpStatus Enum을 참고하셔서 작성해주시면 됩니다.

    // auth
    AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "인증이 필요합니다."),
    AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, "A002", "접근 권한이 없습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "유효하지 않은 토큰입니다."),
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A004", "토큰이 만료되었습니다."),
    AUTH_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "A005", "아이디 또는 비밀번호가 올바르지 않습니다."),

    // user
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "U001", "이미 사용 중인 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U002", "사용자가 존재하지 않습니다."),

    POST_USER_NOTFOUND(HttpStatus.NO_CONTENT, "P001", "응답할 내용을 찾지못했습니다."),
    POST_TEXT_NONINCODING(HttpStatus.BAD_REQUEST, "P002", "잘못된 파일명을 가지고있습니다.")
    ;



    private final HttpStatus status;
    private final String code;
    private final String message;
}
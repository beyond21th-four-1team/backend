package com.mac4.yeopabackend.post.dto;

import java.time.LocalDate;

public record MypageResponse (
        String image,
        String title,
        String singleText,
        LocalDate createdAt,
        String location
        ){
}

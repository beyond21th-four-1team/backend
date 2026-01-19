package com.mac4.yeopabackend.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record PostResponse (
        Long userId,
        String title,
        String location,
        String text,
        String image,
        String singleText
){

}

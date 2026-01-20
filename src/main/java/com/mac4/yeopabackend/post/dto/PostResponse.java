package com.mac4.yeopabackend.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public record PostResponse (
        Long id,
        Long userId,
        String username,
        String title,
        String location,
        String text,
        String image,
        String singleText,
        LocalDate date
){

}

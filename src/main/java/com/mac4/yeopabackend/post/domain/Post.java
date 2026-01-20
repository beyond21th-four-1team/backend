package com.mac4.yeopabackend.post.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mac4.yeopabackend.post.dto.PostRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column
    private Long userId;

    @NotNull
    @Column
    private String nickname;

    @NotNull
    @Column
    private String title;

    @NotNull
    @Column
    private String location;

    @Lob
    @NotNull
    @Column
    private String text;

    @NotNull
    @Column
    private String objectKey;

    @NotNull
    @Column
    private String originalName;

    @NotNull
    @Column
    private String image;

    @Column(name = "creat_at")
    @CreatedDate
    private LocalDate createdAt;

    @Lob
    @NotNull
    @Column
    private String singleText;

    public static Post from(Long userId, String nickname,PostRequest req, String image, String objectKey, String originalName) {
        return new Post(null, userId, nickname,req.getTitle(), req.getLocation(), req.getText(),objectKey, originalName, image,LocalDate.now(),req.getSingleText());
    }

}

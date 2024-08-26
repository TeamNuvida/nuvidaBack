package com.nuvida.nuvidaback.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Post {
    private int post_seq;
    private String user_id;
    private String user_nick;
    private String profile_img;
    private int views;
    private String post_title;
    private String details;
    private String regi_at;
    private String image;
    private int cmtCount;
    private int intCount;

    private List<IMAGES> images;
    private List<Comments> comments;

    private List<String> imageList;

    public Post(String user_id, String post_title, String details) {
        this.user_id = user_id;
        this.post_title = post_title;
        this.details = details;
    }

    public Post(int post_seq, String user_nick, String profile_img, String post_title, String details, String regi_at, List<IMAGES> images, int intCount, List<Comments> comments) {
        this.post_seq = post_seq;
        this.user_nick = user_nick;
        this.profile_img = profile_img;
        this.post_title = post_title;
        this.details = details;
        this.regi_at = regi_at;
        this.images = images;
        this.intCount = intCount;
        this.comments = comments;
    }

}

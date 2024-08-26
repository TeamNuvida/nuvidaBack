package com.nuvida.nuvidaback.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    private int cmt_seq;
    private int post_seq;
    private int cmt_num;
    private int cmt_level;
    private String user_id;
    private String user_nick;
    private String profile;
    private String cmt_detail;
    private String regi_at;
}

package com.nuvida.nuvidaback.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MEMBERS {
    private int mem_seq;
    private int plan_seq;
    private String user_id;
    private String mem_type;
    private String regi_at;
    private String user_nick;
    private String profile_img;
}

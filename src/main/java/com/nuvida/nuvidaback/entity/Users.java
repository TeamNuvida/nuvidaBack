package com.nuvida.nuvidaback.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    private String user_id;
    private String user_name;
    private String user_nick;
    private String user_phone;
    private String user_type;
    private String profile_img;
    private int user_point;
}

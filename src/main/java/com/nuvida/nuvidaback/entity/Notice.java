package com.nuvida.nuvidaback.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private int nt_seq;
    private String user_id;
    private String nt_type;
    private String message;
    private String state;
    private String nt_at;
}

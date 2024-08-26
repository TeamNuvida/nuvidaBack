package com.nuvida.nuvidaback.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Baseball {
    private int bs_seq;
    private String team_name;
    private String logo_img;
    private String match_date;
    private int score;
    private int op_score;
    private String state;

}

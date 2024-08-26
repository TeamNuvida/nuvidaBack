package com.nuvida.nuvidaback.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Batting {
    private int kiaBtPoint;   // 기아 배팅 포인트 합
    private int opBtPoint;    // 상대팀 배팅 포인트 합
    private String op_team;
    private int op_seq;
    private int bt_point; // 유저가 배팅한 포인트
    private String team_name;  // 배팅한 팀
    private String result; // 배팅의 결과

    private int bs_seq;
    private String logo_img;
    private String match_date;


    public Batting(int kiaBtPoint, int opBtPoint) {
        this.opBtPoint = opBtPoint;
        this.kiaBtPoint = kiaBtPoint;
    }


    public Batting(int bt_point, String team_name, String result) {
        this.bt_point = bt_point;
        this.team_name = team_name;
        this.result = result;
    }

    public Batting(int bs_seq, String match_date, String team_name, String logo_img, int kiaBtPoint, int opBtPoint) {
        this.kiaBtPoint = kiaBtPoint;
        this.opBtPoint = opBtPoint;
        this.team_name = team_name;
        this.bs_seq = bs_seq;
        this.logo_img = logo_img;
        this.match_date = match_date;
    }
}

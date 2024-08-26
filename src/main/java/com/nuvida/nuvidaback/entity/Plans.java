package com.nuvida.nuvidaback.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Plans {
    private int plan_seq;
    private String plan_name;
    private String start_date;
    private String end_date;
    private int member;
    private String create_at;

    public Plans(String plan_name, String start_date, String end_date) {
        this.plan_name = plan_name;
        this.start_date = start_date;
        this.end_date = end_date;
    }
}

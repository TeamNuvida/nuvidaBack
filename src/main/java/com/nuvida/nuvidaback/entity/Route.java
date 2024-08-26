package com.nuvida.nuvidaback.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private int route_seq;
    private int plan_seq;
    private int seq;
    private String title;
    private String addr;
    private String image;
    private String contentid;
    private String contenttypeid;
    private int lat;
    private int lng;
    private String travel_date;
    private String reser_dt;
}

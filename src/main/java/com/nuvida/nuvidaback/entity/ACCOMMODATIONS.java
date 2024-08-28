package com.nuvida.nuvidaback.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ACCOMMODATIONS {
    private int acc_seq;
    private int plan_seq;
    private String acc_name;
    private String acc_addr;
    private String check_in;
    private String check_out;
    private float lat;
    private float lng;
    private String contentid;
    private String contenttypeid;
    private String regi_at;

}

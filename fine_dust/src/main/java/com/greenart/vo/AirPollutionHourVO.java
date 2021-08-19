package com.greenart.vo;

import java.util.Date;

import lombok.Data;

@Data
public class AirPollutionHourVO {
    private Integer seq;
    private Integer daegu;
    private Integer chungnam;
    private Integer incheon;
    private Integer daejeon;
    private Integer gyeongbuk;
    private Integer sejong;
    private Integer gwangju;
    private Integer jeonbuk;
    private Integer gangwon;
    private Integer ulsan;
    private Integer jeonnam;
    private Integer seoul;
    private Integer busan;
    private Integer jeju;
    private Integer chungbuk;
    private Integer gyeongnam;
    private Date dataTime;
    private String dataGubun;
    private Integer gyeonggi;
    private String itemCode;
}

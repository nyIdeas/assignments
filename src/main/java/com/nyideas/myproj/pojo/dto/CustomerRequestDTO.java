package com.nyideas.myproj.pojo.dto;

import lombok.Data;

@Data
public class CustomerRequestDTO {

    private Long customerId;
    private Integer contractId;
    private String geoZone;
    private String teamCode;
    private String projectCode;
    private Long buildDuration;
}

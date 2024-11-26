package com.nyideas.myproj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Customer {
    //customerId,contractId,geozone,teamcode,projectcode,buildduration
//2343225,2345,us_east,RedTeam,ProjectApple,3445s
    private Long customerId;
    private Integer contractId;
    private String geoZone;
    private String teamCode;
    private String projectCode;
    private Long buildDuration;

}

package com.nyideas.myproj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("customerData")
public class Customer {
    //customerId,contractId,geozone,teamcode,projectcode,buildduration
//2343225,2345,us_east,RedTeam,ProjectApple,3445s
    @Id
    private String id;
    private Long customerId;
    private Integer contractId;
    private String geoZone;
    private String teamCode;
    private String projectCode;
    private Long buildDuration;

}

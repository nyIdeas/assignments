package com.nyideas.myproj;

import com.nyideas.myproj.service.ProcessingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.Arrays;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CustomerProcessorApp {

    public static void main(String[] args) {
        SpringApplication.run(CustomerProcessorApp.class);
//        String customers = "2343225,2345,us_east,RedTeam,ProjectApple,3445s\n" +
//                "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n" +
//                "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n" +
//                "1233456,2345,us_west,BlueTeam,ProjectDate,2221s\n" +
//                "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";
//        ProcessingService processingService = new ProcessingService();
//        String[] customersRecordsArr = customers.split("\\n");
//        processingService.processCustomerRecords(Arrays.asList(customersRecordsArr));
    }
}

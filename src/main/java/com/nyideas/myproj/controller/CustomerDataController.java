package com.nyideas.myproj.controller;

import com.nyideas.myproj.pojo.Customer;
import com.nyideas.myproj.pojo.dto.CustomerRequestDTO;
import com.nyideas.myproj.pojo.dto.CustomerResponseDTO;
import com.nyideas.myproj.service.ProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerDataController {

    private final ProcessingService processingService;

    @PostMapping(value = "/processData")
    public ResponseEntity<List<String>> processCustomerData(@RequestBody List<String> customerDataList){
        List<String> reportData = processingService.processData(customerDataList);
        return new ResponseEntity<List<String>>(reportData,HttpStatus.OK);
    }

    private List<Customer> convertDTO(List<CustomerRequestDTO> customerRequestDTOS) {
        List<Customer> customers = new ArrayList<>();
        for(CustomerRequestDTO customerRequestDTO : customerRequestDTOS){
            Customer customer = new Customer(null,customerRequestDTO.getCustomerId(),customerRequestDTO.getContractId(),customerRequestDTO.getGeoZone(),customerRequestDTO.getTeamCode(),customerRequestDTO.getProjectCode(),customerRequestDTO.getBuildDuration());
            customers.add(customer);
        }
        return customers;
    }
}

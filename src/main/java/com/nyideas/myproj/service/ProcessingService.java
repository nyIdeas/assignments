package com.nyideas.myproj.service;

import com.nyideas.myproj.pojo.Customer;
import com.nyideas.myproj.repository.CustomerDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The number of unique customerId for each contractId
 * The number of unique customerId for each geozone
 * The average buildduration for each geozone
 * The list of unique customerId for each geozone
 */
@Service
@AllArgsConstructor
public class ProcessingService {

    private final CustomerDataRepository repository;

    public  List<String> processData(List<String> customerData){
        List<String> report = new ArrayList<>();
        try{
            report = processCustomerRecords(customerData);
        }
        catch (Exception e){
            System.out.println("Exception Occured "+e.getMessage());
        }
        return  report;
    }

    public List<String> processCustomerRecords(List<String> records) {
        List<Customer> customers = new ArrayList<>();
        List<String> reportData = new ArrayList<>();
        for (String line : records) {
            String[] details = line.split(",");
            long duration = 0L;
            if (details[5] != null) {
                duration = Long.parseLong(details[5].split("s")[0]);
            }
            customers.add(new Customer(null,Long.parseLong(details[0]), Integer.parseInt(details[1]), details[2], details[3], details[4], duration));
        }
        Map<Integer, Set<Long>> uniqueCustomerIdByContractId = findUniqueCustomerIdByContractId(customers);
        System.out.println("The number of unique customerId for each contractId ");
        reportData.add("The number of unique customerId for each contractId ");
        uniqueCustomerIdByContractId.forEach((id, set) -> {
            System.out.println("ContractId : "+id +", CustomerIds : "+set.size());
            reportData.add("\nContractId :"+id +", CustomerIds : "+set.size());
        });


        Map<String, Set<Long>> uniqueCustomerIdByGeoZone = findUniqueCustomerIdByGeoZone(customers);

        System.out.println("\nThe number of unique customerId for each geoZone ");
        reportData.add("The number of unique customerId for each geoZone ");
        uniqueCustomerIdByGeoZone.forEach((id, set) -> {
            System.out.println("geoZone : "+id +", CustomerIds : "+set.size());
            reportData.add("GeoZone : "+id +", CustomerIds : "+set.size());
        });

        System.out.println("\nThe average buildduration for each geozone ");
        reportData.add("The average buildduration for each geozone ");
        Map<String, Double> avgBuildDurationByGeoZone = findAvgBuildDurationByGeoZone(customers);
        avgBuildDurationByGeoZone.forEach((s, aDouble) -> {
            System.out.println("geoZone : "+s+", Average Build Duration :"+aDouble);
            reportData.add("geoZone : "+s+", Average Build Duration :"+aDouble);
        });

        Map<String, Set<Long>> uniqueCustomersByGeoZone = findUniqueCustomerIdByGeoZone(customers);

        System.out.println("\nThe list of unique customerId for each geoZone ");
        reportData.add("The list of unique customerId for each geoZone ");
        uniqueCustomerIdByGeoZone.forEach((id, set) -> {
            System.out.println("geoZone : "+id +", CustomerIds : "+set);
            reportData.add("geoZone : "+id +", CustomerIds : "+set);
        });

        //TO be separated  in another method.
        repository.saveAll(customers);

        return reportData;

    }

    private Map<Integer,Set<Long>> findUniqueCustomerIdByContractId(List<Customer> customers){
        Map<Integer,Set<Long>> contractIdVsCustomerIdSetMap = new HashMap<>();
        customers.forEach(customer -> {
            Set<Long> customerIdSet = contractIdVsCustomerIdSetMap.getOrDefault(customer.getContractId(),new HashSet<>());
            customerIdSet.add(customer.getCustomerId());
            contractIdVsCustomerIdSetMap.put(customer.getContractId(),customerIdSet);
        });
        return contractIdVsCustomerIdSetMap;
    }

    private Map<String,Set<Long>> findUniqueCustomerIdByGeoZone(List<Customer> customers){
        Map<String,Set<Long>> geoZoneVsCustomerIdSetMap = new HashMap<>();
        customers.forEach(customer -> {
            Set<Long> customerIdSet = geoZoneVsCustomerIdSetMap.getOrDefault(customer.getGeoZone(),new HashSet<>());
            customerIdSet.add(customer.getCustomerId());
            geoZoneVsCustomerIdSetMap.put(customer.getGeoZone(),customerIdSet);
        });
        return geoZoneVsCustomerIdSetMap;
    }

    private Map<String,Double> findAvgBuildDurationByGeoZone(List<Customer> customers){
        Map<String, Double> geoZoneVsAvgDurationMap = customers.stream().collect(Collectors.groupingBy(
                Customer::getGeoZone, Collectors.averagingDouble(Customer::getBuildDuration)
        ));
        return geoZoneVsAvgDurationMap;
    }


}

package com.nyideas.myproj.service;

import com.nyideas.myproj.pojo.Customer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The number of unique customerId for each contractId
 * The number of unique customerId for each geozone
 * The average buildduration for each geozone
 * The list of unique customerId for each geozone
 */
public class ProcessingService {

    public void processCustomerRecords(List<String> records) {
        List<Customer> customers = new ArrayList<>();
        for (String line : records) {
            String[] details = line.split(",");
            long duration = 0L;
            if (details[5] != null) {
                duration = Long.parseLong(details[5].split("s")[0]);
            }
            customers.add(new Customer(Long.parseLong(details[0]), Integer.parseInt(details[1]), details[2], details[3], details[4], duration));
        }
        Map<Integer, Set<Long>> uniqueCustomerIdByContractId = findUniqueCustomerIdByContractId(customers);
        System.out.println("The number of unique customerId for each contractId ");
        uniqueCustomerIdByContractId.forEach((id, set) -> {
            System.out.println("ContractId : "+id +", CustomerIds : "+set.size());
        });

        Map<String, Set<Long>> uniqueCustomerIdByGeoZone = findUniqueCustomerIdByGeoZone(customers);

        System.out.println("\nThe number of unique customerId for each geoZone ");
        uniqueCustomerIdByGeoZone.forEach((id, set) -> {
            System.out.println("geoZone : "+id +", CustomerIds : "+set.size());
        });

        System.out.println("\nThe average buildduration for each geozone ");
        Map<String, Double> avgBuildDurationByGeoZone = findAvgBuildDurationByGeoZone(customers);
        avgBuildDurationByGeoZone.forEach((s, aDouble) -> {
            System.out.println("geoZone : "+s+", Average Build Duration :"+aDouble);
        });

        Map<String, Set<Long>> uniqueCustomersByGeoZone = findUniqueCustomerIdByGeoZone(customers);

        System.out.println("\nThe list of unique customerId for each geoZone ");
        uniqueCustomerIdByGeoZone.forEach((id, set) -> {
            System.out.println("geoZone : "+id +", CustomerIds : "+set);
        });

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

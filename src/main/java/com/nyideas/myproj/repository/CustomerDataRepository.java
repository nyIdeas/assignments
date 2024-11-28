package com.nyideas.myproj.repository;

import com.nyideas.myproj.pojo.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerDataRepository extends MongoRepository<Customer,String> {

}

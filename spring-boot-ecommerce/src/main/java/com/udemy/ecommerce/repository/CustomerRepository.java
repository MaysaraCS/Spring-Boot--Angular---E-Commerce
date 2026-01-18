package com.udemy.ecommerce.repository;

import com.udemy.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    // behind the sense it is SELECT * FROM Customer c WHERE c.email = theEmail
    Customer findByEmail(String theEmail);

}

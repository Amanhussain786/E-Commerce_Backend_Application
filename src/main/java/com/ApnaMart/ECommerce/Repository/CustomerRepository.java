package com.ApnaMart.ECommerce.Repository;

import com.ApnaMart.ECommerce.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    public Customer getCustomerByEmail(String email);
}

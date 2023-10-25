package com.ApnaMart.ECommerce.Repository;

import com.ApnaMart.ECommerce.Model.Card;
import com.ApnaMart.ECommerce.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
    public List<Card> findByCustomer(Customer customer);
}

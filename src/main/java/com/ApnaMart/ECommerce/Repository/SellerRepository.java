package com.ApnaMart.ECommerce.Repository;

import com.ApnaMart.ECommerce.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {
    Seller getSellerByPanNo(String panNo);

    List<Seller> getSellersByAge(int age);
}

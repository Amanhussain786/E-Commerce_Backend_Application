package com.ApnaMart.ECommerce.Repository;

import com.ApnaMart.ECommerce.Enum.ProductCategory;
import com.ApnaMart.ECommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    public List<Product> findAllByProductCategory(ProductCategory category);

}

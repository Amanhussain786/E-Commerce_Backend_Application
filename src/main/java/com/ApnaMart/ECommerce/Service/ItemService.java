package com.ApnaMart.ECommerce.Service;

import com.ApnaMart.ECommerce.Exception.ProductNotFoundException;
import com.ApnaMart.ECommerce.Model.Item;
import com.ApnaMart.ECommerce.Model.Product;
import com.ApnaMart.ECommerce.Repository.ItemRepository;
import com.ApnaMart.ECommerce.Repository.ProductRepository;
import com.ApnaMart.ECommerce.ResponseDTO.ItemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ItemRepository itemRepository;

    public ItemResponseDto viewItem(int productId) throws ProductNotFoundException {
        //check product exist or not
        Product product;
        try {
            product = productRepository.findById(productId).get();
        }
        catch (Exception e)
        {
            throw new ProductNotFoundException("Sorry Invalid Product ID");
        }

      /*  Item item = Item.builder()
                .requiredQuantity(0)
                .product(product)
                .build();

       */

        //map item to product
        //product.setItem(item);

        //save item and product
        productRepository.save(product);

        //prepare responsedto
        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .productName(product.getProductName())
                .price(product.getPrice())
                .productCategory(product.getProductCategory())
                .productStatus(product.getProductStatus())
                .build();

        return itemResponseDto;
    }
}

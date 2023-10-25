package com.ApnaMart.ECommerce.Service;

import com.ApnaMart.ECommerce.Converter.ProductConverter;
import com.ApnaMart.ECommerce.Enum.ProductCategory;
import com.ApnaMart.ECommerce.Exception.ProductsNotFoundException;
import com.ApnaMart.ECommerce.Exception.SellerNotFoundException;
import com.ApnaMart.ECommerce.Model.Product;
import com.ApnaMart.ECommerce.Model.Seller;
import com.ApnaMart.ECommerce.Repository.ProductRepository;
import com.ApnaMart.ECommerce.Repository.SellerRepository;
import com.ApnaMart.ECommerce.RequestDTO.ProductRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.ProductResponseDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductRepository productRepository;


    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException
    {
        //check seller exist or not first
        Seller seller;

        try {
            seller = sellerRepository.findById(productRequestDto.getSellerId()).get();
        }
        catch (Exception e)
        {
            //create our own exception
            throw new SellerNotFoundException("Invalid Seller ID");
        }

        //now product object
        Product product = ProductConverter.productRequestDtotoProduct(productRequestDto);
        product.setSeller(seller);

        seller.getProducts().add(product);

        //saved seller and product (seller = parent , product = child)
        sellerRepository.save(seller);

        //converter
        ProductResponseDto productResponseDto = ProductConverter.productToProductResponseDto(product);
        return productResponseDto;
    }

    public String deleteProductById(int id)
    {
        productRepository.deleteById(id);
        return "Product Deleted Successfully !!";
    }

    public List<ProductResponseDto> getProductsByCategoty(ProductCategory productCategory)
    {
        List<Product> products = productRepository.findAllByProductCategory(productCategory);

        //convert product to productresponseDto
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for(int i=0;i<products.size();i++)
        {
            ProductResponseDto productResponseDto = ProductConverter.productToProductResponseDto(products.get(i));
            productResponseDtos.add(productResponseDto);
        }
       return productResponseDtos;
    }
}

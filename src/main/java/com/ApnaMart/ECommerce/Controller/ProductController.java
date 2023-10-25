package com.ApnaMart.ECommerce.Controller;

import com.ApnaMart.ECommerce.Enum.ProductCategory;
import com.ApnaMart.ECommerce.Exception.ProductsNotFoundException;
import com.ApnaMart.ECommerce.Model.Product;
import com.ApnaMart.ECommerce.RequestDTO.ProductRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.ProductResponseDto;
import com.ApnaMart.ECommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add_product")
    public ResponseEntity addProduct(@RequestBody ProductRequestDto productRequestDto)
    {
        ProductResponseDto productResponseDto;
        try {
           productResponseDto =  productService.addProduct(productRequestDto);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(productResponseDto,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete_product_by_Id")
    public ResponseEntity<?> deleteProductById(@RequestParam("id") int id)
    {
        try {
            productService.deleteProductById(id);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Product Not Exist with such Id",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Product Deleted Successfully!!",HttpStatus.ACCEPTED);
    }

    @GetMapping("/get_products_by_category")
    public ResponseEntity<?> getProductsByCategoty(@RequestParam("category")ProductCategory productCategory)
    {
        List<ProductResponseDto> productResponseDtos =  productService.getProductsByCategoty(productCategory);
        if(productResponseDtos.isEmpty())
        {
            return new ResponseEntity<>("Product not exist of that Categoty",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productResponseDtos,HttpStatus.ACCEPTED);
    }
}

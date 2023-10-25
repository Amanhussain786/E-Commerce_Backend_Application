package com.ApnaMart.ECommerce.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductsNotFoundException extends Exception{

    public ProductsNotFoundException(String message){
        super(message);
    }
}

//        try {
//            List<ProductResponseDto> productResponseDtos =  productService.getProductsByCategoty(productCategory);
//            return new ResponseEntity<>(productResponseDtos,HttpStatus.ACCEPTED);
//        }
//       catch (Exception e)
//       {
//           return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//       }

// if(productResponseDtos.isEmpty())
//         {
//         throw  new ProductsNotFoundException("Product Not Found");
//         }
//         return productResponseDtos;

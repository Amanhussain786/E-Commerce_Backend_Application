package com.ApnaMart.ECommerce.Converter;

import com.ApnaMart.ECommerce.Enum.ProductStatus;
import com.ApnaMart.ECommerce.Model.Product;
import com.ApnaMart.ECommerce.RequestDTO.ProductRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.ProductResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductConverter {
    public static Product productRequestDtotoProduct(ProductRequestDto productRequestDto)
    {
        return Product.builder().productName(productRequestDto.getProductName())
                .price(productRequestDto.getPrice())
                .quantity(productRequestDto.getQuantity())
                .productCategory(productRequestDto.getProductCategory())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }

    public static ProductResponseDto productToProductResponseDto(Product product)
    {
        return  ProductResponseDto.builder()
                .name(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .productStatus(product.getProductStatus())
                .build();
    }
}

package com.ApnaMart.ECommerce.ResponseDTO;

import com.ApnaMart.ECommerce.Enum.ProductCategory;
import com.ApnaMart.ECommerce.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponseDto {

    private String productName;
    private int price;
    private ProductCategory productCategory;
    private ProductStatus productStatus;
}

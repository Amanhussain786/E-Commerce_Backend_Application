package com.ApnaMart.ECommerce.Converter;

import com.ApnaMart.ECommerce.Model.Seller;
import com.ApnaMart.ECommerce.RequestDTO.SellerRequestDTO;

public class SellerConverter {
    public static Seller SellerRequestDTOtoSeller(SellerRequestDTO sellerRequestDTO)
    {
         return  Seller.builder()
                .age(sellerRequestDTO.getAge())
                .panNo(sellerRequestDTO.getPanNo())
                .email(sellerRequestDTO.getEmail())
                .mobNo(sellerRequestDTO.getMobNo())
                .name(sellerRequestDTO.getName()).build();
    }
}

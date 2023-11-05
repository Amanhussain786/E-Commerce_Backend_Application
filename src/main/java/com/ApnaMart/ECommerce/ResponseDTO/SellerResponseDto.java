package com.ApnaMart.ECommerce.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SellerResponseDto {
    private String name;

    private int age;

    private String mobNo;

    private String email;

    private String panNo;
}

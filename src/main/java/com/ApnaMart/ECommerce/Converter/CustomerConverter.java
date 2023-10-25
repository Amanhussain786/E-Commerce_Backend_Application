package com.ApnaMart.ECommerce.Converter;

import com.ApnaMart.ECommerce.Model.Customer;
import com.ApnaMart.ECommerce.RequestDTO.CustomerRequestDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerConverter {

    public Customer CustomerRequestDtotoCustomer(CustomerRequestDto customerRequestDto)
    {
        return Customer.builder()
                .name(customerRequestDto.getName())
                .age(customerRequestDto.getAge())
                .email(customerRequestDto.getEmail())
                .mobNo(customerRequestDto.getMobNo())
                .build();
    }
}

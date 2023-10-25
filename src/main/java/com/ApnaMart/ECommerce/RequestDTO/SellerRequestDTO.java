package com.ApnaMart.ECommerce.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerRequestDTO {

     private String name;

     private int age;

     private String mobNo;

     private String email;

     private String panNo;
}

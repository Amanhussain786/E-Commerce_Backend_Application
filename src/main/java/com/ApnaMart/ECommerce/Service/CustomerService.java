package com.ApnaMart.ECommerce.Service;

import com.ApnaMart.ECommerce.Converter.CustomerConverter;
import com.ApnaMart.ECommerce.Exception.CustomerNotFoundException;
import com.ApnaMart.ECommerce.Model.Cart;
import com.ApnaMart.ECommerce.Model.Customer;
import com.ApnaMart.ECommerce.Repository.CustomerRepository;
import com.ApnaMart.ECommerce.RequestDTO.CustomerRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.CustomerResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public String addCustomer(CustomerRequestDto customerRequestDto)
    {
       Customer customer = CustomerConverter.CustomerRequestDtotoCustomer(customerRequestDto);

       //we have to set empty cart
        Cart cart = new Cart();
        cart.setCartTotal(0);
        cart.setCustomer(customer);

        //set cart in customer
        customer.setCart(cart);

        //set the customer
        customerRepository.save(customer);

        return "Customer Added Successfully!!";
    }

    public List<CustomerResponseDto> getAllCustomers()
    {
        List<Customer> customers = customerRepository.findAll();

        //convert customer to customer responsedto

        List<CustomerResponseDto> customerResponseDtos = new ArrayList<>();

        for(int i=0;i<customers.size();i++)
        {
            CustomerResponseDto customerResponseDto;
            Customer customer = customers.get(i);
           customerResponseDto =  CustomerResponseDto.builder()
                    .name(customer.getName())
                    .age(customer.getAge())
                    .email(customer.getEmail())
                    .mobNo(customer.getMobNo())
                    .build();

            customerResponseDtos.add(customerResponseDto);
        }
        return customerResponseDtos;
    }

    public CustomerResponseDto getCustomerById(int id){

        Customer customer = customerRepository.findById(id).get();

       //convert customer to customerResponseDto
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto = CustomerResponseDto.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmail())
                .mobNo(customer.getMobNo())
                .build();

        return customerResponseDto;
    }

    public String deleteCustomerById(int id)
    {
        customerRepository.deleteById(id);
        return "Customer Deleted Successfully!";
    }

    public CustomerResponseDto getCustomerByEmail(String email)
    {
        Customer customer = customerRepository.getCustomerByEmail(email);

        CustomerResponseDto customerResponseDto;

       customerResponseDto = CustomerResponseDto.builder()
               .name(customer.getName())
               .age(customer.getAge())
               .email(customer.getEmail())
               .mobNo(customer.getMobNo())
               .build();

       return customerResponseDto;
    }
}

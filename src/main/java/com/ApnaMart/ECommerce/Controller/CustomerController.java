package com.ApnaMart.ECommerce.Controller;
import com.ApnaMart.ECommerce.Exception.CustomerNotFoundException;
import com.ApnaMart.ECommerce.Model.Customer;
import com.ApnaMart.ECommerce.RequestDTO.CustomerRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.CustomerResponseDto;
import com.ApnaMart.ECommerce.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/add_customer")
    public String addCustomer(@RequestBody CustomerRequestDto customerRequestDto)
    {
        return customerService.addCustomer(customerRequestDto);
    }

    @GetMapping("/get_all_customer")
    public List<CustomerResponseDto> getAllCustomers()
    {
        return customerService.getAllCustomers();
    }

    @GetMapping("get_customer_by_id")
    public ResponseEntity<?> getCustomerById(@RequestParam("id") int id){

        CustomerResponseDto customerResponseDto;
        try {
            customerResponseDto = customerService.getCustomerById(id);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Customer with such Id does not Exist!!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerResponseDto,HttpStatus.ACCEPTED);
    }


    //implementing this
    @DeleteMapping("/delete_customer_by_id")
    public ResponseEntity<?> deleteCustomerById(@RequestParam("id") int id)
    {
        try {
          customerService.deleteCustomerById(id);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Customer with such ID not Exist",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Customer Deleted Successfully!",HttpStatus.ACCEPTED);
    }

    @GetMapping("/get_customer_by_email")
    public ResponseEntity<?> getCustomerByEmail(@RequestParam("email") String email)
    {
        CustomerResponseDto customerResponseDto;

        try {
            customerResponseDto = customerService.getCustomerByEmail(email);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("No Customer Exist With such Email!",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerResponseDto,HttpStatus.ACCEPTED);
    }

    //update the customer

}

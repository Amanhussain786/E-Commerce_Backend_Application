package com.ApnaMart.ECommerce.Controller;

import com.ApnaMart.ECommerce.Model.Cart;
import com.ApnaMart.ECommerce.RequestDTO.OrderRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.OrderResponseDto;
import com.ApnaMart.ECommerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/add_to_cart")
    public String addToCart(@RequestBody OrderRequestDto orderRequestDto)
    {
        String response = "";
        try {
            response = cartService.addToCart(orderRequestDto);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return response;
    }

    @PostMapping("/order_everything_from_cart")
    public ResponseEntity<?> checkOutCart(@RequestParam("customerId") int id)
    {
        List<OrderResponseDto> orderResponseDtos;
        try {
            orderResponseDtos = cartService.checkOutCart(id);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderResponseDtos,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/remove_from_cart_by_id")
    public ResponseEntity<?> cleanCart()
    {
        try {
            cartService.cleanCart();
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Cart is empty no need to clear!!",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Cart is clear Now!!",HttpStatus.ACCEPTED);
    }
}

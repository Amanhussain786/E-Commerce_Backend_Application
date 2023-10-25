package com.ApnaMart.ECommerce.Controller;

import com.ApnaMart.ECommerce.Model.Ordered;
import com.ApnaMart.ECommerce.RequestDTO.OrderRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.OrderResponseDto;
import com.ApnaMart.ECommerce.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

  //  place the order without adding to cart
    @Autowired
    OrderService orderService;
   @PostMapping("/place_order")
    public ResponseEntity placeOrder(@RequestBody OrderRequestDto orderRequestDto)
    {
        OrderResponseDto orderResponseDto;
        try{
            orderResponseDto = orderService.placeOrder(orderRequestDto);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(orderResponseDto,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete_order_by_id")
    public ResponseEntity<?> deleteOrderById(@RequestParam("id") int id)
    {
        try {
            orderService.deleteOrderById(id);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Order With Such Id Not Exist",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Order Deleted Successfully!!",HttpStatus.ACCEPTED);
    }

}

package com.ApnaMart.ECommerce.Service;
import com.ApnaMart.ECommerce.Enum.ProductStatus;
import com.ApnaMart.ECommerce.Exception.CustomerNotFoundException;
import com.ApnaMart.ECommerce.Exception.ProductNotFoundException;
import com.ApnaMart.ECommerce.Model.*;
import com.ApnaMart.ECommerce.Repository.CustomerRepository;
import com.ApnaMart.ECommerce.Repository.OrderRepository;
import com.ApnaMart.ECommerce.Repository.ProductRepository;
import com.ApnaMart.ECommerce.RequestDTO.OrderRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemService itemService;


 public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws Exception {

//        //customer is valid or not
//        //product exist or not
//       //quantity is sufficient or not
//       //view item first also get the item
//        //if it comes here means customer ID is valid, product exist, quantity is available,view
//      //now we are ready to place the order
//        //calculate the total cost of product
//       //prepare order
//       //prepare the card String
//        //update customer current order list
//        //out of stock case handle
//        //after placing order product quantity decrease
//        //subtract from product
//       //update item
//       //save customer, order automatically saved && product, item automatically saved
//        //prepare responseDto
       Customer customer;
       try{
           customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();
       }
       catch(Exception e){
           throw new CustomerNotFoundException("Invalid Customer id !!!");
       }

       Product product;
       try{
          product = productRepository.findById(orderRequestDto.getProductId()).get();
       }
       catch (Exception e){
           throw new ProductNotFoundException("Invalid Product Id");
       }

       if(product.getQuantity()<orderRequestDto.getRequiredQuantity() || product.getQuantity()==0){
           throw new Exception("Sorry! Required quantity not available");
       }

       Ordered order = new Ordered();
       order.setTotalCost(orderRequestDto.getRequiredQuantity()* product.getPrice());
       order.setDeliveryCharge(40);

       Card card = customer.getCards().get(0);

       String cardNo = "";
       for(int i=0;i<card.getCardNo().length()-4;i++)
           cardNo += 'X';

       cardNo += card.getCardNo().substring(card.getCardNo().length()-4);
       order.setCardUsedForPayment(cardNo);

       Item item = new Item();
       item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());
       item.setProduct(product);
       item.setOrder(order);
       order.getOrderedItems().add(item);
       order.setCustomer(customer);

       int leftQuantity = product.getQuantity()-orderRequestDto.getRequiredQuantity();
       if(leftQuantity<=0)
           product.setProductStatus(ProductStatus.OUT_OF_STOCK);
       product.setQuantity(leftQuantity);

       productRepository.save(product);

       customer.getOrders().add(order);
       Customer savedCustomer = customerRepository.save(customer);
       Ordered savedOrder = savedCustomer.getOrders().get(savedCustomer.getOrders().size()-1);

       //prepare response DTO
       OrderResponseDto orderResponseDto = OrderResponseDto.builder()
               .productName(product.getProductName())
               .orderDate(savedOrder.getOrderDate())
               .quantityOrdered(orderRequestDto.getRequiredQuantity())
               .cardUsedForPayment(cardNo)
               .itemPrice(product.getPrice())
               .totalCost(order.getTotalCost())
               .deliveryCharge(40)
               .build();

     String text = "Congrats your order with total value "+ order.getTotalCost()+" has been placed!!";

     SimpleMailMessage message = new SimpleMailMessage();
     message.setFrom("backendaman123@gmail.com");
     message.setTo(customer.getEmail());
     message.setSubject("Order Placed Notification");
     message.setText(text);
     emailSender.send(message);

       return orderResponseDto;
    }

    public String deleteOrderById(int id)
    {
        orderRepository.deleteById(id);
        return "Order Deleted Successfully!!";
    }

}

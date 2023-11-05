package com.ApnaMart.ECommerce.Service;

import com.ApnaMart.ECommerce.Enum.ProductStatus;
import com.ApnaMart.ECommerce.Exception.CustomerNotFoundException;
import com.ApnaMart.ECommerce.Exception.ProductNotFoundException;
import com.ApnaMart.ECommerce.Model.*;
import com.ApnaMart.ECommerce.Repository.CartRepository;
import com.ApnaMart.ECommerce.Repository.CustomerRepository;
import com.ApnaMart.ECommerce.Repository.ProductRepository;
import com.ApnaMart.ECommerce.RequestDTO.OrderRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CartRepository cartRepository;

    public String addToCart(OrderRequestDto orderRequestDto) throws Exception {
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

        if(product.getQuantity()<orderRequestDto.getRequiredQuantity()){
            throw new Exception("Sorry! Required quantity not available");
        }

        Cart cart = customer.getCart();

        //set new cart total after adding all items to the cart
        int newCost = cart.getCartTotal()+(orderRequestDto.getRequiredQuantity()*product.getPrice());
        cart.setCartTotal(newCost);

        //add item to current cart
        Item item = new Item();
        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());
        item.setCart(cart);
        item.setProduct(product);
        cart.getItems().add(item);

        customerRepository.save(customer);
        return "Item has been added to your cart!!";
    }

    //order every Item from cart
    public List<OrderResponseDto> checkOutCart(int id) throws CustomerNotFoundException {
        //first check customer is valid or not
        Customer customer;
        try{
            customer = customerRepository.findById(id).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid Customer id !!!");
        }

        //to order we need customerId,productId,requiredQuantity
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        int totalCost = customer.getCart().getCartTotal();

        Cart cart = customer.getCart();

        for(Item item : cart.getItems())
        {
            Ordered order = new Ordered();
            order.setTotalCost(item.getRequiredQuantity()*item.getProduct().getPrice());
            order.setDeliveryCharge(0);
            //order.setOrderDate(item.getOrder().getOrderDate());
            order.setCustomer(customer);
            order.getOrderedItems().add(item);

            Card card = customer.getCards().get(0);

            String cardNo = "";
            for(int i=0;i<card.getCardNo().length()-4;i++)
                cardNo += 'X';

            cardNo += card.getCardNo().substring(card.getCardNo().length()-4);
            order.setCardUsedForPayment(cardNo);

            int leftQuantity = item.getProduct().getQuantity()-item.getRequiredQuantity();
            if(leftQuantity<=0)
                item.getProduct().setProductStatus(ProductStatus.OUT_OF_STOCK);
            item.getProduct().setQuantity(leftQuantity);

            customer.getOrders().add(order);
            //System.out.println(order.getOrderDate());

            //prepare responseDto

            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .productName(item.getProduct().getProductName())
                    .orderDate(new Date())
                    .quantityOrdered(item.getRequiredQuantity())
                    .cardUsedForPayment(cardNo)
                    .itemPrice(item.getProduct().getPrice())
                    .totalCost(order.getTotalCost())
                    .deliveryCharge(40)
                    .build();

            orderResponseDtos.add(orderResponseDto);
        }

        cart.setItems(new ArrayList<>());
        cart.setCartTotal(0);
        customerRepository.save(customer);

        String text = "Congrats your order with total value "+ totalCost +" has been placed!!";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backendaman123@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Order Placed Notification");
        message.setText(text);
        emailSender.send(message);

        return orderResponseDtos;
    }

    public String cleanCart()
    {
        cartRepository.deleteAll();
        return "Item Deleted from the cart";
    }
}

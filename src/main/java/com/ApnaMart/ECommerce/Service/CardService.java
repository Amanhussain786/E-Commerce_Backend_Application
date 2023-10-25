package com.ApnaMart.ECommerce.Service;

import com.ApnaMart.ECommerce.Exception.CustomerNotFoundException;
import com.ApnaMart.ECommerce.Model.Card;
import com.ApnaMart.ECommerce.Model.Customer;
import com.ApnaMart.ECommerce.Repository.CardRepository;
import com.ApnaMart.ECommerce.Repository.CartRepository;
import com.ApnaMart.ECommerce.Repository.CustomerRepository;
import com.ApnaMart.ECommerce.RequestDTO.CardRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.CardDto;
import com.ApnaMart.ECommerce.ResponseDTO.CardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CardRepository cardRepository;


    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws CustomerNotFoundException {
        //fetch the customer first
        //Customer customer;
        Optional<Customer> temp = customerRepository.findById(cardRequestDto.getCustomerId());
        if(temp.isEmpty())
        {
            throw new CustomerNotFoundException("Customer with such ID does not Exist!");
        }

        Customer customer = temp.get();
        //make a card object
        Card card = Card.builder()
                .cardNo(cardRequestDto.getCardNo())
                .cvv(cardRequestDto.getCvv())
                .cardType(cardRequestDto.getCardType())
                .customer(customer)
                .build();

        //add card to current card list of customer
        customer.getCards().add(card);

        //save the customer card will automatically be saved
        customerRepository.save(customer);

        //return type is responseDto
        CardResponseDto cardResponseDto = new CardResponseDto();
        cardResponseDto.setName(customer.getName());

        //convert evey card to cardDtos
        List<CardDto> cardDtoList = new ArrayList<>();

        for(Card card1:customer.getCards())
        {
            CardDto cardDto = new CardDto();
            cardDto.setCardNo(card1.getCardNo());
            cardDto.setCardType(card1.getCardType());

            cardDtoList.add(cardDto);
        }
        cardResponseDto.setCardDtos(cardDtoList);

        return cardResponseDto;
    }

    //remove all card of a particular customer ID
    public String removeAllCardofCustomer(int id) throws Exception {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new Exception("Customer with such Id not Exist!!");
        }

        List<Card> cardsToDelete = cardRepository.findByCustomer(customer);

        for (Card card : cardsToDelete) {
            cardRepository.delete(card);
        }
        return "Cards removed Successfully!";
    }
}

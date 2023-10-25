package com.ApnaMart.ECommerce.Controller;

import com.ApnaMart.ECommerce.Model.Customer;
import com.ApnaMart.ECommerce.Repository.CustomerRepository;
import com.ApnaMart.ECommerce.RequestDTO.CardRequestDto;
import com.ApnaMart.ECommerce.ResponseDTO.CardResponseDto;
import com.ApnaMart.ECommerce.Service.CardService;
import com.ApnaMart.ECommerce.Service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping("/add_card")
    public ResponseEntity addCard(@RequestBody CardRequestDto cardRequestDto)
    {
        CardResponseDto cardResponseDto;
        try {
            cardResponseDto = cardService.addCard(cardRequestDto);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(cardResponseDto,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/remove_all_card_for_Particular_customer")
    public ResponseEntity<?> removeAllCardofCustomer(@RequestParam("id") int id)
    {
        try {
            cardService.removeAllCardofCustomer(id);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Customer Not Exist !!",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("All Card Related to a Particular customer deleted Successfully!!",HttpStatus.ACCEPTED);
    }
}

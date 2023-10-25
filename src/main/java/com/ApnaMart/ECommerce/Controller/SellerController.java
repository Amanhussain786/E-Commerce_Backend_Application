package com.ApnaMart.ECommerce.Controller;

import com.ApnaMart.ECommerce.Exception.SellerNotFoundException;
import com.ApnaMart.ECommerce.Model.Seller;
import com.ApnaMart.ECommerce.RequestDTO.SellerRequestDTO;
import com.ApnaMart.ECommerce.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/add_seller")
    public String addSeller(@RequestBody SellerRequestDTO sellerRequestDTO)
    {
        return sellerService.addSeller(sellerRequestDTO);
    }

    @GetMapping("/get_all_sellers")
    public List<Seller> getAllSellers()
    {
        return sellerService.getAllSellers();
    }

    @GetMapping("/get_seller_by_panNo")
    public Seller getSellerByPanNo(@RequestParam("panNo") String panNo)
    {
        return sellerService.getSellerByPanNo(panNo);
    }

    @GetMapping("/get_sellers_by_age")
    public List<Seller> getSellersByAge(@RequestParam("age") int age)
    {
        return sellerService.getSellersByAge(age);
    }

    @DeleteMapping("delete_seller_by_id")
    public ResponseEntity<?> deleteSellerById(@RequestParam("id") int id){
        try {
            sellerService.deleteSellerById(id);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Seller With Such Id Not Exist!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Seller Deleted Successfully!",HttpStatus.ACCEPTED);
    }
}

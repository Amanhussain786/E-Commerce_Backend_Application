package com.ApnaMart.ECommerce.Service;

import com.ApnaMart.ECommerce.Converter.SellerConverter;
import com.ApnaMart.ECommerce.Model.Seller;
import com.ApnaMart.ECommerce.Repository.SellerRepository;
import com.ApnaMart.ECommerce.RequestDTO.SellerRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    public String addSeller(SellerRequestDTO sellerRequestDTO)
    {
        Seller seller = SellerConverter.SellerRequestDTOtoSeller(sellerRequestDTO);

        sellerRepository.save(seller);

        return "Seller Added Successfully!";
    }

    public List<Seller> getAllSellers()
    {
        List<Seller> sellers = sellerRepository.findAll();
        return sellers;
    }

    public Seller getSellerByPanNo(String panNo)
    {
        return sellerRepository.getSellerByPanNo(panNo);
    }

    public List<Seller> getSellersByAge(int age)
    {
        return sellerRepository.getSellersByAge(age);
    }

    public String deleteSellerById(int id)
    {
        sellerRepository.deleteById(id);
        return "Seller Deleted Successfully !!";
    }
}

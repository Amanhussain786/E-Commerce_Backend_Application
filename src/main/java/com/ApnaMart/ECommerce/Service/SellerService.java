package com.ApnaMart.ECommerce.Service;

import com.ApnaMart.ECommerce.Converter.SellerConverter;
import com.ApnaMart.ECommerce.Model.Seller;
import com.ApnaMart.ECommerce.Repository.SellerRepository;
import com.ApnaMart.ECommerce.RequestDTO.SellerRequestDTO;
import com.ApnaMart.ECommerce.ResponseDTO.SellerResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public SellerResponseDto getSellerByPanNo(String panNo)
    {
        Seller seller = sellerRepository.getSellerByPanNo(panNo);

        SellerResponseDto sellerResponseDto = SellerResponseDto.builder()
                .mobNo(seller.getMobNo())
                .name(seller.getName())
                .email(seller.getEmail())
                .panNo(seller.getPanNo())
                .age(seller.getAge())
                .build();

        return sellerResponseDto;
    }

    public ResponseEntity<?> getSellersByAge(int age)
    {
        List<Seller> sellers = sellerRepository.getSellersByAge(age);

        List<SellerResponseDto> sellerResponseDtos = new ArrayList<>();

        for(int i=0;i<sellers.size();i++)
        {
            Seller seller1 = sellers.get(i);

            SellerResponseDto sellerResponseDto1 = SellerResponseDto.builder()
                    .age(seller1.getAge())
                    .panNo(seller1.getPanNo())
                    .email(seller1.getEmail())
                    .name(seller1.getName())
                    .mobNo(seller1.getMobNo())
                    .build();

            sellerResponseDtos.add(sellerResponseDto1);
        }
        if(sellerResponseDtos.isEmpty())
        {
            return new ResponseEntity<>("No Seller Exist for this Age", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sellerResponseDtos,HttpStatus.ACCEPTED);
    }

    public String deleteSellerById(int id)
    {
        sellerRepository.deleteById(id);
        return "Seller Deleted Successfully !!";
    }
}

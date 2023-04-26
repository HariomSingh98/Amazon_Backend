package com.Ecommerce.AmazonBackened.Service;

import com.Ecommerce.AmazonBackened.Model.Seller;
import com.Ecommerce.AmazonBackened.Repository.SellerRepository;
import com.Ecommerce.AmazonBackened.RequestDTO.SellerRequestDTO;
import com.Ecommerce.AmazonBackened.ResponseDTO.SellerResponseDTO;
import com.Ecommerce.AmazonBackened.converter.SellerConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    public SellerResponseDTO addSeller(SellerRequestDTO sellerRequestDTO){
         //convert from dto to seller object
        Seller newSeller = SellerConvertor.dtoToSeller(sellerRequestDTO);

        sellerRepository.save(newSeller);

        //convert from seller to dto
        SellerResponseDTO sellerResponseDTO = SellerConvertor.sellerToDto(newSeller);

        return sellerResponseDTO;

    }
    public List<SellerResponseDTO> findAllSellers(){
        List<Seller> sellerList = sellerRepository.findAll();

        List<SellerResponseDTO> sellerResponseDTOList = new ArrayList<>();
        for(Seller s : sellerList){
            SellerResponseDTO sellerResponseDTO = SellerConvertor.sellerToDto(s);
            sellerResponseDTOList.add(sellerResponseDTO);
        }

        return sellerResponseDTOList;
    }

    public SellerResponseDTO sellerByPan(String panNo){

        Seller desiredSeller = sellerRepository.findByPanNo(panNo);

        return SellerConvertor.sellerToDto(desiredSeller);//return SellerResponse after converting

    }
    public List<SellerResponseDTO> sellersByAge(int age){
        List<Seller> sellerList = sellerRepository.findByAge(age);

        List<SellerResponseDTO> sellerResponseDTOList = new ArrayList<>();
        for(Seller s : sellerList){
            SellerResponseDTO sellerResponseDTO = SellerConvertor.sellerToDto(s);
            sellerResponseDTOList.add(sellerResponseDTO);
        }

        return sellerResponseDTOList;

    }
}















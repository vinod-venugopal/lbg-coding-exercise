package com.lbg.data.atm.controller;

import com.lbg.data.atm.service.AtmService;
import lombok.extern.slf4j.Slf4j;
import model.ATM;
import model.ATMResponse;
import model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.*;


@RestController
@Slf4j
public class AtmController {


    @Autowired
    private AtmService atmService;

    @GetMapping(value = "/atms", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ATMResponse> getAtms(){
        ATMResponse atmResponse =  atmService.retrieveAllAtms();
        return ResponseEntity.ok().body(atmResponse);

    }

    @GetMapping(value = "/atms/{identificationValue}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ATM> getAtmDetailByIdentificationValue(@PathVariable String identificationValue){
        ATMResponse atmResponse =  atmService.retrieveAllAtms();
        Optional<ATM> atmDetails = null;
        if(atmResponse !=null ) {
            if(atmResponse.getData() !=null && !atmResponse.getData().isEmpty()) {
                List<Brand> brands = atmResponse.getData().get(0).getBrands();
                if(brands !=null && !brands.isEmpty()){
                    List<ATM> atms = brands.get(0).getATMS();
                     atmDetails = atms.stream()
                            .filter(i -> i.getIdentification().equalsIgnoreCase(identificationValue))
                            .findAny();
                    atmDetails.ifPresent( i->   log.info("ATM Details {} ",i));
                }
            }
        }

        return atmDetails.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }
}

package com.lbg.data.atm.service;


import model.ATMResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AtmService {


    private final String atmUri = "https://api.lloydsbank.com/open-banking/v2.2/atms";

    @Autowired
    private WebClient webClient;

    public ATMResponse retrieveAllAtms(){
        return webClient
                .get()
                .uri(atmUri)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ATMResponse.class)
                .block();
    }


}

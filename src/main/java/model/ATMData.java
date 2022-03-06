package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@lombok.Data
public class ATMData {

    @JsonProperty("Brand")
    private List<Brand> brands;

    public ATMData() {
    }
}

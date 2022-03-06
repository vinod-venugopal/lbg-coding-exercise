package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Brand {

    @JsonProperty("BrandName")
    private String brandName;

    @JsonProperty("ATM")
    private List<ATM> ATMS;

    public Brand() {
    }
}

package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {
    private int id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private boolean block;


    private int regionId;
    private int companyId;
}

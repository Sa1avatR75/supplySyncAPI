package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {
    private String id;
    private boolean blocked;
    private String logo;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private int status;


//    Abduvohid

private String region;



}

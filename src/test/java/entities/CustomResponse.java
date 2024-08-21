package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {

//    private int id;

    private String id;
    private boolean blocked;
    private String logo;

    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private boolean block;
    private int regionId;
    private int companyId;
    private int status;

    private Region region;
//    private int region;




}

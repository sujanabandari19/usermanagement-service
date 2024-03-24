package com.usermanagement.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String addressType;

}

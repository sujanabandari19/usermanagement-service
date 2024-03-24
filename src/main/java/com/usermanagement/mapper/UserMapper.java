package com.usermanagement.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.usermanagement.dto.AddressDTO;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.Address;
import com.usermanagement.entity.User;

@Component
public class UserMapper {

    public UserDTO toUserDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setStatus(user.getStatus());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        //dto.setDateOfBirth(user.getDateOfBirth());
        dto.setAddresses(toAddressDTOs(user.getAddresses()));
        return dto;
    }

    public List<UserDTO> toUserDtos(List<User> users) {
        return users.stream().map(this::toUserDto).collect(Collectors.toList());
    }

    private List<AddressDTO> toAddressDTOs(List<Address> addresses) {
        return addresses.stream()
                .map(this::toAddressDto)
                .collect(Collectors.toList());
    }

    private AddressDTO toAddressDto(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setStreetAddress("********");
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        dto.setAddressType(address.getAddressType());
        return dto;
    }
    
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setStatus(dto.getStatus());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setAddresses(toAddresses(dto.getAddresses(), user));
        return user;
    }

    private List<Address> toAddresses(List<AddressDTO> addressDTOs, User user) {
        List<Address> addresses = new ArrayList<>();
        if (addressDTOs != null) {
            for (AddressDTO addressDTO : addressDTOs) {
                Address address = new Address();
                address.setUser(user);
                address.setId(addressDTO.getId());
                address.setStreetAddress(addressDTO.getStreetAddress());
                address.setCity(addressDTO.getCity());
                address.setState(addressDTO.getState());
                address.setPostalCode(addressDTO.getPostalCode());
                address.setCountry(addressDTO.getCountry());
                address.setAddressType(addressDTO.getAddressType());
                addresses.add(address);
            }
        }
        return addresses;
    }
    
    public User updateEntity(User user, UserDTO dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setStatus(dto.getStatus());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        user.setAddresses(toAddresses(dto.getAddresses(), user));
        return user;
    }
    
}

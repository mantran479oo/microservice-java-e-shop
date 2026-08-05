package org.example.userservice.mapper;

import org.example.commonsservice.mapper.BaseMapper;
import org.example.userservice.dto.request.RegistrationRequestDTO;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends BaseMapper<Customer, UserResponse,RegistrationRequestDTO> {
}

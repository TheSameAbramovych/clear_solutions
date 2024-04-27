package com.bohdan.abramovych.clear_solutions.core.service.mapper;


import com.bohdan.abramovych.clear_solutions.core.service.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "userId", source = "userId")
    Address toAddress(Address address, String userId);
}

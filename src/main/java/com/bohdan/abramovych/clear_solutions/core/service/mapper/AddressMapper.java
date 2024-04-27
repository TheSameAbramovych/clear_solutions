package com.clear.solutions.task.core.service.mapper;

import com.clear.solutions.task.core.service.model.Address;
import com.clear.solutions.task.core.service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressMapper {

    Address toAddress(Address address, String userId);
}

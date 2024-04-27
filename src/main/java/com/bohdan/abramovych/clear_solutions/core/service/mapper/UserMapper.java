package com.clear.solutions.task.core.service.mapper;

import com.clear.solutions.task.core.repository.model.AddressRecord;
import com.clear.solutions.task.core.repository.model.UserRecord;
import com.clear.solutions.task.core.service.model.Address;
import com.clear.solutions.task.core.service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

    @Mapping(target = "address", source = "addressRecord")
    @Mapping(target = "id", source = "userRecord.id")
    User toUser(UserRecord userRecord, AddressRecord addressRecord);
}

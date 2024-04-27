package com.bohdan.abramovych.clear_solutions.core.service.mapper;

import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.AddressRecord;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.UsersRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "address", source = "addressRecord")
    @Mapping(target = "id", source = "usersRecord.id")
    User toUser(UsersRecord usersRecord, AddressRecord addressRecord);
}

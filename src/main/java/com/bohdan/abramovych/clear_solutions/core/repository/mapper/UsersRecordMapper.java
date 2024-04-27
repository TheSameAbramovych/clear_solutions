package com.bohdan.abramovych.clear_solutions.core.repository.mapper;

import com.bohdan.abramovych.clear_solutions.core.repository.model.UserRecord;
import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.UsersRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface UsersRecordMapper {


    @Mapping(target = "id", source = "id", defaultExpression = "java(java.util.UUID.randomUUID().toString())")
    UsersRecord toUserRecord(User user);

    @Mapping(target = "id", source = "id", defaultExpression = "java(java.util.UUID.randomUUID().toString())")
    UsersRecord toUsersRecord(User user);
}

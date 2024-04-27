package com.clear.solutions.task.core.repository.mapper;

import com.clear.solutions.task.core.repository.model.AddressRecord;
import com.clear.solutions.task.core.repository.model.UserRecord;
import com.clear.solutions.task.core.service.model.Address;
import com.clear.solutions.task.core.service.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface AddressRecordMapper {

    AddressRecord toAddressRecord(Address address);
}

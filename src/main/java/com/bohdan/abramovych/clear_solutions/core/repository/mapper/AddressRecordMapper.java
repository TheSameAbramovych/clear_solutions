package com.bohdan.abramovych.clear_solutions.core.repository.mapper;


import com.bohdan.abramovych.clear_solutions.core.service.model.Address;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.AddressRecord;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface AddressRecordMapper {

    AddressRecord toAddressRecord(Address address);
}

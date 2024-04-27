package com.bohdan.abramovych.clear_solutions.infra.repository;

import com.bohdan.abramovych.clear_solutions.core.repository.AddressRepository;
import com.bohdan.abramovych.clear_solutions.core.repository.mapper.AddressRecordMapper;
import com.bohdan.abramovych.clear_solutions.core.service.model.Address;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.AddressRecord;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressInMemoryRepository implements AddressRepository {

    private final Map<String, AddressRecord> addresses = new ConcurrentHashMap<>();

    AddressRecordMapper addressRecordMapper;

    public AddressRecord upsert(Address address) {
        log.debug("Upsetting address {}...", address);

        AddressRecord addressRecord = addressRecordMapper.toAddressRecord(address);
        AddressRecord insertedAddressRecord = addresses.put(address.getId(), addressRecord);

        log.debug("Address {} was inserted by id {}.", address, address.getId());
        return insertedAddressRecord;
    }

    public Optional<AddressRecord> getById(String id) {
        log.debug("Getting address by id {}...", id);

        Optional<AddressRecord> addressRecord = Optional.ofNullable(addresses.get(id));

        log.debug("Address {} was found by id {}.", addressRecord, id);
        return addressRecord;
    }

    public Optional<AddressRecord> remove(String id) {
        log.debug("Removing address by id {}...", id);

        Optional<AddressRecord> addressRecord = Optional.ofNullable(addresses.remove(id));

        log.debug("Address {} was removed by id {}.", addressRecord, id);
        return addressRecord;
    }
}

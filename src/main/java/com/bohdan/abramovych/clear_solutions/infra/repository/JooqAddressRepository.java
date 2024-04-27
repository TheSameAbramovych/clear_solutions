package com.bohdan.abramovych.clear_solutions.infra.repository;

import com.bohdan.abramovych.clear_solutions.core.repository.AddressRepository;
import com.bohdan.abramovych.clear_solutions.core.repository.mapper.AddressRecordMapper;
import com.bohdan.abramovych.clear_solutions.core.service.model.Address;
import com.bohdan.abramovych.clear_solutions.persistence.Tables;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.AddressRecord;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.bohdan.abramovych.clear_solutions.persistence.tables.Address.ADDRESS;
import static com.bohdan.abramovych.clear_solutions.persistence.tables.Users.USERS;

@Slf4j
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JooqAddressRepository implements AddressRepository {

    DSLContext context;
    AddressRecordMapper addressRecordMapper;

    public AddressRecord upsert(Address address) {
        log.debug("Upsetting address {}...", address);

        AddressRecord addressRecord = addressRecordMapper.toAddressRecord(address);
        AddressRecord insertedAddressRecord = context.insertInto(ADDRESS)
            .set(addressRecord)
            .returning(ADDRESS)
            .fetchOne();

        log.debug("Address {} was inserted by id {}.", address, address.getId());
        return insertedAddressRecord;
    }

    public Optional<AddressRecord> getByUserId(String id) {
        log.debug("Getting address by id {}...", id);

        Optional<AddressRecord> addressRecord = context.select(ADDRESS.fields())
            .from(ADDRESS)
            .join(USERS)
            .on(USERS.ID.eq(ADDRESS.USER_ID))
            .where(USERS.ID.eq(id))
            .fetchOptionalInto(ADDRESS);

        log.debug("Address {} was found by id {}.", addressRecord, id);
        return addressRecord;
    }

    public Optional<AddressRecord> remove(String id) {
        log.debug("Removing address by id {}...", id);

        Optional<AddressRecord> addressRecord = context.selectFrom(Tables.ADDRESS)
            .where(Tables.ADDRESS.ID.eq(id))
            .fetchOptional();

        addressRecord.ifPresent(ignore ->
            context.deleteFrom(ADDRESS)
                .where(ADDRESS.ID.eq(id))
                .execute());

        log.debug("Address {} was removed by id {}.", addressRecord, id);
        return addressRecord;
    }
}

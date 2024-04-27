package com.bohdan.abramovych.clear_solutions.infra.service.infra.repository;

import com.bohdan.abramovych.clear_solutions.core.repository.mapper.AddressRecordMapperImpl;
import com.bohdan.abramovych.clear_solutions.core.repository.mapper.UsersRecordMapperImpl;
import com.bohdan.abramovych.clear_solutions.core.service.model.Address;
import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.infra.repository.JooqAddressRepository;
import com.bohdan.abramovych.clear_solutions.infra.repository.JooqUserRepository;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.AddressRecord;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.AssertionsForClassTypes;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static com.bohdan.abramovych.clear_solutions.persistence.Tables.ADDRESS;
import static com.bohdan.abramovych.clear_solutions.infra.service.TestDataProvider.createUser;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JooqTest
@ContextConfiguration(classes = {
    JooqAddressRepository.class,
    JooqUserRepository.class,
    UsersRecordMapperImpl.class,
    AddressRecordMapperImpl.class
})
class JooqAddressRepositoryTest {

    @Autowired
    DSLContext context;
    @Autowired
    JooqAddressRepository addressRepository;
    @Autowired
    JooqUserRepository userRepository;

    @Test
    public void upsert() {
        User user = createUser(false);
        Address address = user.getAddress();

        userRepository.upsert(user);
        addressRepository.upsert(address);

        AddressRecord addressRecord = context.selectFrom(ADDRESS)
            .where(ADDRESS.ID.eq(address.getId()))
            .fetchAny();

        assertUser(assertThat(addressRecord), address);
    }

    @Test
    public void getByUserId() {
        User user = createUser(false);
        Address address = user.getAddress();

        userRepository.upsert(user);
        addressRepository.upsert(address);
        Optional<AddressRecord> byUserId = addressRepository.getByUserId(address.getUserId());

        assertUser(AssertionsForClassTypes.assertThat(byUserId).isPresent().get(), address);
    }

    @Test
    public void remove() {
        User user = createUser(false);
        Address address = user.getAddress();

        userRepository.upsert(user);
        addressRepository.upsert(address);
        Optional<AddressRecord> byUserId = addressRepository.remove(address.getId());

        Long count = context.selectCount()
            .from(ADDRESS)
            .where(ADDRESS.ID.eq(address.getId()))
            .fetchOneInto(Long.class);
        assertEquals(0L, count);
        assertUser(AssertionsForClassTypes.assertThat(byUserId).isPresent().get(), address);
    }

    private static void assertUser(AbstractObjectAssert<?, AddressRecord> objectAssert, Address address) {
        objectAssert
            .returns(address.getId(), AddressRecord::getId)
            .returns(address.getUserId(), AddressRecord::getUserId)
            .returns(address.getLine1(), AddressRecord::getLine1)
            .returns(address.getLine2(), AddressRecord::getLine2)
            .returns(address.getPostCode(), AddressRecord::getPostCode);
    }
}

package com.bohdan.abramovych.clear_solutions.infra.service;


import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.infra.controller.model.UserView;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.AddressRecord;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.UsersRecord;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.time.LocalDate;
import java.util.UUID;

public class TestDataProvider {

    public static final EasyRandom EASY_RANDOM = new EasyRandom(new EasyRandomParameters()
        .stringLengthRange(5, 10)
        .collectionSizeRange(1, 5)
        .randomize(field -> "id".equals(field.getName()), TestDataProvider::generateRandomId)
        .randomizationDepth(3)
        .dateRange(LocalDate.now().minusMonths(11), LocalDate.now())
        .ignoreRandomizationErrors(true)
        .scanClasspathForConcreteTypes(true));

    public static User createUser(boolean isAdult) {
        User user = EASY_RANDOM.nextObject(User.class);
        return user
            .toBuilder()
            .birthday(isAdult ? LocalDate.now().minusYears(18) : LocalDate.now())
            .address(user.getAddress().toBuilder()
                .userId(user.getId())
                .build())
            .build();
    }

    public static UserView createUserView() {
        return EASY_RANDOM.nextObject(UserView.class);
    }

    public static UsersRecord createUserRecord() {
        return EASY_RANDOM.nextObject(UsersRecord.class);
    }

    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    public static AddressRecord createAddressRecord() {
        return EASY_RANDOM.nextObject(AddressRecord.class);
    }
}

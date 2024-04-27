package com.bohdan.abramovych.clear_solutions.core.service.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class User {

    String id;
    String email;
    String firstName;
    String secondName;
    String phoneNumber;
    LocalDate birthday;
    Address address;
}

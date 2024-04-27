package com.clear.solutions.task.core.service.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class User {

    String id;
    String email;
    String firstName;
    String secondName;
    String phoneNumber;
    LocalDate birthday;
    Address address;
}

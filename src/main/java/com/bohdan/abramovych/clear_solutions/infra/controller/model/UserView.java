package com.clear.solutions.task.infra.controller.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Value
@Builder
public class UserView {

    @NotEmpty
    String id;

    @NotEmpty
    String email;

    @NotEmpty
    String firstName;

    @NotEmpty
    String secondName;

    @NotEmpty
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")
    String phoneNumber;

    @NotEmpty
    @Past
    LocalDate birthday;

    AddressView address;
}

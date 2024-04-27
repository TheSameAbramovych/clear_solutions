package com.bohdan.abramovych.clear_solutions.infra.controller.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
@Builder
public class AddressView {

    String line1;
    String line2;
    String line3;
    @NotEmpty
    String postCode;
}

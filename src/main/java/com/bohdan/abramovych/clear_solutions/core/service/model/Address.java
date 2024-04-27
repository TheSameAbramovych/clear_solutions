package com.bohdan.abramovych.clear_solutions.core.service.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Address {

    String id;
    String line1;
    String line2;
    String line3;
    String postCode;
    String userId;
}

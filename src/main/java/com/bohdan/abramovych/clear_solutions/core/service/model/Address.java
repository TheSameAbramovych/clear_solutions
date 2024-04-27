package com.clear.solutions.task.core.service.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Address {

    String id;
    String line1;
    String line2;
    String line3;
    String postCode;
    String userId;
}

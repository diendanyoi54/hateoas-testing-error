package com.example.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCustomerDto {

    public final String firstName;
    public final String lastName;

    @JsonCreator CreateCustomerDto(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
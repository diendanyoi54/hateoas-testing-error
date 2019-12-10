package com.example.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.Random;

@AllArgsConstructor public class Customer {

    public final int id;
    public final String firstName;
    public final String lastName;
}
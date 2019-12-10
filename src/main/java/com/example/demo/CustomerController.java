package com.example.demo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Random;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

//@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Controller public class CustomerController {
	
	@GetMapping("/user/{id}") ResponseEntity<Resource<Customer>> readCustomer(@PathVariable int id) {
		Link updateLink = linkTo(CustomerController.class).slash("/user").withRel("update");
		Resource<Customer> resource = new Resource<>(new Customer(id, "test", "test"));
		resource.add(updateLink);

		return ResponseEntity.ok(resource);
	}

	@PostMapping ResponseEntity<Resource<Customer>> createCustomer(@RequestBody CreateCustomerDto input) {
		int id = new Random().nextInt();
		Customer customer = new Customer(id, input.firstName, input.lastName);

		Link updateLink = linkTo(CustomerController.class).slash("/user/" + id).withSelfRel();
		Resource<Customer> resource = new Resource<>(customer);
		resource.add(updateLink);

		return new ResponseEntity(resource, HttpStatus.CREATED);
	}
}
package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static capital.scalable.restdocs.AutoDocumentation.authorization;
import static capital.scalable.restdocs.AutoDocumentation.description;
import static capital.scalable.restdocs.AutoDocumentation.methodAndPath;
import static capital.scalable.restdocs.AutoDocumentation.pathParameters;
import static capital.scalable.restdocs.AutoDocumentation.requestFields;
import static capital.scalable.restdocs.AutoDocumentation.requestParameters;
import static capital.scalable.restdocs.AutoDocumentation.responseFields;
import static capital.scalable.restdocs.AutoDocumentation.section;
import static capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson;
import static org.springframework.restdocs.cli.CliDocumentation.curlRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends MockMvcBase {

    private static final String DEFAULT_AUTHORIZATION = "Resource is public.";

    private final CustomerController customerController = new CustomerController();

    private MockMvc mockMvc;

    @Before public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .alwaysDo(prepareJackson(objectMapper))
                .alwaysDo(commonDocumentation())
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080)
                        .and().snippets()
                        .withDefaults(curlRequest(), httpRequest(), httpResponse(), requestFields(), responseFields(), pathParameters(),
                                requestParameters(), description(), methodAndPath(), section(), authorization(DEFAULT_AUTHORIZATION)))
                .build();
    }

    @Test public void retrieve() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/111"))
//                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test public void createLegacy() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new CreateCustomerDto("myFirst", "myLast"))))
//                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isCreated());
    }
}
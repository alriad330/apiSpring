package com.example.products.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTestInteg {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetProducts() throws Exception{
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name", is("TCL")));
    }

    @Test
    public void testGetProduct() throws Exception{
        mockMvc.perform(get("/product/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("inventoryStatus", is("LOWSTOCK")));
    }
}

package com.example.products.dto;

import com.example.products.models.Product;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProductDtoTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertProductEntityToProductDto_thenCorrect() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("toto1");
        product.setName("toto");

        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        assertEquals(product.getId(), productDto.getId());
        assertEquals(product.getCode(), productDto.getCode());
        assertEquals(product.getName(), productDto.getName());
    }

    @Test
    public void whenConvertPostDtoToPostEntity_thenCorrect() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setCode("toto1");
        productDto.setName("toto");

        Product product = modelMapper.map(productDto, Product.class);
        assertEquals(productDto.getId(), product.getId());
        assertEquals(productDto.getCode(), product.getCode());
        assertEquals(productDto.getName(), product.getName());
    }
}

package com.example.products.utils.mapper;

import com.example.products.dto.ProductDto;
import com.example.products.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ProductMapper {

    Iterable<ProductDto> mapProductsList(Iterable<Product> productList);

    ProductDto toProductDto(Product product);

    Product toProduct(ProductDto productDto);

}

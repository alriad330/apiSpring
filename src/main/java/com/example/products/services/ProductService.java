package com.example.products.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.products.dto.ProductDto;
import com.example.products.exceptionHandler.ProductNotFoundException;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.products.models.Product;
import com.example.products.repositories.ProductRepository;

@Service
@NoArgsConstructor
public class ProductService {
    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<ProductDto> getProduct(final Long id){
        try {
            Optional<Product> product = productRepository.findById(id);
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            return Optional.ofNullable(productDto);
        } catch (ProductNotFoundException p) {
            logger.error("Product with id : " + id + " not found.");
            return null;
        }
    }

    public Iterable<ProductDto> getProducts(){
        List<Product> products = productRepository.findAll();
        Iterable<ProductDto> productDtos = products.stream().map(
                product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

        return productDtos;
    }

    public void deleteProduct(final Long id){
        productRepository.deleteById(id);
    }

    public ProductDto saveProduct(ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
        ProductDto savedProductDto = modelMapper.map(productRepository.save(product), ProductDto.class);
        return savedProductDto;
    }
}

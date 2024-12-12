package com.example.products.controller;

import java.util.Optional;

import com.example.products.exceptionHandler.ErrorResponse;
import com.example.products.exceptionHandler.ProductNotFoundException;
import com.example.products.dto.ProductDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.products.models.Product;
import com.example.products.services.ProductService;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Create a new product
     * @param productDto
     * @return productSaved
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping("/product")
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto){
        ProductDto productSaved = productService.saveProduct(productDto);
        return ResponseEntity.ok(productSaved);
    }

    /**
     * Retrieve All Products
     * @return List
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "415", description = "Unsupported Media Type"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value = "/products", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Iterable<ProductDto>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    /**
     * Retrieve details for product 1
     * @param id
     * @return product
     * @throws ProductNotFoundException
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value = "/product/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") final Long id){
        Optional<ProductDto> product = productService.getProduct(id);
        if(product.isPresent()){
            return ResponseEntity.ok(product.get());
        } else {
            throw new ProductNotFoundException("Product with id : " + id + " not found.");
        }
    }

    /**
     * Remove product 1
     * @param id
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable("id") final Long id){
        productService.deleteProduct(id);
    }

    /**
     * Update details of product 1 if it exists
     * @param id
     * @param newProduct
     * @return productUpdated
     * @throws ProductNotFoundException
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PatchMapping("/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") final Long id,
                                                 @RequestBody ProductDto newProduct) throws ProductNotFoundException {

        ProductDto productDto = productService.getProduct(id).get();
       
        if (productDto != null) {

            String code = newProduct.getCode();
            if(code != null){
                productDto.setCode(code);
            }
            String name = newProduct.getName();
			if(name != null) {
                productDto.setName(name);
			}
            ProductDto productPatched = productService.saveProduct(productDto);
            return ResponseEntity.ok(productPatched);
        } else {
            throw new ProductNotFoundException("Product with id : " + id + " not found.");
        }
    }

}

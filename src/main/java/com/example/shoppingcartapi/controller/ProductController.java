package com.example.shoppingcartapi.controller;

import com.example.shoppingcartapi.dto.ProductDto;
import com.example.shoppingcartapi.dto.request.AddProductRequest;
import com.example.shoppingcartapi.dto.request.ProductUpdateRequest;
import com.example.shoppingcartapi.dto.response.ApiResponse;
import com.example.shoppingcartapi.exception.ResourceNotFoundException;
import com.example.shoppingcartapi.model.Product;
import com.example.shoppingcartapi.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("success", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add product success", theProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(
            @RequestBody ProductUpdateRequest request,
            @PathVariable Long productId
    ) {
        try {
            Product product = productService.updateProduct(request, productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Update success", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long productId
    ) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete success", productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(
            @RequestParam String brand,
            @RequestParam String productName
    ) {
        try {
            List<Product> products = productService.getProductByBrandAndName(brand, productName);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products found!", null));
            }

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(
            @RequestParam  String category,
            @RequestParam  String brand
    ) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products found!", null));
            }

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by-name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(
            @PathVariable  String name
    ) {
        try {
            List<Product> products = productService.getProductByName(name);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products found!", null));
            }

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(
            @RequestParam  String brand
    ) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products found!", null));
            }

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by-category/{category}")
    public ResponseEntity<ApiResponse> getProductsByCategory(
            @PathVariable  String category
    ) {
        try {
            List<Product> products = productService.getProductsByCategory(category);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products found!", null));
            }

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/count/by/brand-and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(
            @RequestParam String brand,
            @RequestParam String name
    ) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product Count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}

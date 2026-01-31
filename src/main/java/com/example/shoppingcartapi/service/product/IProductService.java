package com.example.shoppingcartapi.service.product;

import com.example.shoppingcartapi.dto.ProductDto;
import com.example.shoppingcartapi.dto.request.AddProductRequest;
import com.example.shoppingcartapi.dto.request.ProductUpdateRequest;
import com.example.shoppingcartapi.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    void deleteProductById(Long id);

    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}

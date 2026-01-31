package com.example.shoppingcartapi.service.product;

import com.example.shoppingcartapi.dto.ProductDto;
import com.example.shoppingcartapi.dto.request.AddProductRequest;
import com.example.shoppingcartapi.dto.request.ProductUpdateRequest;
import com.example.shoppingcartapi.exception.ResourceNotFoundException;
import com.example.shoppingcartapi.model.Category;
import com.example.shoppingcartapi.model.Image;
import com.example.shoppingcartapi.model.Product;
import com.example.shoppingcartapi.repository.CategoryRepository;
import com.example.shoppingcartapi.repository.ImageRepository;
import com.example.shoppingcartapi.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in the DB
        // if Yes, set it as the new product of category
        // if No, the save as the new category
        // the set as the new product category

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

        request.setCategory(category);
        return  productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }

    @Transactional
    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

//        Category category = categoryRepository.findByName(request.getCategory().getName());
//        existingProduct.setName(category.getName());
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Transactional
    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                        .ifPresentOrElse(
                                productRepository::delete,
                                ()-> {throw new ResourceNotFoundException("Product not found");}
                        );
    }


    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }


    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }


    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }


    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }


    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }


    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }


    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }


    @Override
    public ProductDto convertToDto(Product product) {
        // ModelMapper will automatically map the list of images
        // inside 'product' to the list of ImageDtos inside 'productDto'
        return modelMapper.map(product, ProductDto.class);
    }
}

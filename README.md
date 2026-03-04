# Shopping Cart API

A Spring Boot learning project I built as a student to practice building a simple eCommerce backend. It covers authentication, products, categories, carts, orders, and image uploads with a clean layered structure.

## What I Practiced

- Spring Security with JWT (stateless auth)
- JPA/Hibernate relationships and repositories
- DTOs and request/response models
- Validation and global exception handling
- Building REST APIs with controllers and services

## Tech Stack

- Spring Boot 4.0.1
- Java 25
- MySQL
- Spring Security + JJWT
- Lombok, ModelMapper, Jakarta Validation

## Project Structure

- `controller/` REST endpoints
- `service/` business logic
- `repository/` data access
- `model/` entities
- `dto/` API contracts
- `security/` JWT and security config
- `exception/` global error handling

## Base URL

All endpoints start with `/api/v1`.

## Endpoints

Products (`/products`)
- `GET /products/all`
- `GET /products/product/{productId}`
- `POST /products/add`
- `PUT /products/product/{productId}/update`
- `DELETE /products/product/{productId}/delete`
- `GET /products/product/by/brand-and-name`
- `GET /products/product/by/category-and-brand`
- `GET /products/product/by-name/{name}`
- `GET /products/product/by-brand`
- `GET /products/product/by-category/{category}`
- `GET /products/product/count/by/brand-and-name`

Categories (`/categories`)
- `GET /categories/all`
- `POST /categories/add`
- `GET /categories/category/{id}/category`
- `GET /categories/category/{name}/category`
- `DELETE /categories/category/{id}/delete`
- `PUT /categories/category/{id}/update`

Images (`/images`)
- `POST /images/upload`
- `GET /images/image/download/{imageId}`
- `PUT /images/image/{imageId}/update`
- `DELETE /images/image/{imageId}/delete`

Carts (`/carts`)
- `GET /carts/{cartId}/my-cart`
- `DELETE /carts/{cartId}/clear`
- `GET /carts/{cartId}/cart/total-price`

Cart Items (`/cartItems`)
- `POST /cartItems/item/add`
- `DELETE /cartItems/cart/{cartId}/item/{itemId}/remove`
- `PUT /cartItems/cart/{cartId}/item/{itemId}/update`

Orders (`/orders`)
- `POST /orders/order`
- `GET /orders/{orderId}/order`
- `GET /orders/{userId}/order`

Users (`/users`)
- `GET /users/{userId}/user`
- `POST /users/create`
- `PUT /users/{userId}/update`
- `DELETE /users/{userId}/delete`

Auth (`/auths`)
- `POST /auths/register`
- `POST /auths/login`

## Getting Started

Prerequisites
- JDK 25
- MySQL

Configure database
- Edit `src/main/resources/application.properties` with your MySQL URL, username, and password.

Run the app
```bash
./mvnw spring-boot:run
```

## Notes

This is a student learning project. I plan to add unit tests and Swagger/OpenAPI docs next.

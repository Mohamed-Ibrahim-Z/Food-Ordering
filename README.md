# Food Ordering System

## Overview
This is a Spring Boot-based Food Ordering System that allows users to browse restaurants, order food, and manage restaurants (for admin users). The system includes features such as user authentication, restaurant management, food item management, order processing, and more.

## Key Features
- User authentication and authorization (JWT-based)
- Restaurant creation, management, and search
- Food item creation, management, and search
- Order placement and management
- Shopping cart functionality
- Category management for food items
- Ingredient management for restaurants

# API Documentation Overview

## Authentication
- Sign Up: `POST /auth/signup` - Register a new user
- Sign In: `POST /auth/signin` - Authenticate a user and receive a JWT token
- Get User Profile: `GET /api/users/profile` - Retrieve the current user's profile information

## Restaurant Management
- Get Restaurant by ID: `GET /api/restaurants/{id}` - Retrieve details of a specific restaurant
- Get All Restaurants: `GET /api/restaurants` - Retrieve a list of all restaurants
- Search Restaurants: `GET /api/restaurants/search` - Search for restaurants by keyword
- Add Restaurant to Favorites: `PUT /api/restaurants/{id}/add-favorites` - Add a restaurant to user's favorites
- Delete Restaurant (Admin): `DELETE /api/admin/restaurants/{id}` - Delete a restaurant
- Create Restaurant (Admin): `POST /api/admin/restaurants` - Create a new restaurant
- Update Restaurant Status (Admin): `PUT /api/admin/restaurants/{id}/status` - Update a restaurant's status
- Get User's Restaurants (Admin): `GET /api/admin/restaurants/user` - Get restaurants owned by the current user

## Food Category Management
- Create Category (Admin): `POST /api/admin/category` - Create a new food category
- Get All Restaurant Categories: `GET /api/category/restaurant/{id}` - Get all categories for a specific restaurant

## Ingredient Management
- Create Ingredient Category (Admin: `POST /api/admin/ingredients/category` - Create a new ingredient category

## Food Management
- Search Food: `GET /api/food/search` - Search for food items
- Get Restaurant Food: `GET /api/food/restaurant/{restaurantId}` - Get food items for a specific restaurant
- Create Food (Admin): `POST /api/admin/food` - Create a new food item
- Delete Food (Admin): `DELETE /api/admin/food/{id}` - Delete a food item
- Update Food Availability (Admin): `PUT /api/admin/food/{id}` - Update food item availability

## Cart Management
- Add Item to Cart: `PUT /api/cart/add` - Add an item to the user's cart
- Update Cart Item Quantity: `PUT /api/cart-item/update` - Update the quantity of an item in the cart
- Remove Item from Cart: `DELETE /api/cart-item/{id}/remove` - Remove an item from the cart
- Clear Cart: `PUT /api/cart/clear` - Clear all items from the user's cart
- Get User Cart: `GET /api/cart` - Retrieve the current user's cart

## Order Management
- Create Order: `POST /api/order` - Place a new order
- Get Order History: `GET /api/order/user` - Retrieve order history for the current user
- Get Restaurant Orders (Admin): `GET /api/admin/order/restaurant/{id}` - Get orders for a specific restaurant
- Update Order Status (Admin): `PUT /api/admin/order/{id}/{orderStatus}` - Update the status of an order

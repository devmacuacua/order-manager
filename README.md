# Order Manager API

A simple **Order Manager API** that allows users to create and manage
orders. Orders are automatically fulfilled when stock becomes available.

This project was developed as a technical exercise using **Java EE**,
**Jersey**, **Hibernate**, **PostgreSQL**, and **Docker**.

------------------------------------------------------------------------

# Tech Stack

-   Java 8
-   Java EE (JAX-RS)
-   Jersey
-   Hibernate / JPA
-   PostgreSQL
-   Log4j
-   Docker
-   Docker Compose
-   Apache Tomcat

------------------------------------------------------------------------

# Architecture

The project follows a layered architecture:

    API (Controllers)
    ↓
    Service (Business Logic)
    ↓
    Repository (Persistence)
    ↓
    Entity (Database Models)

Additional layers:

    DTO
    Mapper
    Infrastructure

------------------------------------------------------------------------

# Quick Start

Clone the repository:

``` bash
git clone git@github.com:devmacuacua/order-manager.git
```

Enter the project directory:

``` bash
cd order-manager
```

Start the system with Docker:

``` bash
docker compose up --build
```

------------------------------------------------------------------------

# API Base URL

    http://localhost:8080/order-manager/api

Example:

``` bash
curl http://localhost:8080/order-manager/api/users
```

------------------------------------------------------------------------

# Entities

## User

    id
    name
    email

## Item

    id
    name

## StockMovement

    id
    creationDate
    item
    quantity

## Order

    id
    creationDate
    item
    quantity
    user
    fulfilledQuantity

## OrderStockMovement

    orderId
    stockMovementId
    quantityUsed

------------------------------------------------------------------------

# Key Features

-   CRUD operations for all entities
-   Automatic order fulfillment
-   Stock allocation tracking
-   Email notifications when orders are completed
-   Logging with Log4j
-   Docker environment for Tomcat and PostgreSQL

------------------------------------------------------------------------

# Example Endpoints

## Users

Create user

    POST /users

Example body:

``` json
{
  "name": "Francisco",
  "email": "francisco@email.com"
}
```

List users

    GET /users

------------------------------------------------------------------------

# Orders

Create order

    POST /orders

Example body:

``` json
{
  "userId": 1,
  "itemId": 1,
  "quantity": 20
}
```

------------------------------------------------------------------------

# Tracking

Get order completion

    GET /orders/{id}/completion

Get allocations used by an order

    GET /orders/{id}/allocations

Get orders affected by a stock movement

    GET /stock-movements/{id}/allocations

------------------------------------------------------------------------

# Database

PostgreSQL runs inside Docker.

    Database: order_manager
    Port: 5433

------------------------------------------------------------------------

# Stop the system

``` bash
docker compose down
```

------------------------------------------------------------------------

# Author

Francisco Macuácua\
Software Engineer

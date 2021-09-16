package com.productmanager.data;

import java.math.BigDecimal;

import static com.productmanager.data.Rating.*;
import static java.math.RoundingMode.HALF_UP;

/**
 * {@code Product} class represents the properties and behaviours of
 * a product object in a Product Management System.
 * <br>
 * Discount and rating is applicable to each product.
 *
 */


public class Product {

    private int id;
    private String name;
    private BigDecimal price;
    private final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.1);
    private Rating rating;

    public Product(int id, String name, BigDecimal price, Rating rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public Product(int id, String name, BigDecimal price) {
        this(id, name, price, NOT_RATED);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Rating getRating() {
        return rating;
    }


    public BigDecimal getDiscount() {
        return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }
}

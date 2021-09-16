package com.productmanager.data;

import java.math.BigDecimal;
import java.util.Objects;

import static com.productmanager.data.Rating.*;
import static java.math.RoundingMode.HALF_UP;

/**
 * {@code Product} class represents the properties and behaviours of
 * a product object in a Product Management System.
 * <br>
 * Discount and rating is applicable to each product.
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

    public Product() {
        this(0, "no name", BigDecimal.ZERO, NOT_RATED);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Rating getRating() {
        return rating;
    }

    public String getRatingDisplay() {
        return rating.getStars();
    }

    public BigDecimal getDiscount() {
        return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }

    public Product applyNewRating(Rating newRating){
        return new Product(id, name, price, newRating);
    }

    @Override
    public String toString() {
        return id+" "+name+" "+price+getDiscount()+" "+getRatingDisplay();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        if( o.getClass() != this.getClass()) return false;
        Product product = (Product) o;
        return getId() == product.getId() && Objects.equals(getName(), product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

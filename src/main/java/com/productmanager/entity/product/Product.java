package com.productmanager.entity.product;

import com.productmanager.entity.review.Rateable;
import com.productmanager.entity.review.Rating;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static java.math.RoundingMode.HALF_UP;

/**
 * {@code Product} class represents the properties and behaviours of
 * a product object in a Product Management System.
 * <br>
 * Discount and rating is applicable to each product.
 */


public abstract class Product implements Rateable<Product> {

    private int id;
    private String product_name;
    private BigDecimal price;
    private final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.1);
    private Rating rating;

    public LocalDate getBestBefore() {
        return LocalDate.now();
    }

    public Product(int id, String product_name, BigDecimal price, Rating rating) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.rating = rating;
    }

    public Product(int id, String product_name, BigDecimal price) {
        this(id, product_name, price, Rating.NOT_RATED);
    }

    public int getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public Rating getRating() {
        return rating;
    }

    public String getRatingDisplay() {
        return rating.getStars();
    }

    public BigDecimal getDiscount() {
        return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }


    @Override
    public String toString() {
        return id+" "+ product_name +" "+price+" " +getDiscount()+" "+getRatingDisplay()+" "+getBestBefore() ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        if( o.getClass() != this.getClass()) return false;
        Product product = (Product) o;
        return getId() == product.getId() && Objects.equals(getProduct_name(), product.getProduct_name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

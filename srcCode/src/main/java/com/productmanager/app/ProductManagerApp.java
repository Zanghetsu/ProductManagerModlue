package com.productmanager.app;

import com.productmanager.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * {@code ProductManagerApp} class represents a shop inventory management system
 */

public class ProductManagerApp {
    public static void main(String[] args) {
        ProductManager productManager = new ProductManager();
        Product p1 = productManager.createProduct(101, "Coca Cola", BigDecimal.valueOf(1.99), Rating.FIVE_STAR);
        Product p2 = productManager.createProduct(102, "Hamburger", BigDecimal.valueOf(20.78), Rating.THREE_STAR, LocalDate.now().plusDays(2));
        Product p3 = productManager.createProduct(102, "Pizza", BigDecimal.valueOf(18.97), Rating.FIVE_STAR, LocalDate.now().plusDays(3));
        Product p4 = p3.applyNewRating(Rating.FOUR_STAR);
        Product p5 = productManager.createProduct(105, "Cocoa", BigDecimal.valueOf(4.37),Rating.NOT_RATED);
        Product p6 = productManager.createProduct(105, "Cocoa", BigDecimal.valueOf(4.37), Rating.NOT_RATED, LocalDate.now().plusDays(3));

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);
        System.out.println(p6);

    }
}

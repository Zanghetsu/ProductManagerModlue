package com.productmanager.app;

import com.productmanager.data.Product;
import com.productmanager.data.Rating;

import java.math.BigDecimal;

/**
 * {@code ProductManagerApp} class represents a shop inventory management system
 */

public class ProductManagerApp {
    public static void main(String[] args) {
        Product p1 = new Product(101, "Coca Cola", BigDecimal.valueOf(1.99));
        Product p2 = new Product(102, "Hamburger", BigDecimal.valueOf(20.78), Rating.THREE_STAR);
        Product p3 = new Product(102, "Pizza",BigDecimal.valueOf(18.97), Rating.FIVE_STAR);

        System.out.println(p1.getName()+" "+p1.getPrice()+" "+p1.getDiscount()+" "+p1.getRatingDisplay());
        System.out.println(p2.getName()+" "+p2.getPrice()+" "+p2.getDiscount()+" "+p2.getRatingDisplay());
        System.out.println(p3.getName()+" "+p3.getPrice()+" "+p3.getDiscount()+" "+p3.getRatingDisplay());

    }
}

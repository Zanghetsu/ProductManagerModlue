package com.productmanager.app;

import com.productmanager.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * {@code ProductManagerApp} class represents a shop inventory management system
 */

public class ProductManagerApp {
    public static void main(String[] args) {
        ProductManager productManager = new ProductManager(Locale.UK);

        Product p1 = productManager.createProduct(101, "Coca Cola", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        productManager.printProductReport(p1);
        p1 = productManager.reviewProduct(p1, Rating.THREE_STAR,"Not the best that I have drank...");
        p1 = productManager.reviewProduct(p1, Rating.TWO_STAR,"Not too good...");
        p1 = productManager.reviewProduct(p1, Rating.ONE_STAR,"The best...");

        /* Product p2 = productManager.createProduct(102, "Hamburger", BigDecimal.valueOf(20.78), Rating.THREE_STAR, LocalDate.now().plusDays(2));
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
        */
         productManager.printProductReport(p1);
    }
}

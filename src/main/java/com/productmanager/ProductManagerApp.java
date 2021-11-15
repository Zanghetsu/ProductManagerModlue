package com.productmanager;

import com.productmanager.entity.ProductManager;
import com.productmanager.entity.product.Product;
import com.productmanager.entity.review.Rating;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * {@code ProductManagerApp} class represents a shop inventory management system
 */

public class ProductManagerApp {
    public static void main(String[] args) {
        ProductManager productManager = new ProductManager("en-US");

        productManager.createProduct(101, "Coca Cola", BigDecimal.valueOf(1.99), Rating.NOT_RATED);

        productManager.reviewProduct(101, Rating.THREE_STAR, "Not the best that I have drank...");
        productManager.reviewProduct(101, Rating.TWO_STAR, "Not too good...");
        productManager.reviewProduct(101, Rating.ONE_STAR, "The best...");

        productManager.createProduct(102, "Hamburger", BigDecimal.valueOf(20.78), Rating.THREE_STAR, LocalDate.now().plusDays(2));
        productManager.reviewProduct(102, Rating.THREE_STAR, "Not the best that I have drank...");
        productManager.reviewProduct(102, Rating.TWO_STAR, "Not too good...");
        productManager.reviewProduct(102, Rating.FIVE_STAR, "The best...");


        productManager.parseReview("101,4,Nice bevrage");
        productManager.printProduct(101);

        /*Product p3 = productManager.createProduct(102, "Pizza", BigDecimal.valueOf(18.97), Rating.FIVE_STAR, LocalDate.now().plusDays(3));
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
        //productManager.printProductReport(101);
        //productManager.printProductReport(p2);

        productManager.printProductsReportSorted(p -> p.getPrice().floatValue() < 2 ,(p1,p2) -> p2.getRating().ordinal() - p1.getRating().ordinal());
        Comparator<Product> ratingSorter = (p1, p2) -> p1.getRating().ordinal() - p2.getRating().ordinal();
        Comparator<Product> priceSorter = (p1,p2) -> p1.getPrice().compareTo(p2.getPrice());
        //productManager.printProductsReportSorted(ratingSorter.thenComparing(priceSorter));
        productManager.getDiscounts().forEach((rating,discount) -> System.out.println(rating +"\t"+discount));
    }
}

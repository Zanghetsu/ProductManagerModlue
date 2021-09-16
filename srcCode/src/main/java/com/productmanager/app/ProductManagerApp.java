package com.productmanager.app;

import com.productmanager.data.Drink;
import com.productmanager.data.Food;
import com.productmanager.data.Product;
import com.productmanager.data.Rating;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * {@code ProductManagerApp} class represents a shop inventory management system
 */

public class ProductManagerApp {
    public static void main(String[] args) {
        Product p1 = new Drink(101, "Coca Cola", BigDecimal.valueOf(1.99), Rating.FIVE_STAR);
        Product p2 = new Food(102, "Hamburger", BigDecimal.valueOf(20.78), Rating.THREE_STAR, LocalDate.now().plusDays(2));
        Product p3 = new Food(102, "Pizza",BigDecimal.valueOf(18.97), Rating.FIVE_STAR, LocalDate.now().plusDays(3));
        Product p4 = p3.applyNewRating(Rating.FOUR_STAR);

        System.out.println(p1.getName()+" "+p1.getPrice()+" "+p1.getDiscount()+" "+p1.getRatingDisplay());
        System.out.println(p2.getName()+" "+p2.getPrice()+" "+p2.getDiscount()+" "+p2.getRatingDisplay());
        System.out.println(p3.getName()+" "+p3.getPrice()+" "+p3.getDiscount()+" "+p3.getRatingDisplay());
        System.out.println(p4.getName()+" "+p4.getPrice()+" "+p4.getDiscount()+" "+p4.getRatingDisplay()); // -> actually this is only a copy of p3, p3 do not change

    }
}

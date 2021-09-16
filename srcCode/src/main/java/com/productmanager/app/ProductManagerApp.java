package com.productmanager.app;

import com.productmanager.data.Product;
import java.math.BigDecimal;

/**
 * {@code ProductManagerApp} class represents a shop inventory management system
 */

public class ProductManagerApp {
    public static void main(String[] args) {
        Product p1 = new Product();

        p1.setId(101);
        p1.setName("Coca Cola");
        p1.setPrice(BigDecimal.valueOf(1.99));

        System.out.println(p1.getId() +" "+p1.getPrice()+ " "+p1.getName()+" "+p1.getDiscount());
    }
}

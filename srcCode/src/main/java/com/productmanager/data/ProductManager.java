package com.productmanager.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class ProductManager {

    private Map<Product, List<Review>> products = new HashMap<>();
    private Locale locale;
    private ResourceBundle resources;
    private DateTimeFormatter dateFormatter;
    private NumberFormat currencyFormatter;


    public ProductManager(Locale locale) {
        this.locale = locale;
        resources = ResourceBundle.getBundle("reviews_en_GB");
        dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
        currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        Product product = new Food(id, name, price, rating, bestBefore);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
        Product product = new Drink(id, name, price, rating);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product reviewProduct(Product productUnderReview, Rating rating, String comments) {
        List<Review> reviews = products.get(productUnderReview);
        products.remove(productUnderReview, reviews);
        reviews.add(new Review(rating, comments));
        int sum = 0;

        for (Review review : reviews) {
            sum += review.getRating().ordinal();
        }

        productUnderReview = productUnderReview.applyNewRating(Rateable.convert(Math.round((float) sum / reviews.size())));
        products.put(productUnderReview, reviews);
        return productUnderReview;

    }

    public void printProductReport(Product product) {

        StringBuilder txt = new StringBuilder();
        List<Review> reviews = products.get(product);

        txt.append(MessageFormat.format(resources.getString("product"),
                product.getName(),
                currencyFormatter.format(product.getPrice()),
                product.getRating().getStars(),
                dateFormatter.format(product.getBestBefore())));
        txt.append("\n");

        for (Review review : reviews) {
            txt.append(MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(),
                    review.getComments()));
            txt.append("\n");
        }

        if (reviews.isEmpty()) {
            txt.append(resources.getString("no.review"));
            txt.append("\n");
        }
        System.out.println(txt);
    }
}

package com.productmanager.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

//TODO : Later refactor the whole to ProductDAO!!

public class ProductManager {

    private Map<Product, List<Review>> products = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(ProductManager.class.getName());
    private Formatter formatter;
    private static Map<String, Formatter> resourceFormatters =
            Map.of("en-GB", new Formatter(Locale.UK),
                    "en-US", new Formatter(Locale.US),
                    "ger-GER", new Formatter(Locale.GERMAN),
                    "fr-FR", new Formatter(Locale.FRANCE),
                    "jp-JAP", new Formatter(Locale.JAPAN)
            );

    public ProductManager(Locale locale) {
        this(locale.getDisplayLanguage());
    }

    public ProductManager(String languageTag) {
        setLocale(languageTag);
    }

    public void setLocale(String languageTag) {
        formatter = resourceFormatters.getOrDefault(languageTag, resourceFormatters.get("en-GB"));
    }

    public static Set<String> getSupportedLocaleTags() {
        return resourceFormatters.keySet();
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

    public Product findProductById(int id) throws ProdManException {
        return products.keySet().stream().filter(p -> p.getId() == id).findFirst().orElseThrow(() -> new ProdManException("product with "+id+" not found!!"));
    }

    public Product reviewProduct(int id, Rating rating, String comments) {
        try{
            return reviewProduct(findProductById(id), rating, comments);
        } catch (ProdManException exc){
            LOGGER.log(Level.SEVERE,null,exc);
        }
        return null;
    }

    public Product reviewProduct(Product productUnderReview, Rating rating, String comments) {
        List<Review> reviews = products.get(productUnderReview);
        products.remove(productUnderReview, reviews);
        reviews.add(new Review(rating, comments));

        productUnderReview = productUnderReview.applyNewRating(Rateable
                .convert(
                        (int) Math.round(reviews.stream().mapToInt(r -> r.getRating()
                                .ordinal())
                                .average()
                                .orElse(0))));

        products.put(productUnderReview, reviews);
        return productUnderReview;

    }

    public void printProduct(int id) {
        try {
            printProductReport(findProductById(id));
        } catch (ProdManException e) {
            LOGGER.log(Level.SEVERE,null,e);
        }
    }

    public void printProductReport(Product product) {
        StringBuilder txt = new StringBuilder();
        List<Review> reviews = products.get(product);
        txt.append(formatter.productFormatter(product));
        txt.append("\n");

        if (reviews.isEmpty()) {
            txt.append(formatter.getTextFromBundle("no.review"));
        } else {
            txt.append(reviews.stream().map(r -> formatter.reviewFormatter(r) + "\n").collect(Collectors.joining()));
        }
        System.out.println(txt);
    }

    public void printProductsReportSorted(Comparator<Product> sorter) {
        StringBuilder sb = new StringBuilder();
        products.keySet().stream().sorted(sorter).forEach(p -> sb.append(formatter.productFormatter(p)).append("\n"));

        System.out.println(sb);
    }

    public void printProductsReportSorted(Predicate<Product> filter, Comparator<Product> sorter) {
        StringBuilder sb = new StringBuilder();
        products.keySet().stream().sorted(sorter).filter(filter).forEach(p -> sb.append(formatter.productFormatter(p)).append("\n"));

        System.out.println(sb);
    }

    private static class Formatter {
        private Locale locale;
        private ResourceBundle resources;
        private DateTimeFormatter dateFormatter;
        private NumberFormat currencyFormatter;

        private Formatter(Locale locale) {
            this.locale = locale;
            resources = ResourceBundle.getBundle("reviews_en_GB");
            dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
            currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        }

        private String productFormatter(Product product) {
            return MessageFormat.format(resources.getString("product"),
                    product.getName(),
                    currencyFormatter.format(product.getPrice()),
                    product.getRating().getStars(),
                    dateFormatter.format(product.getBestBefore()));
        }

        private String reviewFormatter(Review review) {
            return MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(),
                    review.getComments());
        }

        private String getTextFromBundle(String resourceKey) {
            return resources.getString(resourceKey);
        }


    }

    public Map<String, String> getDiscounts() {
        return products.keySet().stream().collect(Collectors.groupingBy(product -> product.getRating().getStars(),
                Collectors.collectingAndThen(
                        (Collectors.summingDouble(
                                product -> product.getDiscount()
                                        .doubleValue())),
                        discount -> formatter.currencyFormatter.format(discount))));
    }
}

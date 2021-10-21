package com.productmanager.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

//TODO : Later refactor the whole to ProductDAO!!

public class ProductManager {

    private Map<Product, List<Review>> products = new HashMap<>();
    private ResourceBundle config = ResourceBundle.getBundle("config");
    private MessageFormat reviewFormat = new MessageFormat(config.getString("review.data.format"));
    private MessageFormat productFormat = new MessageFormat(config.getString("product.data.format"));
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
        return products.keySet().stream().filter(p -> p.getId() == id).findFirst().orElseThrow(() -> new ProdManException("product with id: "+id+" not found!!"));
    }

    public Product reviewProduct(int id, Rating rating, String comments) {
        try{
            return reviewProduct(findProductById(id), rating, comments);
        } catch (ProdManException exc){
            LOGGER.log(Level.INFO,exc.getMessage());
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

    public void parseProduct(String text){
        try{
            Object[] values = productFormat.parse(text);
            int id = Integer.parseInt((String) values[1]);
            String name = (String) values[2];
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble((String) values[3]));
            Rating rating = Rateable.convert(Integer.parseInt((String) values[4]));
            switch ((String) values[0]) {
                case "D" -> createProduct(id, name, price, rating);
                case "F" -> {
                    LocalDate bestBefore = LocalDate.parse((String) values[5]);
                    createProduct(id, name, price, rating, bestBefore);
                }
            }
        } catch (ParseException |
                NumberFormatException |
                DateTimeParseException e) {
            LOGGER.log(Level.SEVERE, "Error parsing product "+ text + " " + e.getMessage());
        }
    }


    public void parseReview(String text){
        try {
            Object[] values = reviewFormat.parse(text);
            reviewProduct(Integer.parseInt((String) values[0]),
                    Rateable.convert(Integer.parseInt((String) values[1])),
                    (String) values[2]);
        }
        catch (ParseException e){
            LOGGER.log(Level.WARNING, "Error parsing review " + text, e);
        };
    }

    public void printProduct(int id) {
        try {
            printProductReport(findProductById(id));
        } catch (ProdManException | NumberFormatException e) {
            LOGGER.log(Level.INFO,e.getMessage());
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

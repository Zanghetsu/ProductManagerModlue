package com.productmanager.data;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Drink extends Product {

    Drink(int id, String name, BigDecimal price, Rating rating) {
        super(id, name, price, rating);
    }

    Drink(int id, String name, BigDecimal price) {
        super(id,name,price);
    }

    @Override
    public Product applyNewRating(Rating newRating) {
        return new Drink(getId(), getName(), getPrice(), newRating );
    }

    @Override
    public BigDecimal getDiscount() {
        LocalTime now = LocalTime.now();
        return (now.isAfter(LocalTime.of(17, 30)) && now.isBefore(LocalTime.of(23, 30)))
                ? super.getDiscount() : BigDecimal.ZERO;
    }

    //TODO : REFACTOR CODE -> REMOVE MAGIC NUMBERS, ADD unpacking to method param, fill in numbers,
    // extract whole method into an interface (like below)

    /*
    public BigDecimal getDiscount(int hour, int minute, int untilHour, int untilMinute) {
        LocalTime now = LocalTime.now();
        return (now.isAfter(LocalTime.of(hour,minute)) && now.isBefore(LocalTime.of(untilHour,untilMinute)))
                ? super.getDiscount() : BigDecimal.ZERO;
    }
     */
}

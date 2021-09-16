package com.productmanager.data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This {@subclass Food} is an extension of product class,
 * it has some props and methods specific to this subclass
 * such as best before.
 */

public class Food extends Product{

    private LocalDate bestBefore;

    public Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        super(id, name, price, rating);
        this.bestBefore = bestBefore;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    @Override
    public String toString() {
        return super.toString()+" "+ getBestBefore();
    }

    @Override
    public BigDecimal getDiscount() {
        return (bestBefore.isEqual(LocalDate.now()) ? super.getDiscount() : BigDecimal.ZERO);
    }
}

package com.productmanager.data;

/**
 * This is a simple representation of a ratings system,
 * using unicode characters (u2605 and u2606) to display ratings early on the project build process
 */
public enum Ratings {
    NOT_RATED("\u2606\u2606\u2606\u2606\u2606"),
    ONE_STAR("\u2605\u2606\u2606\u2606\u2606"),
    TWO_STAR("\u2605\u2605\u2606\u2606\u2606"),
    THREE_STAR("\u2605\u2605\u2605\u2606\u2606"),
    FOUR_STAR("\u2605\u2605\u2605\u2605\u2606"),
    FIVE_STAR("\u2605\u2605\u2605\u2605\u2605");


    private final String stars;

    Ratings(String stars) {
        this.stars = stars;
    }

    public String getStars() {
        return stars;
    }
}

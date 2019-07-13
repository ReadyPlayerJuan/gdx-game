package com.mygdx.game.weapons;

public enum WeaponStarRating {
    ONE_STAR (1),
    TWO_STARS (2),
    THREE_STARS (3);

    private int rating;
    private String starString;
    WeaponStarRating(int rating) {
        this.rating = rating;
        starString = "*";
        for(int i = 1; i < rating; i++) {
            starString += "*";
        }
    }

    public static WeaponStarRating random() {
        return WeaponStarRating.values()[(int)(Math.random() * WeaponStarRating.values().length)];
    }

    public int getRating() {
        return rating;
    }

    public String getStarString() {
        return starString;
    }
}

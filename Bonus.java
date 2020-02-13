package com.tema1.Engine_Game;

final public class Bonus {
    private int id;
    private int numberofgoods;

    Bonus() {
    }

    Bonus(final int id, final int numberofgoods) {
        this.id = id;
        this.numberofgoods = numberofgoods;
    }

    public int getId() {
        return id;
    }

    public int getNumberofgoods() {
        return numberofgoods;
    }
}

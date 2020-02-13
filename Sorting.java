package com.tema1.Engine_Game;

import java.util.Comparator;

final public class Sorting implements Comparator<Bonus> {
    public int compare(final Bonus a, final Bonus b) {
        if (a.getNumberofgoods() == b.getNumberofgoods()) {
            return a.getId() - b.getId();
        }
        return b.getNumberofgoods() - a.getNumberofgoods();
    }
}

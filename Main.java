package com.tema1.main;

import com.tema1.Engine_Game.Engine;
import com.tema1.Participants.Competitor;

import java.util.List;

public final class Main {
    private Main() {
        // just to trick checkstyle
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        Engine myengine = new Engine();
        myengine.gamestart(gameInput);
        myengine.sumpoints();
       myengine.sumbonuses();
        //TODO implement homework logic
        List<Competitor> displayplayers = myengine.getMyplayers();
        List<String> strings1 = gameInput.getPlayerNames();
        displayplayers.sort((final Competitor first, final Competitor second) ->
                (second.getSumofmoney() - first.getSumofmoney()));
        for (Competitor i : displayplayers) {
            System.out.println(i.getId() + " " + strings1.get(i.getId()).toUpperCase()
                    + " " + i.getSumofmoney());
        }
    }
}

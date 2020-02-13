package com.tema1.Engine_Game;

import com.tema1.Participants.Competitor;
import com.tema1.Participants.Bribedplayer;
import com.tema1.Participants.Greedyplayer;
import com.tema1.Participants.Basicplayer;
import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.main.GameInput;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Collections;

final public class Engine {
    private List<String> players = new LinkedList<>();
    private List<Integer> ids = new LinkedList<>();
    private List<Competitor> myplayers = new LinkedList<>();

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(final List<String> players) {
        this.players = players;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void gamestart(final GameInput inputinitial) {
        setPlayers(inputinitial.getPlayerNames());
        ids = inputinitial.getAssetIds();
        int norounds = inputinitial.getRounds();
        for (int i = 0; i < getPlayers().size(); i++) {
            switch (getPlayers().get(i)) {
                case "greedy":
                    myplayers.add(new Greedyplayer());
                    break;
                case "basic":
                    myplayers.add(new Basicplayer());
                    break;
                case "bribed":
                    myplayers.add(new Bribedplayer());
                    break;
            }
            try {
                myplayers.get(i).setId(i);
           } catch (IndexOutOfBoundsException ExtPtr) {
            }
        }
        GoodsFactory factorygoods = GoodsFactory.getInstance();
        for (Integer i : getIds()) {
            Goods item = factorygoods.getGoodsById(i);
            Competitor.add(item);
        }
        for (int i = 0; i < norounds; i++) {
            Competitor.setNumberofrounds();
            for (int j = 0; j < myplayers.size(); j++) {
                Competitor current = myplayers.get(j);
                for (int k = 0; k < myplayers.size(); k++) {
                    if (myplayers.get(k) != current) {
                        myplayers.get(k).DrawSomeGoods();
                        myplayers.get(k).comercial();
                    }
                }
                current.sheriff(myplayers);
            }

        }
    }
    public void sumpoints() {
        Map<Goods, Integer> goods = new HashMap<Goods, Integer>();
        for (Competitor i : myplayers) {
            goods = i.getTotalGoods();
            for (Map.Entry<Goods, Integer> entry: goods.entrySet()) {
                if (entry.getValue() > 0) {
                    i.modifysum(entry.getValue() * entry.getKey().getProfit());
                }
            }
        }
    }

    public void sumbonuses() {
        ArrayList<Bonus> bonusgoods = new ArrayList<Bonus>();
        GoodsFactory factory = GoodsFactory.getInstance();
        int value1 = -1;
        int value2 = -1;
        boolean condition1 = false;
        boolean condition2 = false;
        for (int i = 0; i <= Constants.getCARDSINHAND(); i++) {
            for (Competitor j : myplayers) {
                Map<Goods, Integer> mymap = j.getTotalGoods();
                int value = mymap.get(factory.getGoodsById(i));
                Bonus aux = new Bonus(j.getId(), value);
                bonusgoods.add(aux);
            }
            Collections.sort(bonusgoods, new Sorting());
            if (bonusgoods.get(0).getNumberofgoods() != 0) {
                value1 = bonusgoods.get(0).getId();
                condition1 = true;
            } else {
                value1 = -1;
                condition1 = false;
            }
            if (bonusgoods.get(1).getNumberofgoods() != 0) {
                value2 = bonusgoods.get(1).getId();
                condition2 = true;
            } else {
                value2 = -1;
                condition2 = false;
            }
            for (Competitor j : myplayers) {
                if (j.getId() == value1 && condition1) {
                    j.modifysum(factory.getGoodsById(i).getKingBonus());
                }
                if (j.getId() == value2 && condition2) {
                    j.modifysum(factory.getGoodsById(i).getQueenBonus());
                }
            }
            bonusgoods.clear();
        }
    }

    public List<Competitor> getMyplayers() {
        return myplayers;
    }
}

package com.tema1.Participants;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;

import java.util.ArrayList;
import java.util.List;

public class Greedyplayer extends Basicplayer {
    GoodsFactory factory = GoodsFactory.getInstance();
    public void comercial() {
        super.comercial();
        if (super.getNumberofrounds() % 2 == 0
                && super.getInthebag().size() < Constants.getMAXBAG()) {
            int max = -1;
            int idillegal = -1;
            for (Goods i : super.getGoodsinhand()) {
                if (i.getType() == GoodsType.Illegal && i.getProfit() > max) {
                    max = i.getProfit();
                    idillegal = i.getId();
                }
            }
            for (Goods i : super.getGoodsinhand()) {
                if (i.getType() == GoodsType.Illegal && i.getProfit() == max) {
                    if (idillegal < i.getId()) {
                        idillegal = i.getId();
                    }
                }
            }
            if (idillegal != -1) {
            super.getInthebag().add(factory.getGoodsById(idillegal));
            }
        }
    }
    public void sheriff(final List<Competitor> players) {
        ArrayList<Goods> delete = new ArrayList<Goods>();
        int sumofpenalty = 0;
        for (Competitor i : players) {
            if (i.getBribe() == 0) {
            if (super.getSumofmoney() >= Constants.getMINVALUE() && i.getId() != super.getId()) {
                sumofpenalty = 0;
                for (Goods j : i.getInthebag()) {
                    if (i.getDeclaration().getId() != j.getId()) {
                        sumofpenalty += j.getPenalty();
                        delete.add(j);
                    }
                }
                if (sumofpenalty == 0) {
                    super.subpenalties(i.getInthebag().size()
                            * i.getDeclaration().getPenalty());
                    i.subpenalties(-i.getInthebag().size() * i.getDeclaration().getPenalty());
                } else {
                    super.subpenalties(-sumofpenalty);
                    i.subpenalties(sumofpenalty);
                }
                i.getInthebag().removeAll(delete);
            }
            } else {
                super.takeBribe(i.givebribe());
            }
            i.Putonthetable();
        }
    }
}

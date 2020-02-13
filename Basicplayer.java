package com.tema1.Participants;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basicplayer extends Competitor {
    public void comercial() {
        Map<Goods, Integer> map = new HashMap<>();
        GoodsFactory factory = GoodsFactory.getInstance();
        for (int i = 0; i <= Constants.getMAXID(); i++) {
            try {
                if (factory.getGoodsById(i).getType() == GoodsType.Legal) {
                    Goods aux = factory.getGoodsById(i);
                    map.put(aux, 0);
                }
            } catch (NullPointerException ExtPtr) {
            }
        }
        for (int i = 0; i < super.getGoodsinhand().size(); i++) {
            try {
                if (factory.getGoodsById(i).getType() == GoodsType.Legal) {
                    int number = map.get(super.getGoodsinhand().get(i));
                    map.put(super.getGoodsinhand().get(i), number + 1);
                }
            } catch (NullPointerException ExtPtr) {
            }
        }
        int maxim = -1, id = -1;
        for (Map.Entry<Goods, Integer> entry : map.entrySet()) {
            if ((entry.getValue() > maxim)) {
                maxim = entry.getValue();
                try {
                    id = entry.getKey().getId();
                } catch (NullPointerException ExtPtr) {
                }
            }
        }
        for (Map.Entry<Goods, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxim && entry.getKey().getProfit()
                    > factory.getGoodsById(id).getProfit()) {
                id = entry.getKey().getId();
            }
        }
        for (Map.Entry<Goods, Integer> entry : map.entrySet()) {
            if ((entry.getValue() == maxim) && (entry.getKey().getProfit()
                    == factory.getGoodsById(id).getProfit()) && (entry.getKey().getId()
                    > factory.getGoodsById(id).getId())) {
                id = entry.getKey().getId();
            }
        }
        if (id != -1) {
            super.setDeclaration(factory.getGoodsById(id));
        }
        for (Goods i : super.getGoodsinhand()) {
            if (i.getId() == id) {
                super.getInthebag().add(i);
            }
        }
        if (super.getInthebag().size() == 0) {
            int maximillegal = -1, idillegal = -1;
            for (Goods i : super.getGoodsinhand()) {
                if (i.getType() == GoodsType.Illegal && i.getProfit() > maximillegal) {
                    maximillegal = i.getProfit();
                    idillegal = i.getId();
                }
                if (i.getType() == GoodsType.Illegal && i.getProfit()
                        == maximillegal && i.getId() < idillegal) {
                    maximillegal = i.getProfit();
                    idillegal = i.getId();
                }
            }
            super.getInthebag().add(factory.getGoodsById(idillegal));
            super.setDeclaration(factory.getGoodsById(0));
        }
    }
    public void sheriff(final List<Competitor> players) {
        ArrayList<Goods> delete = new ArrayList<Goods>();
        int ii = 0, jj = 0;
        for (Competitor i : players) {
            int sumofpenalty = 0;
            if (super.getSumofmoney() >= Constants.getMINVALUE() && i.getId() != super.getId()) {
                for (Goods j : i.getInthebag()) {
                    try {
                         ii = i.getDeclaration().getId();
                         jj = j .getId();
                    } catch (NullPointerException extptr) {
                    }
                    if (ii != jj) {
                        sumofpenalty += j.getPenalty();
                        delete.add(j);
                    }
                }
                if (sumofpenalty == 0) {
                    try {
                        super.subpenalties(i.getInthebag().size()
                                * i.getDeclaration().getPenalty());
                        i.subpenalties(-i.getInthebag().size()
                                * i.getDeclaration().getPenalty());
                    } catch (NullPointerException extPtr) {
                    }
                } else {
                    super.subpenalties(-sumofpenalty);
                    i.subpenalties(sumofpenalty);
                }
                if (sumofpenalty != 0) {
                    i.getInthebag().removeAll(delete);
                }

            }
            i.Putonthetable();
        }
        delete.clear();
    }
}

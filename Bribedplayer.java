package com.tema1.Participants;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;

import java.util.ArrayList;
import java.util.List;

public class Bribedplayer extends Basicplayer {
    private Competitor left = null;
    private Competitor right = null;
    public void comercial() {
        ArrayList<Goods> legal = new ArrayList<Goods>();
        ArrayList<Goods> illegal = new ArrayList<Goods>();
        GoodsFactory factory = GoodsFactory.getInstance();
        for (Goods i : super.getGoodsinhand()) {
            if (i.getType() == GoodsType.Illegal) {
                illegal.add(i);
            } else {
                legal.add(i);
            }
        }
        legal.sort((final Goods card1, final Goods card2) ->
                card2.getProfit() - card1.getProfit());
        illegal.sort((final Goods card1, final Goods card2) ->
                card2.getProfit() - card1.getProfit());
        if (illegal.size() == 0 || super.getSumofmoney() <= Constants.getBRIBE2()) {
            super.setBribe(0);
            super.comercial();
        } else {
           // System.out.println("da");
            if (illegal.size() > 2 && super.getSumofmoney() > Constants.getBRIBE1()) {
                    super.setBribe(Constants.getBRIBE1());
                    if (super.getSumofmoney() / Constants.getPENALTYVALUE() > 2) {
                        for (Goods i : illegal) {
                            if (super.getInthebag().size() < Constants.getMAXBAG()) {
                                super.getInthebag().add(factory.getGoodsById(i.getId()));
                            }
                        }
                    }
                for (int i = 0; i < Constants.getMAXBAG() - illegal.size(); i++) {
                    if ((super.getSumofmoney() - super.getInthebag().size()
                            * Constants.getPENALTYVALUE()) / 2
                            - (Constants.getMAXBAG() - super.getInthebag().size()) > 0) {
                        if (super.getInthebag().size() < Constants.getMAXBAG()) {
                            super.getInthebag().add(factory.getGoodsById(legal.get(i).getId()));
                        }
                    }
                }
                super.setDeclaration(factory.getGoodsById(0));
            } else {
                super.setBribe(Constants.getBRIBE2());
                    for (Goods i : illegal) {
                        if (super.getInthebag().size() < Constants.getMAXBAG()) {

                            super.getInthebag().add(factory.getGoodsById(i.getId()));
                        }
                    }
                for (int i = 0; i < Constants.getMAXBAG() - illegal.size(); i++) {
                    if ((super.getSumofmoney() - super.getInthebag().size()
                            * Constants.getPENALTYVALUE())
                            / 2 - (Constants.getMAXBAG() - super.getInthebag().size()) > 0) {
                        if (super.getInthebag().size() < Constants.getMAXBAG()) {
                            super.getInthebag().add(factory.getGoodsById(legal.get(i).getId()));
                        }
                    }
                }
                super.setDeclaration(factory.getGoodsById(0));
            }
        }
    }
    public void sheriff(final List<Competitor> players) {
        if (left == null) {
            if (players.size() == 2) {
                if (super.getId() == 1) {
                    right = players.get(0);
                    left = players.get(0);
                }
                if (super.getId() == 0) {
                    right = players.get(1);
                    left = players.get(1);
                }
            }
            if (super.getId() == 0) {
                left = players.get(players.size() - 1);
            } else {
            left = players.get((super.getId() - 1) % players.size());
            }
            right = players.get((super.getId() + 1) % players.size());
        }
        ArrayList<Goods> delete = new ArrayList<Goods>();
        for (Competitor i : players) {
            if (i.getId() == right.getId() || i.getId() == left.getId()) {
                int sumofpenalty = 0;
                if (super.getSumofmoney() >= Constants.getMINVALUE()
                        && i.getId() != super.getId()) {
                    for (Goods j : i.getInthebag()) {
                        if (i.getDeclaration().getId() != j.getId()) {
                            sumofpenalty += j.getPenalty();
                            delete.add(j);
                        }
                    }
                    if (sumofpenalty == 0) {
                        super.subpenalties(i.getInthebag().size()
                                * i.getDeclaration().getPenalty());
                        i.subpenalties(-i.getInthebag().size()
                                * i.getDeclaration().getPenalty());
                    } else {
                        super.subpenalties(-sumofpenalty);
                        i.subpenalties(sumofpenalty);
                    }
                    i.getInthebag().removeAll(delete);

                }
                i.Putonthetable();
            }
            if (i.getId() != right.getId() && i.getId() != left.getId()) {
                i.getInthebag().removeAll(delete);
                i.Putonthetable();
            }
        }
        delete.clear();
    }
}

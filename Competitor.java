package com.tema1.Participants;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;

import java.util.ArrayList;
import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;


abstract public class Competitor {
    private ArrayList<Goods> goodsinhand = new ArrayList<Goods>();
    private ArrayList<Goods> inthebag = new ArrayList<Goods>();
    static Queue<Goods> queue1 = new LinkedList<>();
    private int id;
    private int sumofmoney = Constants.getINITIALMONEY();
    private Goods declaration;
    private Map<Goods, Integer> totalGoods;
    private int bribe = 0;
    private static int numberofrounds = 0;
    Competitor() {
        sumofmoney = Constants.getINITIALMONEY();
        bribe = 0;
        goodsinhand = new ArrayList<>();
        inthebag = new ArrayList<Goods>();
        totalGoods = new HashMap<Goods, Integer>();
        GoodsFactory factory = GoodsFactory.getInstance();
        for (int i = 0; i <= Constants.getMAXID(); i++) {
            totalGoods.put(factory.getGoodsById(i), 0);
        }
    }
    public final void takeBribe(final int something) {
        this.sumofmoney += something;
    }
    public final ArrayList<Goods> getGoodsinhand() {
        return goodsinhand;
    }
    public final ArrayList<Goods> getInthebag() {
        return inthebag;
    }
    public void modifysum(final int sum) {
        this.sumofmoney += sum;
    }
    public Map<Goods, Integer> getTotalGoods() {
        return totalGoods;
    }

    public final int getBribe() {
        return bribe;
    }
    public final int givebribe() {
        sumofmoney -= bribe;
        return bribe;
    }

    public final void setBribe(final int bribe) {
        this.bribe = bribe;
    }

    public final static int getNumberofrounds() {
        return numberofrounds;
    }

    public final static void setNumberofrounds() {
        numberofrounds++;
    }

    public final int getSumofmoney() {
        return sumofmoney;
    }

    public final Goods getDeclaration() {
        return declaration;
    }

    public final void setDeclaration(final Goods declaration) {
        this.declaration = declaration;
    }
    public final void subpenalties(final int sum) {
        sumofmoney -= sum;

    }
    public void DrawSomeGoods() {
        if (goodsinhand.size() != 0) {
            goodsinhand.clear();
        }
        for (int i = 0; i <= Constants.getCARDSINHAND(); i++) {
            goodsinhand.add(queue1.peek());
            queue1.remove(queue1.peek());
        }
    }
    public void Putonthetable() {
        if (getInthebag().size() != 0) {
            for (int t = 0; t < getInthebag().size(); t++) {
                    Goods item = getInthebag().get(t);
                    if (item.getType() == GoodsType.Legal) {
                        int number = totalGoods.get(item);
                        totalGoods.put(item, number + 1);
                    } else {
                    int number2 = totalGoods.get(item);
                    totalGoods.put(item, number2 + 1);
                    Map<Goods, Integer> aux = item.getIllegalBonus();
                    Goods aux1;
                    for (Map.Entry<Goods, Integer> entry : aux.entrySet()) {
                        aux1 = entry.getKey();
                        for (int i = 0; i < entry.getValue(); i++) {
                            int number1 = totalGoods.get(aux1);
                            totalGoods.put(aux1, number1 + 1);
                        }
                    }
                }
            }
        }
        if (getInthebag().size() != 0) {
            getInthebag().clear();
        }
    }


    public static void add(final Goods item) {
        queue1.add(item);
    }
    public int getId() {
        return  this.id;
    }
    public void setId(final int a) {
        this.id = a;
    }

    public abstract void comercial();

    public abstract void sheriff(List<Competitor> players);
}

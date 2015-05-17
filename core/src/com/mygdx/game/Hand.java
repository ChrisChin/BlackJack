package com.mygdx.game;

import java.util.ArrayList;
import com.mygdx.game.Card.Rank;

public class Hand {
    private ArrayList<Card> cardList;
    private boolean completed = false;

    public Hand(){
        setCardList(new ArrayList<Card>());
    }

    /**
     * Finds the best possible maxTotal with the given cardList
     * @return maxTotal of the cardList
     */
    public int maxTotal(){
        int total = 0;
        for(Card card: getCardList()){
            total +=card.getValue();
        }
        int numberOfAces = contains(Rank.ACE);
        while(total +10 <=21 && numberOfAces>0){
            total+= 10;
            numberOfAces--;
        }
        return total;
    }

    public int minTotal(){
        int total = 0;
        for(Card card: getCardList()){
            total +=card.getValue();
        }
        return total;
    }

    public boolean isBust(){
        return maxTotal() > 21;
    }

    /**
     * Finds the number of occurences of the Card rank in the hand
     * @param target The rank
     * @return the number of occurences of the rank
     */
    public int contains(Rank target) {
        int total = 0;
        for(Card card: getCardList()){
            if(target == card.getRank()){
                total++;
            }
        }
        return total;
    }

    public void addCard(Card card) {
        getCardList().add(card);
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

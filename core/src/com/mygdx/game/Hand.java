package com.mygdx.game;

import java.util.ArrayList;
import com.mygdx.game.Card.*;

public class Hand {

    //region global variables
    private ArrayList<Card> cardList;
    private boolean completed = false;
    //endregion

    //region constructor
    public Hand(){
        this.cardList = new ArrayList<Card>();
    }
    //endregion

    //region getter
    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public boolean isCompleted() {
        return completed;
    }
    //endregion

    //region setter
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    //endregion

    //region public functions
    /**
     * Finds the best possible maxTotal with the given cardList
     * This takes into account of the ACE card being either value of 1 or 11
     * @return maximum total of the hand
     */
    public int maxTotal(){

        //Loop through all the cards in the hand and sum the values
        int total = minTotal();

        //Determine whether we should be updating any of the ACE cards to be ACE HIGH values instead
        //Loop while the total + the difference between ACE and ACE HIGH is less than or equal to 21
        // and add the difference to the total
        // E.g. cards 8, ACE = 9
        //9 + (11 - 1) <= 21
        // 19 <= 21
        // update the total from 9 => 19
        int numberOfAces = numberOfCardRanks(Rank.ACE);
        int AceRankDifference = Card.getValue(Rank.ACEHIGH) - Card.getValue(Rank.ACE);
        while(total + AceRankDifference <= BlackJackController.maxHandValue && numberOfAces>0){
            total+= AceRankDifference;
            numberOfAces--;
        }
        return total;
    }

    /**
     * This function will return the minimum value of the cards
     * This takes the ACE card at its lowest value of 1
     * @return minimum total of the hand
     */
    public int minTotal(){
        int total = 0;
        for(Card card: getCardList()){
            total +=card.getValue();
        }
        return total;
    }

    public boolean isBust(){
        return maxTotal() > BlackJackController.maxHandValue;
    }

    /**
     * Finds the number of occurrences of the Card rank in the hand
     * @param target The rank
     * @return the number of occurrences of the rank
     */
    public int numberOfCardRanks(Rank target) {
        int total = 0;
        for(Card card: getCardList()){
            if(target == card.getRank()){
                total++;
            }
        }
        return total;
    }
    //endregion

    //region public methods
    public void addCard(Card card) {
        getCardList().add(card);
    }
    //endregion
}
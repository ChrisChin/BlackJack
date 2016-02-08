package com.mygdx.game;

public class Card{
    private Suit suit; // HEARTS, CLUBS, DIAMONDS, SPADES
    private Rank rank;
    private boolean isDoubleDown;

    public boolean isDoubleDown() {
        return isDoubleDown;
    }

    public void setDoubleDown(boolean isDoubleDown) {
        this.isDoubleDown = isDoubleDown;
    }

    public enum Suit {
        HEARTS,
        CLUBS,
        DIAMONDS,
        SPADES
    }

    public enum Rank {
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.isDoubleDown = false;

    }

    /**
     * Get the suit of this card, between 0 (HEARTS) and 3 (SPADES).
     *
     * @return returns the suit of the card
     */
    public Suit suit() {
        return suit;
    }

    /**
     * Get the number of this card, between 2 and 14 (ACE).
     *
     * @return the rank of the card
     */
    public Rank rank() {
        return rank;
    }

    private static String[] suits = { "Hearts","Clubs","Diamonds","Spades"};
    private static String[] ranks = { "2 of ", "3 of ", "4 of ",
            "5 of ", "6 of ", "7 of ", "8 of ", "9 of ", "10 of ", "Jack of ",
            "Queen of ", "King of ", "Ace of " };

    public String toString() {
        return ranks[rank.ordinal()] + suits[suit.ordinal()];
    }

    /**
     * Finds the value of the card (2 - 10) are worth the same respectively
     * Jack queen and King are worth 10
     * Ace is default to 1 but can be worth 11 and is calculated in hand.maxTotal() method
     * @return the value of the card
     */
    public int getValue(){
        switch(this.rank()){
            case TWO: return 2;
            case THREE: return 3;
            case FOUR: return 4;
            case FIVE: return 5;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;
            case TEN: return 10;
            case JACK: return 10;
            case QUEEN: return 10;
            case KING: return 10;
            case ACE: return 1;
        }
        return 0;

    }

    /**
     * Returns the card number 1- 13 Ace - King used for the name of the image
     * returns card number @return
     */
    public int getCardImageNumber(){
        switch(this.rank()){
            case TWO: return 2;
            case THREE: return 3;
            case FOUR: return 4;
            case FIVE: return 5;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;
            case TEN: return 10;
            case JACK: return 11;
            case QUEEN: return 12;
            case KING: return 13;
            case ACE: return 1;
        }
        return 0;

    }

    /**
     * Return the first letter of the suit used for the name of the image
     * @return Returns the first letter of the suit used for the name of the image
     */
    public String getSuitImageLetter(){
          return this.suit.toString().substring(0,1).toLowerCase();
    }

    public String getImageName(){
        String suitLetter = getSuitImageLetter();
        int cardNumber = getCardImageNumber();
        return suitLetter + (cardNumber < 10 ? "0" : "") + cardNumber;

    }

    public Rank getRank(){
        return rank;
    }
}
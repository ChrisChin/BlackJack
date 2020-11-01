package com.mygdx.game;

public class Card{

    //region global variables
    private Suit suit; // HEARTS, CLUBS, DIAMONDS, SPADES
    private Rank rank;
    private boolean isDoubleDown;

    private static String[] suits = { "Hearts","Clubs","Diamonds","Spades"};
    private static String[] ranks = { "2",
                                      "3",
                                      "4",
                                      "5",
                                      "6",
                                      "7",
                                      "8",
                                      "9",
                                      "10",
                                      "Jack",
                                      "Queen",
                                      "King",
                                      "Ace" };
    //endregion

    //region enums
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
        ACE,
        ACEHIGH
    }
    //endregion

    //region getter
    public boolean isDoubleDown() {
        return isDoubleDown;
    }

    /**
     * Get the suit of this card, between 0 (HEARTS) and 3 (SPADES).
     *
     * @return returns the suit of the card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Get the number of this card, between 2 and 14 (ACE).
     *
     * @return the rank of the card
     */
    public Rank getRank() {
        return rank;
    }
    //endregion

    //region setter
    public void setDoubleDown(boolean isDoubleDown) {
        this.isDoubleDown = isDoubleDown;
    }
    //endregion

    //region constructor
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.isDoubleDown = false;
    }
    //endregion

    //region public functions
    public String toString() {
        return ranks[rank.ordinal()] + " of " + suits[suit.ordinal()];
    }

    /**
     * See getValue(Rank rank)
     * This is an overloads for getValue that uses the class's rank
     * @return
     */
    public int getValue(){
        return getValue(this.rank);
    }

    /**
     * Finds the value of the card (2 - 10) are worth the same respectively
     * Jack queen and King are worth 10
     * Ace is default to 1 but can be worth 11 and is calculated in hand.maxTotal() method
     * @return the value of the card
     */
    public static int getValue(Rank rank){
        switch(rank){
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
            case ACEHIGH: return 11;
        }
        throw new IllegalArgumentException("Unexpected Rank: " + rank.toString());
    }

    /**
     * Return the first letter of the suit used for the name of the image
     * @return Returns the first letter of the suit used for the name of the image
     */
    public String getSuitImageLetter(){
        return this.suit.toString().substring(0,1).toLowerCase();
    }

    /**
     * Gets the image name
     * @return
     */
    public String getImageName(){
        int cardNumber = getCardImageNumber();
        return getSuitImageLetter() + (cardNumber < 10 ? "0" : "") + cardNumber;
    }
    //endregion

    //region private functions
    /**
     * Returns the card number 1- 13 Ace - King used for the name of the image
     * returns card number @return
     */
    private int getCardImageNumber(){
        switch(this.rank){
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
            case ACEHIGH: return 1; //This is the same image as the ACE card
        }
        throw new IllegalArgumentException("Unexpected Rank: " + this.rank);
    }
    //endregion
}
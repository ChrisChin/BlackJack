package com.mygdx.game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.Card.Rank;
import com.mygdx.game.Card.Suit;

/**
 * @author Chris Chin
 * BlackJack follows the traditional Rules of blackjack
 * Hit - gives the player a new card
 * Split - splits a pair of cards of the same rank into two hands (does not include resplits)
 * Double Down - allows the user to pick only 1 card and ends their turn. Allows the player to
 *               double down after a split
 * Stand - ends the players turn
 * New Game - Creates a new game with a new deck
 *
 * The deck has 6 sets of cards. The game automatically checks for bust and
 * stands if the total is 21 or is blackjack
 *
 * The highscore is the number of round played until their money reaches <0
 *  win +2
 *  lose -2
 *  double down win +4
 *  double down lose -4
 *  blackjack +3
 *  split win both +2
 *  split win one +0
 *  split lose both -2
 */
public class BlackJack {
    private ArrayList<Card> deck;
    private Player player;
    private Player dealer;

    private Hand currentHand;
    private GameScreen gameScreen;
    private final int numberOfDecks = 6;
    private final int startingMoney = 3;
    private double money = startingMoney;
    private int numberofRounds = 0;


    private String message = "";
    private boolean allowPlayerOptions;
    private boolean split;
    private boolean doubledown;
    private boolean showDealerCard;

    public static Preferences prefs;


    /**
     * Constructor which sets up the game
     * @param gameScreen
     */
    public BlackJack(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        setPlayer(new Player());
        setDealer(new Player());
        showDealerCard = false;
        allowPlayerOptions = false;
        split = false;
        doubledown = false;
        prefs = Gdx.app.getPreferences("blackjack");
        if (!prefs.contains("highscore")) {
            prefs.putInteger("highscore", 0);
        }
    }



    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getDealer() {
        return dealer;
    }

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }


    public Hand getCurrentHand() {
        return currentHand;
    }

    public double getMoney(){return money;}

    public int getNumberofRounds(){return numberofRounds;}

    // Receives an integer and maps it to the String highScore in prefs
    public static void setHighScore(int val) {
        prefs.putInteger("highscore", val);
        prefs.flush();
    }

    // Retrieves the current high score
    public static int getHighScore() {
        return prefs.getInteger("highscore");
    }

    public boolean isAllowPlayerOptions() {
        return allowPlayerOptions;
    }

    public void setAllowPlayerOptions(boolean allowPlayerOptions) {
        this.allowPlayerOptions = allowPlayerOptions;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public boolean isDoubledown() {
        return doubledown;
    }

    public void setDoubledown(boolean doubledown) {
        this.doubledown = doubledown;
    }

    public boolean isShowDealerCard() {
        return showDealerCard;
    }

    public void setShowDealerCard(boolean showDealerCard) {
        this.showDealerCard = showDealerCard;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getStartingMoney() {
        return startingMoney;
    }

    public void setNumberofRounds(int numberofRounds) {
        this.numberofRounds = numberofRounds;
    }

    public void setCurrentHand(Hand currentHand) {
        this.currentHand = currentHand;
    }

    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

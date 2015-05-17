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
    private boolean showDealerCard;
    private boolean allowPlayerOptions;
    private boolean split;
    private Hand currentHand;
    private GameScreen gameScreen;
    private String message = "";
    private final int numberOfDecks = 6;
    private int money = 2;
    private int numberofRounds = 0;
    private boolean doubledown;
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
        createDeck();
    }

    /**
     * Gives the player a new card for the current hand
     * Checks if the maxTotal is over 21 or not
     */
    public void hit(){
        drawCard(currentHand); // draws a new card
        if(currentHand.isBust()|| currentHand.maxTotal() == 21) stand();
        checkButtons();
    }

    private void checkButtons() {
        Map<String,Button> buttonMap = gameScreen.getButtonRenderer().getButtonMap();
        if(canDoubleDown())buttonMap.get("doubledown").setVisible(true);
        else buttonMap.get("doubledown").setVisible(false);
        if (canSplit()) buttonMap.get("split").setVisible(true);
        else buttonMap.get("split").setVisible(false);
    }

    /**
     * Split is allowed if the player receives two cards of the same rank
     * Splits the primary hand into two hands (primary and secondary)
     */
    public void split(){
        if(canSplit()){
            split = true;
            // add a hand to the player splitting the cards
            Card secondCard = player.getPrimaryHand().getCardList().get(1);
            player.setSecondaryHand(new Hand()); // Creates a new hand for the player
            player.getSecondaryHand().addCard(secondCard); //adds the second card to the 2nd hand
            player.getPrimaryHand().getCardList().remove(secondCard); // Removes the second card from the primary hand
            drawCard(player.getPrimaryHand());
            drawCard(player.getSecondaryHand());
            if(player.getPrimaryHand().maxTotal() ==21){
                stand();
            }
        }
        checkButtons();
    }

    /**
     * Doubling down allows you to double your bet and
     * receive one (and only one) additional card to your hand
     * Player receives one card and ends the hand
     */
    public void doubleDown(){
        if (canDoubleDown()) {
            doubledown = true;
            drawCard(currentHand); // draws a new card
            currentHand.getCardList().get(2).setDoubleDown(true);//add double down attribute to the card
            stand(); // ends the players turn
        }
        checkButtons();
    }

    /**
     * Stand ends the round and the dealer draws cards until the dealer's maxTotal >=17
     */
    public void stand(){
        if(split && !player.getPrimaryHand().isCompleted()){
            player.getPrimaryHand().setCompleted(true);
            currentHand = player.getSecondaryHand();
            if(currentHand.maxTotal() ==21) stand();
        }
        else {
            setAllowPlayerOptions(false);
            //if not bust and not blackjack
            if((player.getPrimaryHand().maxTotal()<=21
                    || split && player.getSecondaryHand().maxTotal()<=21)
                     && !(player.getPrimaryHand().maxTotal()==21 && player.getPrimaryHand().getCardList().size() ==2)){
                while (dealer.getPrimaryHand().maxTotal() < 17) {
                    drawCard(dealer.getPrimaryHand());
                }
            }
            else{
                showDealerCard = false;
            }

            //display winner
            DetermineWinner(player.getPrimaryHand(),"Primary Hand: ");
            if(split){
                DetermineWinner(player.getSecondaryHand(),"Secondary Hand: ");
            }
        }
        checkButtons();
        if(money <0){
            message = message + " \nGame over";
            if(getHighScore() <numberofRounds) {
                setHighScore(numberofRounds);
                message = message + "\nNew High Score!";
            }
            gameScreen.getButtonRenderer().getButtonMap().get("newhand").setVisible(false);
        }
    }

    /**
     * Helper method to determine the winner
     * @param hand Player's hand used to determine the winner
     * @param handName Name of the hand
     */
    private void DetermineWinner(Hand hand,String handName){
        if(player.getSecondaryHand() ==null) handName = "";
        if(hand.maxTotal()>21){
            message = message + handName + "You went bust, Dealer wins \n";
            calculateScore(-2);
        }
        else if(dealer.getPrimaryHand().maxTotal()> 21){
            message = message + handName + "Dealer went bust, You win \n";
            calculateScore(2);
        }
        else if(hand.maxTotal() > dealer.getPrimaryHand().maxTotal()){
            message = message + handName + "You win \n";
            calculateScore(2);
        }
        else if(hand.maxTotal() == dealer.getPrimaryHand().maxTotal()){
            message = message + handName + "Tie \n";
            calculateScore(0);
        }
        else{ //dealer maxTotal > player maxTotal
            message = message + handName + "Dealer wins \n";
            calculateScore(-2);
        }
    }

    public void calculateScore(int result){
        if(doubledown) money += 2*result;
        else if(split) money += 0.5*result;
        else money +=result;
    }

    /**
     * Draws a card from the deck and gives the card to the given hand
     * @param hand
     */
    private void drawCard(Hand hand){
        if(!deck.isEmpty()) {
            hand.addCard(getDeck().remove(0));
        }
        else{
            message = message + " \nYou win";
            if(getHighScore() <numberofRounds) {
                setHighScore(numberofRounds);
                message = message + "\nNew High Score!";
            }
            gameScreen.getButtonRenderer().getButtonMap().get("newhand").setVisible(false);
        }
    }

    /**
     * Starts a new game with a new deck
     */
    public void newGame(){
        createDeck();
        money = 2;
        numberofRounds = 0;
        newHand();
    }

    /**
     * Creates a new hand with the current deck
     */
    public void newHand(){
        player = new Player();
        dealer = new Player();
        split = false;
        doubledown = false;
        deal();
    }

    /**
     * Deals the cards out to the player and the dealer
     * Checks for BlackJack
     */
    public void deal() {
        numberofRounds++;
        setAllowPlayerOptions(true);
        for(int i =0; i< 2; i++){
            drawCard(player.getPrimaryHand());
            drawCard(dealer.getPrimaryHand());
        }
//        player.getPrimaryHand().addCard(new Card(Suit.CLUBS,Rank.ACE));
//        player.getPrimaryHand().addCard(new Card(Suit.DIAMONDS,Rank.ACE));
        currentHand = player.getPrimaryHand();
        if(currentHand.maxTotal() == 21){
            stand();
            message = "You got BlackJack. You win";
            money +=1;
        }
        checkButtons();
    }

    /**
     * Creates a new Deck and shuffles it
     */
    private void createDeck() {
        setDeck(new ArrayList<Card>());
        for(int num = 0; num <numberOfDecks; num++){
            for(Rank rank :Rank.values()){
                for(Suit suit: Suit.values()){
                    getDeck().add(new Card(suit, rank));
                }
            }
        }
        Collections.shuffle(getDeck());
    }

    /**
     * Sets allowPlayerOptions to true or false
     * if true hides dealer card and show all buttons
     * if false shows dealer card and hides all buttons except for new game
     * @param allowPlayerOptions boolean to check if the game is over
     */
    public void setAllowPlayerOptions(boolean allowPlayerOptions) {
        this.allowPlayerOptions = allowPlayerOptions;
        Map<String,Button> buttonMap = gameScreen.getButtonRenderer().getButtonMap();
        if(!allowPlayerOptions){//finish game
            //hide all buttons except for newgame and newhand and back arrow
            setShowDealerCard(true);
            for(String buttonName: buttonMap.keySet()){
                if(buttonName.equals("newgame") || buttonName.equals("newhand")|| buttonName.equals("arrow")){
                    buttonMap.get(buttonName).setVisible(true);
                }
                else{
                    buttonMap.get(buttonName).setVisible(false);
                }
            }
        }
        else{//start game
            //show all buttons except double down and split
            setShowDealerCard(false);
            message = "";
            for(String buttonName: buttonMap.keySet()){
                if(buttonName.equals("doubledown") || buttonName.equals("split")|| buttonName.equals("newhand")) {
                    buttonMap.get(buttonName).setVisible(false);
                }
                else{
                    buttonMap.get(buttonName).setVisible(true);
                }
            }
        }
    }

    public boolean canSplit(){
        Card firstCard = player.getPrimaryHand().getCardList().get(0);
        Card secondCard = player.getPrimaryHand().getCardList().get(1);
        int handSize = player.getPrimaryHand().getCardList().size();
        if(allowPlayerOptions && firstCard.getRank() == secondCard.getRank() && handSize ==2 &&!split)
            return true;
        return false;
    }

    public boolean canDoubleDown(){
        return allowPlayerOptions && currentHand.getCardList().size() == 2;
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

    public boolean isShowDealerCard() {
        return showDealerCard;
    }

    public void setShowDealerCard(boolean showDealerCard) {
        this.showDealerCard = showDealerCard;
    }

    public String getMessage() {
        return message;
    }

    public Hand getCurrentHand() {
        return currentHand;
    }

    public boolean isAllowPlayerOptions() {
        return allowPlayerOptions;
    }

    public int getMoney(){return money;}

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

}

package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class BlackJackController {
    private GameScreen gameScreen;
    private BlackJack blackJack;

    public BlackJackController(GameScreen gameScreen,BlackJack blackJack){
        this.gameScreen = gameScreen;
        this.blackJack = blackJack;
        createDeck();
        deal();
    }

    public void parseButton(String buttonName) {
        if(buttonName.equals("hit"))
            hit();
        else if(buttonName.equals("split"))
            split();
        else if(buttonName.equals("stand"))
            stand();
        else if(buttonName.equals("doubledown"))
            doubleDown();
        else if(buttonName.equals("newgame"))
            newGame();
        else if(buttonName.equals("newhand"))
            newHand();
        else if(buttonName.equals("arrow"))
            gameScreen.getMain().setScreenMainMenu();
    }

    /**
     * Gives the player a new card for the current hand
     * Checks if the maxTotal is over 21 or not
     */
    public void hit(){
        Hand currentHand = blackJack.getCurrentHand();
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
        Player player = blackJack.getPlayer();
        if(canSplit()){
            blackJack.setSplit(true);
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
        Hand currentHand = blackJack.getCurrentHand();
        if (canDoubleDown()) {
            blackJack.setDoubledown(true);
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
        Player player = blackJack.getPlayer();
        Player dealer = blackJack.getDealer();
        Hand currentHand = blackJack.getCurrentHand();
        if(blackJack.isSplit() && !player.getPrimaryHand().isCompleted()){
            player.getPrimaryHand().setCompleted(true);
            currentHand = player.getSecondaryHand();
            if(currentHand.maxTotal() ==21) stand();
        }
        else {
            setAllowPlayerOptions(false);
            //if not bust and not blackjack
            if((player.getPrimaryHand().maxTotal()<=21
                    || blackJack.isSplit() && player.getSecondaryHand().maxTotal()<=21)
                    && !(player.getPrimaryHand().maxTotal()==21 && player.getPrimaryHand().getCardList().size() ==2)){
                while (dealer.getPrimaryHand().maxTotal() < 17) {
                    drawCard(dealer.getPrimaryHand());
                }
            }
            else{
                blackJack.setShowDealerCard(false);
            }

            //display winner
            DetermineWinner(player.getPrimaryHand(),"Primary Hand: ");
            if(blackJack.isSplit()){
                DetermineWinner(player.getSecondaryHand(),"Secondary Hand: ");
            }
        }
        checkButtons();
        if(blackJack.getMoney() <=0){
            blackJack.setMessage(blackJack.getMessage() + " \nGame over");
            if(blackJack.getHighScore() <blackJack.getNumberofRounds()) {
                blackJack.setHighScore(blackJack.getNumberofRounds());
                blackJack.setMessage(blackJack.getMessage() + "\nNew High Score!");
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
        Player player = blackJack.getPlayer();
        Player dealer = blackJack.getDealer();
        if(player.getSecondaryHand() ==null) handName = "";
        if(hand.maxTotal()>21){
            blackJack.setMessage(blackJack.getMessage() + handName + "You went bust, Dealer wins \n");
            calculateScore(-2);
        }
        else if(dealer.getPrimaryHand().maxTotal()> 21){
            blackJack.setMessage(blackJack.getMessage() + handName + "Dealer went bust, You win \n");
            calculateScore(2);
        }
        else if(hand.maxTotal() > dealer.getPrimaryHand().maxTotal()){
            blackJack.setMessage(blackJack.getMessage() + handName + "You win \n");
            calculateScore(2);
        }
        else if(hand.maxTotal() == dealer.getPrimaryHand().maxTotal()){
            blackJack.setMessage(blackJack.getMessage() + handName + "Tie \n");
            calculateScore(0);
        }
        else{ //dealer maxTotal > player maxTotal
            blackJack.setMessage(blackJack.getMessage() + handName + "Dealer wins \n");
            calculateScore(-2);
        }
    }

    public void calculateScore(int result){
        if(blackJack.isDoubledown()) blackJack.setMoney(blackJack.getMoney()+ 2*result);
        else if(blackJack.isSplit()) blackJack.setMoney(blackJack.getMoney()+ 0.5*result);
        else blackJack.setMoney(blackJack.getMoney()+ result);
    }

    /**
     * Draws a card from the deck and gives the card to the given hand
     * @param hand
     */
    private void drawCard(Hand hand){
        if(!blackJack.getDeck().isEmpty()) {
            hand.addCard(blackJack.getDeck().remove(0));
        }
        else{
            blackJack.setMessage(blackJack.getMessage() + " \nYou win");
            if(blackJack.getHighScore() <blackJack.getNumberofRounds()) {
                blackJack.setHighScore(blackJack.getNumberofRounds());
                blackJack.setMessage(blackJack.getMessage() + "\nNew High Score!");
            }
            gameScreen.getButtonRenderer().getButtonMap().get("newhand").setVisible(false);
        }
    }

    /**
     * Starts a new game with a new deck
     */
    public void newGame(){
        createDeck();
        blackJack.setMoney(blackJack.getStartingMoney());
        blackJack.setNumberofRounds(0);
        newHand();
    }

    /**
     * Creates a new hand with the current deck
     */
    public void newHand(){
        blackJack.setPlayer(new Player());
        blackJack.setDealer(new Player());
        blackJack.setSplit(false);
        blackJack.setDoubledown(false);
        deal();
    }

    /**
     * Deals the cards out to the player and the dealer
     * Checks for BlackJack
     */
    public void deal() {
        blackJack.setNumberofRounds(blackJack.getNumberofRounds() + 1);
        setAllowPlayerOptions(true);
        for(int i =0; i< 2; i++){
            drawCard(blackJack.getPlayer().getPrimaryHand());
            drawCard(blackJack.getDealer().getPrimaryHand());
        }
//        player.getPrimaryHand().addCard(new Card(Suit.CLUBS,Rank.ACE));
//        player.getPrimaryHand().addCard(new Card(Suit.DIAMONDS,Rank.ACE));
        blackJack.setCurrentHand(blackJack.getPlayer().getPrimaryHand());
        if(blackJack.getCurrentHand().maxTotal() == 21){
            stand();
            blackJack.setMessage("You got BlackJack. You win");
            blackJack.setMoney(blackJack.getMoney()+1);
        }
        checkButtons();
    }

    /**
     * Creates a new Deck and shuffles it
     */
    public void createDeck() {
        blackJack.setDeck(new ArrayList<Card>());
        for(int num = 0; num <blackJack.getNumberOfDecks(); num++){
            for(Card.Rank rank : Card.Rank.values()){
                for(Card.Suit suit: Card.Suit.values()){
                    blackJack.getDeck().add(new Card(suit, rank));
                }
            }
        }
        Collections.shuffle(blackJack.getDeck());
    }

    /**
     * Sets allowPlayerOptions to true or false
     * if true hides dealer card and show all buttons
     * if false shows dealer card and hides all buttons except for new game
     * @param allowPlayerOptions boolean to check if the game is over
     */
    public void setAllowPlayerOptions(boolean allowPlayerOptions) {
        blackJack.setAllowPlayerOptions(allowPlayerOptions);
        Map<String,Button> buttonMap = gameScreen.getButtonRenderer().getButtonMap();
        if(!allowPlayerOptions){//finish game
            //hide all buttons except for newgame and newhand and back arrow
            blackJack.setShowDealerCard(true);
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
            blackJack.setShowDealerCard(false);
            blackJack.setMessage("");
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
        Player player = blackJack.getPlayer();
        Card firstCard = player.getPrimaryHand().getCardList().get(0);
        Card secondCard = player.getPrimaryHand().getCardList().get(1);
        int handSize = player.getPrimaryHand().getCardList().size();
        if(blackJack.isAllowPlayerOptions() && firstCard.getRank() == secondCard.getRank() && handSize ==2 &&!blackJack.isSplit())
            return true;
        return false;
    }
    public boolean canDoubleDown(){
        return blackJack.isAllowPlayerOptions() && blackJack.getCurrentHand().getCardList().size() == 2 && blackJack.getMoney()>=4;
    }
}
package com.mygdx.game;

public class BlackJackController {
    private GameScreen gameScreen;

    public BlackJackController(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public void parseButton(String buttonName) {
        BlackJack blackJack = gameScreen.getBlackJack();
        if(buttonName.equals("hit"))
            blackJack.hit();
        else if(buttonName.equals("split"))
            blackJack.split();
        else if(buttonName.equals("stand"))
            blackJack.stand();
        else if(buttonName.equals("doubledown"))
            blackJack.doubleDown();
        else if(buttonName.equals("newgame"))
            blackJack.newGame();
        else if(buttonName.equals("newhand"))
            blackJack.newHand();
        else if(buttonName.equals("arrow"))
            gameScreen.getMain().setScreenMainMenu();
    }


}

package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;


/**
 * Created by Chris on 13/02/2015.
 */
public class Main extends Game {
    private MainMenu mainMenu ;
    private GameScreen gameScreen ;

    @Override
    public void create() {
        mainMenu = new MainMenu(this);
        gameScreen = new GameScreen(this);
        setScreen(mainMenu);
    }

    public void setScreenGameScreen(){
        setScreen(gameScreen);
    }

    public void setScreenMainMenu(){
        setScreen(mainMenu);
    }
}
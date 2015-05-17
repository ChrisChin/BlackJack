package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class RulesScreen implements Screen {
    private Main main;
    private String message =
            "BlackJack follows the traditional Rules of blackjack \n \n"+
            "Hit - gives the player a new card\n \n" +
            "Split - splits a pair of cards of the same rank into two hands (does not include resplits)\n \n"+
            "Double Down - allows the user to pick only 1 card and ends their turn. Allows the player to " +
                "double down after a split\n \n"+
            "Stand - ends the players turn\n \n"+
            "New Game - Creates a new game with a new deck\n \n"+
            "The deck has 6 sets of cards. The game automatically checks for bust and "+
            "stands if the total is 21 or is blackjack";
    private final int width = Gdx.graphics.getWidth();
    private final int height = Gdx.graphics.getHeight();
    private Stage stage;
    private Button button;

    public RulesScreen(Main main){
        this.main = main;
        stage = new Stage();
        stage.clear();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        button = new MyButton("arrow");
        button.setPosition(width - button.getWidth(), height - button.getHeight());
        button.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                main.setScreenMainMenu();
            }
        });
        stage.addActor(button);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        BitmapFont font = new BitmapFont();
        SpriteBatch spriteBatch = new SpriteBatch();
        stage.act();
        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
        spriteBatch.begin();

        //header
        float x = width/20;
        float fontWidth = button.getX() - x;
        String header = "BlackJack Rules";
        font.setColor(Color.WHITE);
        font.setScale(button.getHeight()/32);
        font.drawWrapped(spriteBatch,header,x,height*19/20 ,fontWidth);

        //rules
        float y = button.getY()- height/20;
        font.setScale(font.getScaleY()/2);
        font.drawWrapped(spriteBatch,message,x, y,width*18/20);

        spriteBatch.end();
        font.dispose();
        spriteBatch.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

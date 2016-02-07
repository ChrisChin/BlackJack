package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

/**
 * Created by Chris on 8/02/2016.
 */
public abstract class InformationScreen {
    private final int width = Gdx.graphics.getWidth();
    private final int height = Gdx.graphics.getHeight();
    private Main main;
    private Stage stage;
    private Button button;
    private String header;
    private String message;

    public InformationScreen(Main main){
        this.main = main;
        stage = new Stage();
        stage.clear();

    }
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

    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpriteBatch spriteBatch = new SpriteBatch();
        BitmapFont font = new BitmapFont();
        stage.act();
        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
        spriteBatch.begin();

        //header
        float x = width/20;
        float fontWidth = button.getX() - x;
        font.setColor(Color.WHITE);
        font.setScale(button.getHeight()/32);
        font.drawWrapped(spriteBatch,header,x,height*19/20 ,fontWidth);

        //rules
        float y = button.getY()- height/20;
        font.setScale(font.getScaleY()/2);
        font.drawWrapped(spriteBatch,message,x, y,width*18/20);
        spriteBatch.end();
        spriteBatch.dispose();
        font.dispose();
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

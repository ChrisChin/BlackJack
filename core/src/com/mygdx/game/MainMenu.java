package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.HashMap;
import java.util.Map;

public class MainMenu implements Screen {
    private Stage stage;
    private Skin buttonSkin;
    private Map<String,Button> buttonMap;
    private int width;
    private int height;
    private String[] buttonNames = {"rules","about","startgame"};
    private Main main;

    public MainMenu(Main main) {
        this.main = main;
    }

    public void setupButton(final String  buttonName,float y){
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = buttonSkin.getDrawable(buttonName);
        style.down = buttonSkin.getDrawable(buttonName);
        Button button = new Button(style);
        button.setWidth(width / 2);
        button.setHeight(button.getWidth()/3);
        button.setPosition((float)width / 2 - button.getWidth() / 2, y);
        button.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                parseButton(buttonName);
            }
        });
        buttonMap.put(buttonName, button);
        stage.addActor(button);
    }

    private void parseButton(String buttonName) {
        if(buttonName.equals("rules")){
            main.setScreen(new RulesScreen(main));
        }
        else if(buttonName.equals("about")){
            main.setScreen(new AboutScreen(main));
        }
        else{
            main.setScreenGameScreen();
        }
    }

    @Override
    public void show() {
        buttonMap = new HashMap<String, Button>();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        TextureAtlas buttonsAtlas = new TextureAtlas("images/mainmenu/mainmenu.atlas");
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);
        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        float y = 0;
        for(String buttonName: buttonNames){
            y += (float) height/(buttonNames.length + 1);
            setupButton(buttonName,y);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpriteBatch batch = new SpriteBatch();
        stage.act();
        batch.begin();
        stage.draw();
        batch.end();
        batch.dispose();
    }

    @Override  public void resize(int width, int height) {}
    @Override  public void pause(){}
    @Override  public void resume(){}
    @Override  public void hide(){}
    @Override  public void dispose(){}
}
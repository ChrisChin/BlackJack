package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import java.util.HashMap;
import java.util.Map;

public class ButtonRenderer {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;
    private Button button;
    private Map<String,Button> buttonMap;
    private int width = Gdx.graphics.getWidth();
    private int height = Gdx.graphics.getHeight();
    private float xOffset = (float)width / CAMERA_WIDTH;
    private float yOffset = (float)height / CAMERA_HEIGHT;
    private static final float CAMERA_WIDTH = 20f;
    private static final float CAMERA_HEIGHT = 20f;
    private String[] buttonNames = {"hit","split","stand","doubledown","newhand","newgame","arrow"};
    private GameScreen gameScreen;

    public ButtonRenderer(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        buttonMap = new HashMap<String, Button>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        float x = xOffset;
        for(String button: buttonNames){
            setupButton(button,x);
            float buttonWidth = (float) width/6;
            x += buttonWidth + buttonWidth/(buttonNames.length * 3 / 2);
        }
    }

    private void setupButton(final String  buttonName,float x){
        button = new MyButton(buttonName);
        if(buttonName.equals("arrow"))
            button.setPosition(width - button.getWidth(), height - button.getHeight());
        else if(buttonName.equals("newgame"))
            button.setPosition(width - button.getWidth(), height - 2*button.getHeight());
        else
            button.setPosition(x, yOffset + height/24);
        button.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.getBlackJackController().parseButton(buttonName);
            }
        });
        buttonMap.put(buttonName, button);
        getStage().addActor(button);
    }

    public void render() {
        batch = new SpriteBatch();
        stage.act();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stage.draw();
        batch.end();
        batch.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Map<String, Button> getButtonMap() {
        return buttonMap;
    }
}

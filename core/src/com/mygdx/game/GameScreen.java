package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Json;

public class GameScreen implements Screen{
    private BlackJack blackJack;
    private BlackJackController blackJackController;
    private Renderer renderer;
    private ButtonRenderer buttonRenderer;
    private Main main;

    // * Screen methods ***************************//

    public GameScreen(Main main){
        this.main = main;

    }

    @Override
    public void show() {
        blackJackController = new BlackJackController(this);
        buttonRenderer = new ButtonRenderer(this);
        blackJack = new BlackJack(this);
        renderer = new Renderer(this,blackJack);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        buttonRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.setSize(width,height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    public BlackJack getBlackJack() {
        return blackJack;
    }

    public BlackJackController getBlackJackController() {
        return blackJackController;
    }

    public ButtonRenderer getButtonRenderer() {
        return buttonRenderer;
    }

    public Main getMain() {
        return main;
    }
}

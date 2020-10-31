package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen{

    //region global variables
    private BlackJack blackJack;
    private BlackJackController blackJackController;
    private Renderer renderer;
    private ButtonRenderer buttonRenderer;
    private Main main;
    //endregion

    //region constructor
    public GameScreen(Main main){
        this.main = main;
    }
    //endregion

    //region getters
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
    //endregion

    //region public overrides
    @Override
    public void show() {
        buttonRenderer = new ButtonRenderer(this);
        blackJack = new BlackJack(this);
        blackJackController = new BlackJackController(this,blackJack);
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
        //TODO work out what to pause here for running the background
    }

    @Override
    public void resume() {
        //TODO work out what we need to resume after a pause
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }
    //endregion
}
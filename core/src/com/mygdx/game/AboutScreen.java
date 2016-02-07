package com.mygdx.game;

import com.badlogic.gdx.Screen;

/**
 * @author Chris
 * Screen which shows the information about the game
 */
public class AboutScreen extends InformationScreen implements Screen {

    public AboutScreen(Main main){
        super(main);
        setMessage("Made by Chris Chin");
        setHeader("About");
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
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

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;

public class RulesScreen extends InformationScreen implements Screen {

    public RulesScreen(Main main){
        super(main);
        setHeader("BlackJack Rules");
    }

    @Override
    public void show() {
        super.show();
        FileHandle file = Gdx.files.internal("data/Rules.txt");
        setMessage(file.readString());
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

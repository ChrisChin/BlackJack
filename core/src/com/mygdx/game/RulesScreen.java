package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class RulesScreen extends InformationScreen {

    public RulesScreen(Main main) {
        super(main);
        setHeader("BlackJack Rules");
    }

    @Override
    public void show() {
        super.show();
        FileHandle file = Gdx.files.internal("data/Rules.txt");
        setContent(file.readString());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 20/02/2015.
 */
public class MyButton extends Button{
    private TextureAtlas buttonsAtlas;
    private Skin buttonSkin;
    private int width = Gdx.graphics.getWidth();

    public MyButton(String buttonName){
        buttonsAtlas = new TextureAtlas("images/textures/textures.atlas");
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = buttonSkin.getDrawable(buttonName);
        style.down = buttonSkin.getDrawable(buttonName);
        setStyle(style);
        setHeight(width / 7);
        setWidth(getHeight());
    }








}

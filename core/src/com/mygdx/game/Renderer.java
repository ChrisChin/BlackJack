package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.HashMap;
import java.util.Map;

public class Renderer {
    private final int width = Gdx.graphics.getWidth();
    private final int height = Gdx.graphics.getHeight();
    private Map<String,TextureRegion> textureRegionMap;
    private float xOffset;	// pixels per unit on the X axis
    private float yOffset;	// pixels per unit on the Y axis
    private float textureWidth; // width of the texture of a card
    private float textureHeight; // height of the texture of a card
    private final float cardHeightMultiplier = 1980*3;
    private final float cardWidthMultiplier = 1080*3;
    private static final float CAMERA_WIDTH = 20f;
    private static final float CAMERA_HEIGHT = 20f;
    private OrthographicCamera cam;
    private GameScreen gameScreen;
    private BlackJack blackJack;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private BitmapFont headerFont;
    private enum buttonNames {hit,split,stand,doubledown,newgame};

    public Renderer(GameScreen gameScreen,BlackJack blackJack){
        this.gameScreen = gameScreen;
        this.blackJack = blackJack;
        this.textureRegionMap = new HashMap<String,TextureRegion>();
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        this.cam.update();
        loadTextures();
    }

    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.atlas"));
        for(Card card: blackJack.getDeck()) {
            String imageName = card.getImageName();
            textureRegionMap.put(imageName, atlas.findRegion(imageName));
        }
        textureRegionMap.put("bp", atlas.findRegion("bp")); // blue portrait card

        textureWidth = (float) atlas.findRegion("bp").getRegionWidth()/width *cardWidthMultiplier; // Sets the texture width - all cards are the same size
        textureHeight = (float) atlas.findRegion("bp").getRegionHeight()/height*cardHeightMultiplier; //sets the texture height -  all cards are the same size

        for(buttonNames button: buttonNames.values()){
            textureRegionMap.put(button.toString(), atlas.findRegion(button.toString())); // hit Button
        }
    }

    public void render(){
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setScale(4);
        spriteBatch.begin();
        drawCards();
        drawHeader();
        if(!blackJack.isAllowPlayerOptions())
            renderMessage();

        spriteBatch.end();
        font.dispose();
        headerFont.dispose();
        spriteBatch.dispose();
    }

    private void drawCards() {
        Player player = blackJack.getPlayer();
        Player dealer = blackJack.getDealer();
        float offset = 0;
        float y = 0;
        Map<String,Button> buttonMap = gameScreen.getButtonRenderer().getButtonMap();
        float gamewindowHeight = buttonMap.get("arrow").getY();// height - header

        //Draw the Player's hand at the bottom of the screen
        for (Card card : player.getPrimaryHand().getCardList()) {
            y = yOffset + gamewindowHeight* 2 / 4; //draws at the 2nd quarter
            drawCard(textureRegionMap.get(card.getImageName()),y,offset,card.isDoubleDown());
            offset += textureWidth / 6;
        }
        int maxTotal = player.getPrimaryHand().maxTotal();
        int minTotal = player.getPrimaryHand().minTotal();
        String message = "Player Total: " + maxTotal;
        if(maxTotal != minTotal) message = message + " / " + minTotal;
        if(blackJack.getCurrentHand().equals(player.getPrimaryHand()))
            message = "Current Hand \n" + message;
        drawTotal(message,y,offset);

        offset = 0;
        if(player.getSecondaryHand() !=null){
            for (Card card : player.getSecondaryHand().getCardList()) {
                y = yOffset + gamewindowHeight / 4; //draws at the 1st quarter
                drawCard(textureRegionMap.get(card.getImageName()),y,offset,card.isDoubleDown());
                offset += textureWidth / 6;
            }
            maxTotal = player.getSecondaryHand().maxTotal();
            minTotal = player.getSecondaryHand().minTotal();
            message = "Player Total: " + maxTotal;
            if(maxTotal != minTotal) message = message + " / " + minTotal;
            if(blackJack.getCurrentHand().equals(player.getSecondaryHand()))
                message = "Current Hand \n" + message;
            drawTotal(message,y,offset);
        }

        //Draw the Dealer's hand at the top of the screen
        offset = 0;
        for (Card card : dealer.getPrimaryHand().getCardList()) {
            y = yOffset + gamewindowHeight* 3 / 4;    //draws at 3rd quarter

            //First card is faced down so textureRegion is a blue portrait card
            if(offset==0 && !blackJack.isShowDealerCard()){
                drawCard(textureRegionMap.get("bp"),y,offset,false);
            }
            else{ //Rest of the cards are faced up
                drawCard(textureRegionMap.get(card.getImageName()),y,offset,false);
            }
            offset += textureWidth / 6;
        }
        if(blackJack.isShowDealerCard())
            drawTotal("Dealer Total: " + dealer.getPrimaryHand().maxTotal(),y,offset);
    }

    /**
     * Helper method to draw the dealer/player totals
     * @param text Text to display
     * @param y y coordinate
     * @param offset - card offset
     */
    private void drawTotal(String text,float y,float offset){
        float textWidth = width - 1 * xOffset - offset - textureWidth; // the width is the empty space that is not cards
        float textX = width - textWidth - xOffset;
        float textY = y + textureHeight/2;
        font.setColor(Color.RED);
        font.drawWrapped(spriteBatch,text,textX ,textY ,textWidth, BitmapFont.HAlignment.RIGHT);
    }

    private void drawCard(TextureRegion textureRegion, float y, float offset, boolean isDoubleDown){
        float x = 1 * xOffset + offset;
        if(isDoubleDown)
            spriteBatch.draw(textureRegion,x + xOffset,y,textureWidth/2,textureHeight/2,textureWidth,textureHeight,1,1,90f);
        else
            spriteBatch.draw(textureRegion,x,y,textureWidth,textureHeight);
    }

    private void renderMessage() {
        String message = blackJack.getMessage();
        Map<String,Button> buttonMap = gameScreen.getButtonRenderer().getButtonMap();
        float textWidth = buttonMap.get("newhand").getX() - buttonMap.get("newhand").getWidth();
        font.setColor(Color.RED);
        font.drawWrapped(spriteBatch, message, xOffset, yOffset + height/9,textWidth, BitmapFont.HAlignment.LEFT);
    }

    private void drawHeader(){
        headerFont = new BitmapFont();
        headerFont.setColor(Color.RED);
        headerFont.setScale(2);
        String money = "Chips: $" + blackJack.getMoney();
        String numberOfRounds = "Number of Rounds: " + blackJack.getNumberofRounds();
        String highscore = "HighScore: " + blackJack.getHighScore();
        Map<String,Button> buttonMap = gameScreen.getButtonRenderer().getButtonMap();
        float headerWidth = buttonMap.get("arrow").getX();
        headerFont.drawWrapped(spriteBatch,money , xOffset, height - yOffset,headerWidth/3, BitmapFont.HAlignment.LEFT);
        headerFont.drawWrapped(spriteBatch,numberOfRounds , xOffset + headerWidth/3, height - yOffset,headerWidth/3, BitmapFont.HAlignment.LEFT);
        headerFont.drawWrapped(spriteBatch,highscore , xOffset + headerWidth*2/3, height - yOffset,headerWidth/3, BitmapFont.HAlignment.LEFT);
    }

    public void setSize(int width, int height) {
        xOffset = (float)width / CAMERA_WIDTH;
        yOffset = (float)height / CAMERA_HEIGHT;
    }
}
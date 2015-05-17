package com.mygdx.game;

public class Player {
    private Hand primaryHand;
    private Hand secondaryHand;

    public Player(){
        this.setPrimaryHand(new Hand());
    }

    public Hand getPrimaryHand() {
        return primaryHand;
    }

    public void setPrimaryHand(Hand primaryHand) {
        this.primaryHand = primaryHand;
    }

    public Hand getSecondaryHand() {
        return secondaryHand;
    }

    public void setSecondaryHand(Hand secondaryHand) {
        this.secondaryHand = secondaryHand;
    }
}

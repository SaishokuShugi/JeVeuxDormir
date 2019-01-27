package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class Collectible extends Interactible {

    float froidFactor;
    private float staminaFactor;

    public Collectible(String image, int frame_cols, int frame_rows, int nbFrames, float x, float y, float friction, float density, float restitution, boolean sensor, float sf, float ff) {
        super(image, frame_cols, frame_rows, nbFrames, BodyDef.BodyType.DynamicBody, x, y, friction, density, restitution, sensor);
        this.staminaFactor = sf;
        this.froidFactor = ff;
    }

    public void action(Personnage perso) {
        perso.setStamina(perso.getStamina() + this.staminaFactor);
        perso.setFroid(perso.getFroid() + this.froidFactor);
    }
}

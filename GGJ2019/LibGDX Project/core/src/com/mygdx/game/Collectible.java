package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class Collectible extends Interactible {

    public Collectible(String image, int frame_cols, int frame_rows, int nbFrames, float x, float y, float friction, float density, float restitution, boolean sensor) {
        super(image, frame_cols, frame_rows, nbFrames, BodyDef.BodyType.DynamicBody, x, y, friction, density, restitution, sensor);
    }

    public void action(Personnage perso, float staminaFactor, float froidFactor) {
        perso.setStamina(perso.getStamina() + staminaFactor);
        perso.setFroid(perso.getFroid() + froidFactor);
    }
}

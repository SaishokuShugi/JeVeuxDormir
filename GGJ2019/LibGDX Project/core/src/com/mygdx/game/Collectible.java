package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class Collectible extends Interactible {

    public Collectible(String image, int frame_cols, int frame_rows, float x, float y, float friction, float density, float restitution) {
        super(image, frame_cols, frame_rows, BodyDef.BodyType.DynamicBody, x, y, friction, density, restitution);
    }

    public void action(Personnage perso, float staminaFactor, float froidFactor) {
        perso.setStamina(perso.getStamina() + staminaFactor);
        perso.setFroid(perso.getFroid() + froidFactor);
    }
}

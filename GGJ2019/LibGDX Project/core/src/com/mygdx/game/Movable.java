package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class Movable extends Interactible {

    public Movable(String image, int frame_cols, int frame_rows, int nbFrames, float x, float y, float friction, float density, float restitution) {
        super(image, frame_cols, frame_rows, nbFrames, BodyDef.BodyType.DynamicBody, x, y, friction, density, restitution);
    }

    public void action(Personnage perso, float factor) {
        perso.setStamina(perso.getStamina() - factor);
    }
}

package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class Block extends Interactible {

    public Block(String image, int frame_cols, int frame_rows, int nbFrames, float x, float y, float friction, float density, float restitution, boolean sensor) {
        super(image, frame_cols, frame_rows, nbFrames, BodyDef.BodyType.StaticBody, x, y, friction, density, restitution, sensor);
    }

    public void action() {

    }
}

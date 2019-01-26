package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class Chat extends Interactible {

    public Chat(String image, int frame_cols, int frame_rows, float x, float y, float friction, float density, float restitution) {
        super(image, frame_cols, frame_rows, BodyDef.BodyType.KinematicBody, x, y, friction, density, restitution);
    }

    @Override
    public void action() {

    }

    public void spawnTombant() {

    }
}

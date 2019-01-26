package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class Movable extends Interactible {

    public Movable(String image, int frame_cols, int frame_rows, int x, int y, float friction, float density, float restitution) {
        super(image, frame_cols, frame_rows, BodyDef.BodyType.DynamicBody, x, y, friction, density, restitution);
    }

    @Override
    public void action() {

    }
}

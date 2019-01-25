package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class Block extends Interactible {

    public Block(String image, int frame_cols, int frame_rows, int x, int y, float density) {
        super(image, frame_cols, frame_rows, BodyDef.BodyType.StaticBody, x, y, 0f, density, 0f);
    }

    @Override
    public void action() {
    }
}

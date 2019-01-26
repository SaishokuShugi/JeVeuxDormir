package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class CollectStatic extends Interactible {

    public CollectStatic(String image, int frame_cols, int frame_rows, int x, int y, float friction, float density, float restitution, float echelle) {
        super(image, frame_cols, frame_rows, BodyDef.BodyType.StaticBody, x, y, friction, density, restitution, echelle);
    }

    @Override
    public void action() {

    }
}

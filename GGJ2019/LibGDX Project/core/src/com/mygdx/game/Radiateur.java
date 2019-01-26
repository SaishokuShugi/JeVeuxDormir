package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Radiateur extends Block {

    public float range;

    public Radiateur(String image, int frame_cols, int frame_rows, float x, float y, float friction, float density, float restitution, float range) {
        super(image, frame_cols, frame_rows, x, y, friction, density, restitution);
        this.range = range * (32 * GameScreen.scale_factor);
    }

    public void enable() {
        CircleShape cir = new CircleShape();
        cir.setRadius(range);

        Fixture fixture = this.getBody().createFixture(cir, 0f);
    }

    public void inRange(Personnage perso, float factor) {
        perso.setFroid(perso.getFroid() - factor);
    }
}

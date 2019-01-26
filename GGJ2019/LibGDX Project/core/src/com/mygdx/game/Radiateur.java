package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Radiateur extends Block {

    public float range;

    public Radiateur(String image, int frame_cols, int frame_rows, int nbFrames, float x, float y, float friction, float density, float restitution, float range, boolean sensor) {
        super(image, frame_cols, frame_rows, nbFrames, x, y, friction, density, restitution, sensor);
        this.range = range * (32 * GameScreen.scale_factor);
    }

    public void enable() {
        CircleShape cir = new CircleShape();
        cir.setRadius(range);
        FixtureDef fd = new FixtureDef();
        fd.shape = cir;
        fd.density = 0;
        fd.friction = 0;
        fd.restitution = 0;
        fd.isSensor = true;
        this.getBody().createFixture(fd);
    }

    public void inRange(Personnage perso, float factor) {
        perso.setFroid(perso.getFroid() - factor);
    }
}

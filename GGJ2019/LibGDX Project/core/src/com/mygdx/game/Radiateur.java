package com.mygdx.game;

public class Radiateur extends Block {

    public float range;

    public Radiateur(String image, int frame_cols, int frame_rows, float x, float y, float friction, float density, float restitution, float range) {
        super(image, frame_cols, frame_rows, x, y, friction, density, restitution);
        this.range = range * (32 * GameScreen.scale_factor);
    }

    @Override
    public void action(Personnage perso) {
    }
}

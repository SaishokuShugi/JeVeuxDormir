package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Radiateur extends Block {

    public float range;
    Animation<TextureRegion> anim;
    private float animTime=0;

    public Radiateur(String image, int frame_cols, int frame_rows, int nbFrames, float x, float y, float friction, float density, float restitution, boolean sensor, float range) {
        super(image, frame_cols, frame_rows, nbFrames, x, y, friction, density, restitution, sensor);
        this.range = range;
        anim=Personnage.loadAnim(image, frame_cols, frame_rows, nbFrames, 0.5f);
    }
    public void setAnimTime(float animTime) {
        this.animTime = animTime;
    }
    public TextureRegion getCurrentFrame() {
        return this.anim.getKeyFrame(this.animTime, true);
    }
    public void action(Personnage perso, float factor) {

        int forme = this.getFixture().getShape().getClass().hashCode();
        if (forme == CircleShape.class.hashCode()) {
            if (perso.getFroid() < perso.getFroidMax())
                perso.setFroid(perso.getFroid() + factor);
        } else {
            CircleShape cir = new CircleShape();
            cir.setRadius(this.range);
            FixtureDef fd = new FixtureDef();
            fd.shape = cir;
            fd.density = 0;
            fd.friction = 0;
            fd.restitution = 0;
            fd.isSensor = true;
            this.getBody().destroyFixture(this.getFixture());
            this.getBody().createFixture(fd);
        }

    }
}

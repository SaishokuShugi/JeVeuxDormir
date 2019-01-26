package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Personnage {
    private float staminaMax;
    private float froidMax;
    private float stamina;
    private float froid;
    private float friction;
    private float density;
    private float restitution;
    private Body body;
    private Fixture fixture;

    public Personnage(float staminaMax, float froidMax, float x, float y, float friction, float density, float restitution) {
        this.staminaMax = staminaMax;
        this.stamina = staminaMax;
        this.froidMax = froidMax;
        this.froid = froidMax;
        this.friction = friction;
        this.density = density;
        this.restitution = restitution;

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x * (32 * GameScreen.scale_factor), y * (32 * GameScreen.scale_factor));

        this.body = GameScreen.world.createBody(bd);
    }

    public float getStamina() {
        return stamina;
    }

    public void setStamina(float stamina) {
        this.stamina = stamina;
    }

    public float getFroid() {
        return froid;
    }

    public void setFroid(float froid) {
        this.froid = froid;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public Animation<TextureRegion> loadAnim(String image, int frame_cols, int frame_rows, float frameDuration) {
        Texture sheet = new Texture(image);
        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth() / frame_cols,
                sheet.getHeight() / frame_rows);
        TextureRegion[] frames = new TextureRegion[frame_cols * frame_rows];
        int index = 0;
        for (int i = 0; i < frame_rows; i++) {
            for (int j = 0; j < frame_cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        return new Animation<TextureRegion>(frameDuration, frames);
    }

    public void createBodyFromAnim(Animation<TextureRegion> anim) {
        TextureRegion[] frames = anim.getKeyFrames();

        PolygonShape box = new PolygonShape();
        TextureRegion img = frames[0];
        box.setAsBox(img.getRegionWidth() / 2 * GameScreen.scale_factor, img.getRegionHeight() / 2 * GameScreen.scale_factor);

        if (this.friction == 0 && this.restitution == 0) {
            this.body.createFixture(box, this.density);
        } else {
            FixtureDef fd = new FixtureDef();
            fd.shape = box;
            fd.density = this.density;
            fd.friction = this.friction;
            fd.restitution = this.restitution;

            this.fixture = this.body.createFixture(fd);
        }
    }
}
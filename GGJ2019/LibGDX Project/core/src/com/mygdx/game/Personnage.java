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
    private Body body;
    private Fixture fixture;
    private Animation<TextureRegion> anim;
    private float animTime;

    public Personnage(float staminaMax, float froidMax, float x, float y, float friction, float density, float restitution, float frameDuration) {
        this.staminaMax = staminaMax;
        this.stamina = staminaMax;
        this.froidMax = froidMax;
        this.froid = froidMax;

        this.anim = loadAnim("Personnage/idle.png", 4, 3, frameDuration);

        TextureRegion img = this.anim.getKeyFrame(0f, true);

        PolygonShape box = new PolygonShape();
        box.setAsBox(img.getRegionWidth() / 2 * GameScreen.scale_factor, img.getRegionHeight() / 2 * GameScreen.scale_factor);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        x = x * 32 + img.getRegionWidth() / 2;
        y = y * 32 + img.getRegionHeight() / 2;
        bd.position.set(x * GameScreen.scale_factor, y * GameScreen.scale_factor);

        this.body = GameScreen.world.createBody(bd);

        FixtureDef fd = new FixtureDef();
        fd.shape = box;
        fd.density = density;
        fd.friction = friction;
        fd.restitution = restitution;

        this.fixture = this.body.createFixture(fd);
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

    public float getStaminaMax() {
        return staminaMax;
    }

    public void setStaminaMax(float staminaMax) {
        this.staminaMax = staminaMax;
    }

    public float getFroidMax() {
        return froidMax;
    }

    public void setFroidMax(float froidMax) {
        this.froidMax = froidMax;
    }

    public Animation<TextureRegion> getAnim() {
        return anim;
    }

    public void setAnim(Animation<TextureRegion> anim) {
        this.anim = anim;
    }

    public float getAnimTime() {
        return animTime;
    }

    public void setAnimTime(float animTime) {
        this.animTime = animTime;
    }

    public TextureRegion getCurrentFrame() {
        return this.anim.getKeyFrame(this.animTime, true);
    }

    public float getBodyXToImage() {
        return this.body.getPosition().x - getCurrentFrame().getRegionWidth() / 2 * GameScreen.scale_factor;
    }

    public float getBodyYToImage() {
        return this.body.getPosition().y - getCurrentFrame().getRegionHeight() / 2 * GameScreen.scale_factor;
    }

    public static Animation<TextureRegion> loadAnim(String image, int frame_cols, int frame_rows, float frameDuration) {
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

    public void changeAnim(String image, int frame_cols, int frame_rows, float frameDuration) {
        this.animTime = 0;
        this.anim = loadAnim(image, frame_cols, frame_rows, frameDuration);
    }

    public void run(float frameDuration) {
        changeAnim("Personnage/run.png", 4, 2, frameDuration);
    }

    public void idle(float frameDuration) {
        changeAnim("Personnage/idle.png", 4, 3, frameDuration);
    }

    public void jump(float frameDuration) {
        changeAnim("Personnage/jump2.png", 1, 1, frameDuration);
    }

    public void land(float frameDuration) {
        changeAnim("Personnage/landing.png", 1, 1, frameDuration);
    }

    public void ledge(float frameDuration) {
        changeAnim("Personnage/ledge grab.png", 3, 2, frameDuration);
    }

    public void air(float frameDuration) {
        changeAnim("Personnage/mid air.png", 2, 1, frameDuration);
    }

}
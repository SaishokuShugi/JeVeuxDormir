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

    public Personnage(float staminaMax, float froidMax, float x, float y, float friction, float density, float restitution, Animation<TextureRegion> anim) {
        this.staminaMax = staminaMax;
        this.stamina = staminaMax;
        this.froidMax = froidMax;
        this.froid = froidMax;

        TextureRegion[] frames = anim.getKeyFrames();

        PolygonShape box = new PolygonShape();
        TextureRegion img = frames[0];
        box.setAsBox(img.getRegionWidth() / 2 * GameScreen.scale_factor, img.getRegionHeight() / 2 * GameScreen.scale_factor);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
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

    public void createBodyFromAnim(Animation<TextureRegion> anim) {
        TextureRegion[] frames = anim.getKeyFrames();

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        this.body.getPosition().x = this.body.getPosition().x;
        this.body.getPosition().y = this.body.getPosition().y;
        bd.position.set(this.body.getPosition().x * GameScreen.scale_factor, this.body.getPosition().y * GameScreen.scale_factor);

        GameScreen.world.destroyBody(this.body);
        this.body = GameScreen.world.createBody(bd);

        PolygonShape box = new PolygonShape();
        TextureRegion img = frames[0];
        box.setAsBox(img.getRegionWidth() / 2 * GameScreen.scale_factor, img.getRegionHeight() / 2 * GameScreen.scale_factor);

        FixtureDef fd = new FixtureDef();
        fd.shape = box;
        fd.density = this.fixture.getDensity();
        fd.friction = this.fixture.getFriction();
        fd.restitution = this.fixture.getRestitution();

        this.fixture = this.body.createFixture(fd);
    }
}
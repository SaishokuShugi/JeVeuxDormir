package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Interactible {
    private TextureRegion[] images;
    private Body body;
    private Fixture fixture;
    public int tile;
    Vector2 decal;

    public Interactible(String image, int frame_cols, int frame_rows, int nbFrames, BodyDef.BodyType bodyType, float x, float y, float friction, float density, float restitution, boolean sensor) {
        Texture animSheet = new Texture(image);
        TextureRegion[][] tmp = TextureRegion.split(animSheet,
                animSheet.getWidth() / frame_cols,
                animSheet.getHeight() / frame_rows);
        this.images = new TextureRegion[nbFrames];
        int index = 0;
        for (int i = 0; i < frame_rows; i++) {
            for (int j = 0; j < frame_cols; j++) {
                if (index < nbFrames) {
                    this.images[index++] = tmp[i][j];
                }
            }
        }
        tile = MyGdxGame.random.nextInt(frame_cols*frame_rows);
        
        
        
        
        
        PolygonShape box = new PolygonShape();
        TextureRegion img = this.images[0];
        decal = new Vector2(img.getRegionWidth() / 64f, img.getRegionHeight() / 64f);
        box.setAsBox(decal.x,decal.y);
        
        BodyDef bd = new BodyDef(); 
        bd.type = bodyType;
        bd.position.set(x+decal.x, y+decal.y);

        this.body = GameScreen.world.createBody(bd);
        this.body.setFixedRotation(true);
        FixtureDef fd = new FixtureDef();
        fd.shape = box;
        fd.density = density;
        fd.friction = friction;
        fd.restitution = restitution;
        fd.isSensor = sensor;

        this.fixture = this.body.createFixture(fd);
    }

    public TextureRegion[] getImages() {
        return images;
    }

    public void setImages(TextureRegion[] images) {
        this.images = images;
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

    public float getScaledImageWidth() {
        return this.images[0].getRegionWidth() * GameScreen.scale_factor;
    }

    public float getScaledImageHeight() {
        return this.images[0].getRegionHeight() * GameScreen.scale_factor;
    }

    public float getBodyXToImage() {
        return (this.body.getPosition().x-decal.x) *32 * GameScreen.scale_factor;
    }

    public float getBodyYToImage() {
        return (this.body.getPosition().y-decal.y)*32 * GameScreen.scale_factor;
    }
}

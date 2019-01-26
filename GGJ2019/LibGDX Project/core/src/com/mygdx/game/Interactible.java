package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Interactible {
    private TextureRegion[] images;
    private Body body;
    private Fixture fixture;

    public Interactible(String image, int frame_cols, int frame_rows, BodyDef.BodyType bodyType, float x, float y, float friction, float density, float restitution) {
        Texture animSheet = new Texture(image);
        TextureRegion[][] tmp = TextureRegion.split(animSheet,
                animSheet.getWidth() / frame_cols,
                animSheet.getHeight() / frame_rows);
        this.images = new TextureRegion[frame_cols * frame_rows];
        int index = 0;
        for (int i = 0; i < frame_rows; i++) {
            for (int j = 0; j < frame_cols; j++) {
                this.images[index++] = tmp[i][j];
            }
        }

        PolygonShape box = new PolygonShape();
        TextureRegion img = this.images[0];
        box.setAsBox(img.getRegionWidth() / 2 * GameScreen.scale_factor, img.getRegionHeight() / 2 * GameScreen.scale_factor);
        
        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        x = x * 32 + img.getRegionWidth() / 2;
        y = y * 32 + img.getRegionHeight() / 2;
        bd.position.set(x*GameScreen.scale_factor, y* GameScreen.scale_factor);

        this.body = GameScreen.world.createBody(bd);

        


        if (friction == 0 && restitution == 0) {
            body.createFixture(box, density);
        } else {
            FixtureDef fd = new FixtureDef();
            fd.shape = box;
            fd.density = density;
            fd.friction = friction;
            fd.restitution = restitution;

            this.fixture = body.createFixture(fd);
        }
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
        return this.body.getPosition().x - this.images[0].getRegionWidth() / 2 * GameScreen.scale_factor;
    }

    public float getBodyYToImage() {
        return this.body.getPosition().y - this.images[0].getRegionHeight() / 2 * GameScreen.scale_factor;
    }

    public abstract void action();
}

package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Interactible {
    private TextureRegion[] images;
    private Body body;
    private Fixture fixture;
    private float echelle;

    public Interactible(String image, int frame_cols, int frame_rows, BodyDef.BodyType bodyType, int x, int y, float friction, float density, float restitution, float echelle) {
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
        this.echelle = echelle;

        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.position.set(x * echelle, y * echelle);

        this.body = GameScreen.world.createBody(bd);

        PolygonShape box = new PolygonShape();
        Texture img = this.images[0].getTexture();
        box.setAsBox(img.getHeight()/2, img.getWidth()/2);


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

    public float getEchelle() {
        return echelle;
    }

    public void setEchelle(float echelle) {
        this.echelle = echelle;
    }

    public abstract void action();
}

package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Interactible {
    private TextureRegion[] images;
    private Body body;
    private Fixture fixture;

    public Interactible(String image, int frame_cols, int frame_rows, BodyDef.BodyType bodyType, int x, int y, float friction, float density, float restitution) {
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

        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.position.set(x, y);

        this.body = GameScreen.world.createBody(bd);

        PolygonShape box = new PolygonShape();
        TextureRegion img = this.images[0];
        box.setAsBox(img.getU2(), img.getV2());


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

    public abstract void action();
}

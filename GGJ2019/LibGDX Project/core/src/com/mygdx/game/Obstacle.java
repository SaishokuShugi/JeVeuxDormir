package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Obstacle {
    private Texture image;
    private Body body;

    public Obstacle(String image, Body body) {
        this.image = new Texture(image);
        this.body = body;
    }

    public Obstacle(String image, BodyDef.BodyType bodyType, int x, int y, PolygonShape shape, float friction, float density, float restitution) {
        this.image = new Texture(image);
        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.position.set(x, y);

        this.body = world.createBody(bd);

        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = density;
        fd.friction = friction;
        fd.restitution = restitution;

        Fixture fixture = body.createFixture(fd);
        circle.dispose();
    }
}

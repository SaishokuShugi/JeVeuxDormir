package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    private int flip = 1;
    Vector2 decal;

    public Personnage(float staminaMax, float froidMax, float x, float y, float friction, float density, float restitution, float frameDuration) {
        this.staminaMax = staminaMax;
        this.stamina = staminaMax;
        this.froidMax = froidMax;
        this.froid = froidMax;

        this.anim = loadAnim("Personnage/idle.png", 4, 3, 11, frameDuration);

        TextureRegion img = this.anim.getKeyFrame(0f, true);

        decal = new Vector2(img.getRegionWidth() / 64f, img.getRegionHeight() / 64f);

        PolygonShape box = new PolygonShape();
        box.setAsBox(decal.x, decal.y);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x + decal.x, y + decal.y);

        this.body = GameScreen.world.createBody(bd);
        //this.body.setGravityScale(0.1f);
        this.body.setFixedRotation(true);

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

    public static Animation<TextureRegion> loadAnim(String image, int frame_cols, int frame_rows, int nbFrames, float frameDuration) {
        Texture sheet = new Texture(image);
        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth() / frame_cols,
                sheet.getHeight() / frame_rows);
        TextureRegion[] frames = new TextureRegion[nbFrames];
        int index = 0;
        for (int i = 0; i < frame_rows; i++) {
            for (int j = 0; j < frame_cols; j++) {
                if (index < nbFrames) {
                    frames[index++] = tmp[i][j];
                }
            }
        }

        return new Animation<TextureRegion>(frameDuration, frames);
    }

    public float getBodyXToImage() {
        return (this.body.getPosition().x - decal.x) * 32 * GameScreen.scale_factor;
    }

    public float getBodyYToImage() {
        return (this.body.getPosition().y - decal.y) * 32 * GameScreen.scale_factor;
    }

    public void changeAnim(String image, int frame_cols, int frame_rows, int nbFrames, float frameDuration) {
        this.animTime = 0;
        this.anim = loadAnim(image, frame_cols, frame_rows, nbFrames, frameDuration);
    }

    public void run(float frameDuration, float xImpulse) {
        changeAnim("Personnage/run.png", 4, 2, 8, frameDuration);
        //this.body.applyLinearImpulse(0,-4f, this.body.getPosition().x, this.body.getPosition().y, false);
        this.body.setLinearVelocity(xImpulse, this.body.getLinearVelocity().y);
    }

    public void idle(float frameDuration) {
        changeAnim("Personnage/idle.png", 4, 3, 11, frameDuration);
    }

    public void jump(float frameDuration) {
        changeAnim("Personnage/jump2.png", 1, 1, 1, frameDuration);
        this.body.applyLinearImpulse(0, 15f, this.body.getPosition().x, this.body.getPosition().y, false);
    }


    public void land(float frameDuration) {
        changeAnim("Personnage/landing2.png", 1, 1, 1, frameDuration);
    }

    public void ledge(float frameDuration) {
        changeAnim("Personnage/ledgeGrab.png", 3, 2, 6, frameDuration);
    }

    public void air(float frameDuration) {
        changeAnim("Personnage/midAir.png", 2, 1, 2, frameDuration);
        this.body.setLinearVelocity(this.body.getLinearVelocity().x, -500f);
    }

    void controls() {
        //Update Stamina et Temp�rature
        this.stamina -= 0.001f;
        this.froid -= 0.001f;


        //Controls
        boolean isLeftPressed = Gdx.input.isKeyPressed(Input.Keys.Q);
        boolean isRightPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean isUpPressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        boolean isDownPressed = Gdx.input.isKeyPressed(Input.Keys.S);

        //System.out.println(this.body.getPosition());
        if (isUpPressed && (this.body.getLinearVelocity().y < 0.001f && this.body.getLinearVelocity().y > -0.001f)) {
            jump(.5f);
        }

        if (isDownPressed || this.body.getLinearVelocity().y < -10f) {
            air(.5f);
        }
        if (isRightPressed) {
            run(.2f, 2f);
            setFlip(1);
        } else {
            if (isLeftPressed) {
                run(.2f, -2f);
                setFlip(-1);
            } else {
                this.body.setLinearVelocity(0, this.body.getLinearVelocity().y);
            }
        }
        if (!(isRightPressed || isLeftPressed || !(this.body.getLinearVelocity().y < 0.001f && this.body.getLinearVelocity().y > -0.001f)))
            idle(.5f);

    }

    public int getFlip() {
        return flip;
    }

    public void setFlip(int flip) {
        this.flip = flip;
    }

    public void checkCollision(Interactible objet, float factor1, float factor2) {
        Fixture fix = objet.getFixture();

        boolean cond = fix.testPoint(this.body.getPosition().add(decal))
                || fix.testPoint(this.body.getPosition().sub(decal))
                || fix.testPoint(this.body.getPosition().add(decal.x, -decal.y))
                || fix.testPoint(this.body.getPosition().add(-decal.x, decal.y))
                || fix.testPoint(this.body.getPosition().add(0, -decal.y));

        if (cond) {
            if (objet.getClass().hashCode() == Block.class.hashCode()) {
                Block obj = (Block) objet;
                obj.action(this);
            }
            if (objet.getClass().hashCode() == Chat.class.hashCode()) {
                Chat obj = (Chat) objet;
                obj.action();
            }
            if (objet.getClass().hashCode() == Collectible.class.hashCode()) {
                Collectible obj = (Collectible) objet;
                obj.action(this, factor1, factor2);
            }
            if (objet.getClass().hashCode() == Movable.class.hashCode()) {
                Movable obj = (Movable) objet;
                obj.action(this, factor1);
            }
            if (objet.getClass().hashCode() == Radiateur.class.hashCode()) {
                Radiateur obj = (Radiateur) objet;
                obj.enable();
            }
            if (objet.getClass().hashCode() == Tombant.class.hashCode()) {
                Tombant obj = (Tombant) objet;
                obj.action(this, factor1);
            }
        }
    }
}
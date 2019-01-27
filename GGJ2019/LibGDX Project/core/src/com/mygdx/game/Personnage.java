package com.mygdx.game;

import javax.sound.midi.ControllerEventListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
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
    boolean isGrab;
    
    public boolean triggerReset=false;

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
        this.body.setLinearVelocity(xImpulse, this.body.getLinearVelocity().y);
    }

    public void idle(float frameDuration) {
        changeAnim("Personnage/idle.png", 4, 3, 11, frameDuration);
    }

    public void jump(float frameDuration) {
        changeAnim("Personnage/jump2.png", 1, 1, 1, frameDuration);
        this.body.applyLinearImpulse(0, 15f, this.body.getPosition().x, this.body.getPosition().y, false);
        isGrab=false;
    	this.body.setGravityScale(1);
    }


    public void land(float frameDuration) {
        changeAnim("Personnage/landing2.png", 1, 1, 1, frameDuration);
    }

    public void ledge(float frameDuration) {
        changeAnim("Personnage/ledgeGrab.png", 3, 2, 6, frameDuration);
    }

    public void air(float frameDuration) {
        changeAnim("Personnage/midAir.png", 2, 1, 2, frameDuration);
        this.body.setLinearVelocity(this.body.getLinearVelocity().x, -20f);
    }

    public void grab(float frameDuration) {
    	changeAnim("Personnage/ledgeGrab.png",3,2,6,frameDuration);
    	this.body.setLinearVelocity(0,0);
    	this.body.setGravityScale(0);
    }

    void controls(boolean canGrab) {
        //Update Stamina et Temp�rature
        this.stamina -= 0.001f;
        this.froid -= 0.004f;

        boolean isLeftPressed = Gdx.input.isKeyPressed(Input.Keys.Q);
        boolean isRightPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean isUpPressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        boolean isDownPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean isGrabPressed = Gdx.input.isKeyPressed(Input.Keys.Z);
        boolean isResetPressed = Gdx.input.isKeyPressed(Input.Keys.ESCAPE);
        		
        //Controls
        if(GameScreen.cont!=null) {
         isLeftPressed = isLeftPressed||GameScreen.cont.getAxis(1)<-.1;
         isRightPressed = isRightPressed||GameScreen.cont.getAxis(1)>.1;
         isUpPressed =isUpPressed||GameScreen.cont.getButton(0);
         isDownPressed = isDownPressed||GameScreen.cont.getButton(1);
         isGrabPressed = isGrabPressed||GameScreen.cont.getAxis(4)<-.1;
         isResetPressed=isResetPressed||GameScreen.cont.getButton(3);
        }

        triggerReset=isResetPressed;
        
        //System.out.println(this.body.getPosition());
        if (isUpPressed && (this.body.getLinearVelocity().y < 0.001f && this.body.getLinearVelocity().y > -0.001f)) {
            jump(.5f);
            this.stamina-=.008f;
        }

        if (isDownPressed || this.body.getLinearVelocity().y < -10f) {
            air(.5f);
            this.stamina-=.01f;
        }
        if (isRightPressed&!isGrab) {
            run(.2f, 2f);
            setFlip(1);
        } else {
            if (isLeftPressed&!isGrab) {
                run(.2f, -2f);
                setFlip(-1);
            } else {
                this.body.setLinearVelocity(0, this.body.getLinearVelocity().y);
            }
        }
        if (!(isRightPressed || isLeftPressed || !(this.body.getLinearVelocity().y < 0.001f && this.body.getLinearVelocity().y > -0.001f)))
            idle(.5f);

        if(isGrab=canGrab&(isGrabPressed|isGrab)) {
        	grab(.5f);
        }

    }


    public int getFlip() {
        return flip;
    }

    public void setFlip(int flip) {
        this.flip = flip;
    }

    public void checkCollision(Interactible objet, float staminaFactor, float froidFactor, GameScreen gs) {
        Fixture fix = objet.getFixture();

        boolean cond = fix.testPoint(this.body.getPosition().add(decal))
                || fix.testPoint(this.body.getPosition().sub(decal))
                || fix.testPoint(this.body.getPosition().add(decal.x + 0.05f, -decal.y - 0.05f))
                || fix.testPoint(this.body.getPosition().add(-decal.x - 0.05f, decal.y + 0.05f))
                || fix.testPoint(this.body.getPosition().add(0, -decal.y - 0.05f));

        if (cond) {
            if (objet.getClass().hashCode() == Block.class.hashCode()) {
                Block obj = (Block) objet;
                obj.action(this, gs);
            }
            if (objet.getClass().hashCode() == Chat.class.hashCode()) {
                Chat obj = (Chat) objet;
                obj.action();
            }
            if (objet.getClass().hashCode() == Collectible.class.hashCode()) {
                Collectible obj = (Collectible) objet;
                obj.action(this, staminaFactor, froidFactor);
            }
            if (objet.getClass().hashCode() == Movable.class.hashCode()) {
                Movable obj = (Movable) objet;
                obj.action(this, staminaFactor);
            }
            if (objet.getClass().hashCode() == Radiateur.class.hashCode()) {
                Radiateur obj = (Radiateur) objet;
                obj.action(this, froidFactor);
            }
            if (objet.getClass().hashCode() == Tombant.class.hashCode()) {
                Tombant obj = (Tombant) objet;
                obj.action(this, staminaFactor);
            }
        }
    }

    public void checkOK(GameScreen gs) {
        if (this.froid < 0 || this.stamina < 0) {
            gs.game.setScreen(new MainMenuScreen(gs.game));
            gs.dispose();
        }
    }
}
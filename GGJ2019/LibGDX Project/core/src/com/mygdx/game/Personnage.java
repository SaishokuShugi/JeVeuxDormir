package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class Personnage {
    private float stamina;
    private float froid;
    private Rectangle hitbox;
    private float vitesse;

    public Personnage(float stamina, float froid, int x, int y) {
        this.stamina = stamina;
        this.froid = froid;
        this.hitbox.x = x;
        this.hitbox.y = y;
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

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void setHitbox(int x, int y) {
        this.hitbox.x = x;
        this.hitbox.y = y;
    }
}
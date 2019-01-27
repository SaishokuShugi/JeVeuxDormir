package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

import java.lang.reflect.InvocationTargetException;

public class Block extends Interactible {
	public static boolean faction=false;
    public Block(String image, int frame_cols, int frame_rows, int nbFrames, float x, float y, float friction, float density, float restitution, boolean sensor) {
        super(image, frame_cols, frame_rows, nbFrames, BodyDef.BodyType.StaticBody, x, y, friction, density, restitution, sensor);
    }

    public void action(Personnage perso, GameScreen gs) {
    	faction=true;
    }
    public void action2(Personnage perso, GameScreen gs) {	
    	faction=false;
        perso.setStamina(perso.getStaminaMax());
        perso.setFroid(perso.getFroidMax());
        try {
            gs.mapID++;
            gs.getClass().getMethod("generateMap" + gs.mapID).invoke(gs);
        } catch (NoSuchMethodException e) {
            gs.game.setScreen(MyGdxGame.MainMenu);
            gs.dispose();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

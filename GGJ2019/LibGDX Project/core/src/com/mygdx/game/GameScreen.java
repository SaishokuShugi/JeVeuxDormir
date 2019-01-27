package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
	final MyGdxGame game;
	private OrthographicCamera camera;
	SpriteBatch batch;
	TextureRegion img;
	Texture img2;
	
	Texture staminaE_C;
	Texture staminaE_L;
	Texture staminaE_R;
	Texture stamina_C;
	Texture stamina_L;
	Texture stamina_R;
	
	Texture tempE_C;
	Texture tempE_L;
	Texture tempE_R;
	Texture temp_C;
	Texture temp_L;
	Texture temp_R;

	Texture imback;

	Personnage perso;
	FrameBuffer Frameb;
	TextureRegion Fbtex;

	public static float scale_factor;

	public static World world;

    public static int mapID = 1;

    public static int rayIter = 40;

	float time =0;
	public static Controller cont;
	Box2DDebugRenderer debugRenderer;
	public ArrayList<Interactible> blocks = new ArrayList<Interactible>();
	public ArrayList<Interactible> sensors = new ArrayList<Interactible>();
	public ArrayList<Collectible> items = new ArrayList<Collectible>();
	
	ShaderProgram shader;
	
	Vector3 posCamera,newPosCamera;
	Vector3 posCameraInit = new Vector3(7.5f, 4.0f, 0f);
	int tailleMap3 = 30;


    public GameScreen(final MyGdxGame game) {
        this.game = game;
        // load the images for the monkeys
        img = new TextureRegion();
        imback = new Texture("backback.png");

        staminaE_C = new Texture("EmptyStaminaCenter.png");
        staminaE_L = new Texture("EmptyStaminaLeft.png");
        staminaE_R = new Texture("EmptyStaminaRight.png");
        stamina_C = new Texture("StaminaCenter.png");
        stamina_L = new Texture("StaminaLeft.png");
        stamina_R = new Texture("StaminaRight.png");

        tempE_C = new Texture("EmptyCenter.png");
        tempE_L = new Texture("EmptyLeft.png");
        tempE_R = new Texture("EmptyRight.png");
        temp_C = new Texture("FullCenter.png");
        temp_L = new Texture("FullLeft.png");
        temp_R = new Texture("FullRight.png");

        DeclareFragStrings();

        scale_factor = Gdx.graphics.getWidth() / (32 * 15f);

        // create the camera and the SpriteBatch
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 15, 15 * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
        


        if (!Controllers.getControllers().isEmpty())
            cont = Controllers.getControllers().get(0);
        Box2D.init();
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
		generateMap7();
		mapID=7;

        Frameb = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        loadShader(gameShader0);
        batch.setShader(null);
    }

    public void cleanMap() {
        blocks.clear();
        sensors.clear();
        items.clear();
        world = new World(new Vector2(0, -9.81f), true);
    }
    
    public void generateMap1() {
    	img2 = new Texture("Background2.png");
    	cleanMap();
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, i++, 0f, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, -1f, i++, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, 15f, i++, 0f, 1, 0,false)));
        blocks.add(new Movable("Chaise.png", 1, 1, 1, 1.58f, 3f, 0, 1, .5f, false));
        sensors.add(new Block("Lit.png", 1, 1, 1, 13f, 1f, 0, 0, 0, true));
        blocks.add(new Block("Armoire.png", 1, 1, 1, 8f, 1f, 0, 0, 0, false));
        blocks.add(new Block("Table.png", 1, 1, 1, 4f, 1f, 0, 0, 0, false));
        sensors.add(new Movable("Commode.png", 1, 1, 1, 4f, 2f, 0.1f, 1, 0, false));
        perso = new Personnage(10, 10, 0f, 2f, 1, 3.5f, 0, .5f);
        
	}

    public void generateMap2() {
    	img2.dispose();
    	img2 = new Texture("Background2.png");
    	cleanMap();
		//Mur et sol
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, i++, 0f, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, -1f, i++, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, 15f, i++, 0f, 1, 0,false)));

		//Fleurs
		blocks.add(new Movable("Plante.png", 1, 1, 1, 5f, 3f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 7f, 3f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 6f, 4f, 0, 1, 0,false));

        //Tapis*
		blocks.add(new Movable("Objet.png", 5, 6, 27, 4f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 6f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 7f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 6f, 3f, 0.1f,1, 0,false));

        //Static
		sensors.add(new Block("Lit.png", 1, 1, 1, 13f, 1f, 0, 1, 0,true));
		blocks.add(new Block("Armoire.png", 1, 1, 1, 10f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 6f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 4f, 1f, 0, 1, 0,false));

        //Personnage
        perso = new Personnage(10, 10, 2f, 1f, 1, 3.5f, 0, .5f);
	}
    public void generateMap3() {
    	img2.dispose();
    	img2 = new Texture("Background2.png");
		cleanMap();
		//Mur et sol
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, i++, 0f, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, -1f, i++, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, 15f, i++, 0f, 1, 0,false)));
		
		//Fleurs
		blocks.add(new Movable("Plante.png", 1, 1, 1, 9f, 2f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 8f, 2f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 7f, 2f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 6f, 2f, 0, 1, 0,false));
		
		//Tapis*
		blocks.add(new Movable("Chaise.png", 1, 1, 1, 3f, 2f, 0.1f,0.7f, 0,false));
		blocks.add(new Movable("Chaise.png", 1, 1, 1, 3f, 1f, 0.1f,0.7f, 0,false));
		
		//Static
		sensors.add(new Block("Lit.png", 1, 1, 1, 13f, 1f, 0, 1, 0,true));
		blocks.add(new Block("Bibliotheque.png", 1, 1, 1, 11.1f, 4f, 0, 1, 0,false));
		blocks.add(new Block("Armoire.png", 1, 1, 1, 11.1f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 6f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 4f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 9.05f, 1f, 0, 1, 0,false));
		
		//Personnage
        perso = new Personnage(10, 10, 2f, 1f, 1, 3.5f, 0, .5f);
	}
    public void generateMap5() {
		cleanMap();
		img2.dispose();
    	img2 = new Texture("BackgroundGrand.png");
		//Mur et sol
		for(float i = 0;i<30;blocks.add(new Block("Sol.png", 2, 2, 4, i++, 0f, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, -1f, i++, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, 30f, i++, 0f, 1, 0,false)));
		
		//Fleurs
		blocks.add(new Movable("Plante.png", 1, 1, 1, 9f, 1f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 8f, 2f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 24f, 1f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 24f, 2f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 24f, 3f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 24f, 4f, 0, 1, 0,false));
		
		//Tapis
		blocks.add(new Movable("Chaise.png", 1, 1, 1, 8f, 1f, 0.1f,0.7f, 0,false));
		blocks.add(new Movable("Chaise.png", 1, 1, 1, 9f, 2f, 0.1f,0.7f, 0,false));
		
		//Static
		blocks.add(new Block("Commode.png", 1, 1, 1, 2f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 5f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 6f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Bibliotheque.png", 1, 1, 1, 5f, 2f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 12f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Armoire.png", 1, 1, 1, 12f, 2f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 12f, 5f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 12f, 6f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 13f, 6f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 17f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 20f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 21f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Bibliotheque.png", 1, 1, 1, 20f, 2f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 28f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 29f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Armoire.png", 1, 1, 1, 28f, 2f, 0, 1, 0,false));
		//Sensors
		sensors.add(new Radiateur("Radiateur.png", 5, 5, 22, 0f, 0.9f, 0, 1, 0,true,2f));
		sensors.add(new Radiateur("Radiateur.png", 5, 5, 22, 14f, 0.9f, 0, 1, 0,true,2f));
		sensors.add(new Block("Lit.png", 1, 1, 1, 28f, 5f, 0, 1, 0,true));
		
		//Personnage
        perso = new Personnage(10, 10, 2f, 1f, 1, 3.5f, 0, .5f);
	}
    
    public void generateMap4() {
		cleanMap();
		img2.dispose();
    	img2 = new Texture("BackgroundGrand.png");
		//Mur et sol
		for(float i = 0;i<30;blocks.add(new Block("Sol.png", 2, 2, 4, i++, 0f, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, -1f, i++, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, 30f, i++, 0f, 1, 0,false)));
		
		//Tapis
		blocks.add(new Movable("Objet.png", 5, 6, 27, 2f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 3f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 4f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 5f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 2.5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 3.5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 4.5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 3f, 3f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 4f, 3f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 3.5f, 4f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 13f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 14f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 15f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 16f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 13.5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 14.5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 15.5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 14f, 3f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 15f, 3f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 14.5f, 4f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 17f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 18f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 19f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 20f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 17.5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 18.5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 19.5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 18f, 3f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 20f, 3f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 19f, 4f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 21f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 22f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 23f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 24f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 25f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 26f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 27f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 21f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 22f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 23f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 24f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 25f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 26f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 27f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 24f, 3f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 25f, 3f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 26f, 3f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 27f, 3f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 25f, 4f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 26f, 4f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 27f, 4f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 26f, 5f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 27f, 5f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 27f, 6f, 0.1f,1, 0,false));
		
		//Static
		blocks.add(new Block("Bibliotheque.png", 1, 1, 1, 8f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Bibliotheque.png", 1, 1, 1, 8f, 4f, 0, 1, 0,false));
		blocks.add(new Block("Bibliotheque.png", 1, 1, 1, 12f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Bibliotheque.png", 1, 1, 1, 12f, 4f, 0, 1, 0,false));
		
		//Sensors
		sensors.add(new Radiateur("Radiateur.png", 5, 5, 22, 0f, 0.9f, 0, 1, 0,true,2f));
		sensors.add(new Radiateur("Radiateur.png", 5, 5, 22, 24f, 0.9f, 0, 1, 0,true,2f));
		sensors.add(new Block("Lit.png", 1, 1, 1, 28f, 5f, 0, 1, 0,true));
		
		//Personnage
        perso = new Personnage(10, 10, 2f, 1f, 1, 3.5f, 0, .5f);
	}
    
    

    public void generateMap7() {
		cleanMap();
		//img2.dispose();
    	img2 = new Texture("BackgroundGrand.png");
		//Mur et sol
		for(float i = 0;i<30;blocks.add(new Block("Sol.png", 2, 2, 4, i++, 0f, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, -1f, i++, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, 29f, i++, 0f, 1, 0,false)));
		
		//Tapis
		blocks.add(new Movable("Objet.png", 5, 6, 27, 28f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 27f, 1f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 26f, 2.54f, 0.1f,1, 0,false));
		blocks.add(new Movable("Chaise.png", 5, 6, 27, 27.5f, 2.57f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 25f, 2.6f, 0.1f,1, 0,false));
		blocks.add(new Movable("Plante.png", 5, 6, 27, 25.2f, 2.1f, 0.1f,1, 0,false));
		
		//Static
		blocks.add(new Block("Commode.png", 1, 1, 1, 3f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 6f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Bibliotheque.png", 1, 1, 1, 6f, 2f, 0, 1, 0,false));
		blocks.add(new Block("Armoire.png", 1, 1, 1, 11f, 2f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 11f, 1f, 0, 1, 0,false));
		
		for(float i = 0;i<7;blocks.add(new Block("Sol.png", 2, 2, 4, 14f, 3f+i++, 0, 1, 0,false)));
		blocks.add(new Block("Sol.png", 2, 2, 4, 15f, 3f, 0, 1, 0,false));
		blocks.add(new Block("Sol.png", 2, 2, 4, 16f, 3f, 0, 1, 0,false));
		for(float i = 0;i<4;blocks.add(new Block("Sol.png", 2, 2, 4, 17f, 3f+i++, 0, 1, 0,false)));
		for(float i = 0;i<10;blocks.add(new Block("Sol.png", 2, 2, 4, 17f+i++,7f, 0, 1, 0,false)));
		
		for(float i = 0;i<5;blocks.add(new Block("Sol.png", 2, 2, 4, 28f, 5f+i++, 0, 1, 0,false)));
		for(float i = 0;i<6;blocks.add(new Block("Sol.png", 2, 2, 4, 23f+i++,4f, 0, 1, 0,false)));
		
		//Sensors
		sensors.add(new Radiateur("Radiateur.png", 5, 5, 22, 14f, 0.9f, 0, 1, 0,true,2f));
		sensors.add(new Block("Lit.png", 1, 1, 1, 15f, 4f, 0, 1, 0,true));
		
		//Personnage
        perso = new Personnage(10, 10, 2f, 1f, 1, 3.5f, 0, .5f);
	}
    
	String gameShader0;
	String backMenuShader;
	
	void DeclareFragStrings() {
		backMenuShader ="#version 120 																			\n"
                  + "uniform float time;																		\n"
				  + "varying vec2 v_texCoords;																	\n" 
                  + "uniform sampler2D u_texture;																\n"
                  + "const vec2 resolution= vec2("+Gdx.graphics.getWidth()+","+Gdx.graphics.getHeight()+");		\n" 
                  + "vec3 blur(vec2 fc){																		\n"
                  + "vec3 c= vec3(0);																			\n"
                  + "for(int i =-6;i<=6;i++){																	\n"
                  + "for(int j =-6;j<=6;j++){																	\n"
                  + "c+=texture2D(u_texture,(fc+vec2(i,j)*2.)/resolution).rgb;}}								\n"
                  + "return c/169.;																				\n"
                  + "}																							\n"
                  + "void main()                                  												\n"
                  + "{     												                                       	\n"
                  + "vec2 uv = v_texCoords;																		\n"
                  + "vec2 fc = floor(uv*resolution);															\n"
                  + "vec4 color = texture2D(u_texture, uv);														\n"
                  + "gl_FragColor = vec4(mix(blur(fc),vec3(.5*smoothstep(1.5,0.,distance(uv,vec2(.5)))),.7),1);	\n"
                  + "}";
		
		gameShader0="#version 120 																			\n"
                  + "uniform float time;																	\n"
				  + "varying vec2 v_texCoords;																\n" 
                  + "uniform sampler2D u_texture;															\n"
                  + "uniform vec2 rad1;\n"
                  + "uniform vec2 rad2;\n"
                  + "const vec2 resolution= vec2("+Gdx.graphics.getWidth()+","+Gdx.graphics.getHeight()+");	\n"
                  		+ "float bayer2(vec2 v)\n" + 
                  		"{\n" + 
                  		"    v=floor(v);\n" + 
                  		" return fract(v.y*v.y*.75+v.x*.5);   \n" + 
                  		"}\n" + 
                  		"float bayer4(vec2 v)\n" + 
                  		"{\n" + 
                  		"    return bayer2(.5*v)*.25+bayer2(v);\n" + 
                  		"}\n" + 
                  		"float bayer8(vec2 v)\n" + 
                  		"{\n" + 
                  		"    return bayer4(.5*v)*.25+bayer2(v);\n" + 
                  		"}"
                  		+ "vec2 hash22(vec2 p)\n" + 
                  		"	{\n" + 
                  		"		vec3 p3 = fract(vec3(p.xyx) * vec3(443.897, 441.423, 437.195));\n" + 
                  		"	    p3 += dot(p3, p3.yzx+19.19);\n" + 
                  		"	    return fract((p3.xx+p3.yz)*p3.zy)-.5;\n" + 
                  		"\n" + 
                  		"	}\n" + 
                  		""
                  + "const int samples = "+rayIter+";"
                  		+ "const vec2 lp = vec2(.75);"
                  + "float ray(vec2 uv){																	\n"
                  + "float dith = bayer8(uv*resolution);"
                  + "float a=0;"
                  + "vec2 ld = lp-uv;"
                  + "float m=1.;"
                + "if(texture2D(u_texture,uv).g>=.8 && length(texture2D(u_texture,uv).rb)<.1)m= -1.;											\n"
                  + "uv+=ld/samples*dith;"
                  + "for(int i=0;i++<samples;){"
                  + "float d = texture2D(u_texture,uv).g>=.8"
                  + "&&length(texture2D(u_texture,uv).rb)<.1?1.:0.;"
                  + "a+=d/float(samples);"
                  + "uv+=ld/float(samples);"
                  + "}"
                  + "return m*a+(m-1.)/4.;"
                  + "}"
                  + "void main(){                                 											\n"
                  + "vec2 uv = v_texCoords;"
                  + "vec2 r1 =rad1/resolution;"
                  + "if(abs(uv.x-r1.x)<.15){"
                  + "float a=smoothstep(0.0,.1,uv.y-r1.y)*smoothstep(.5,.3,uv.y-r1.y);\n"
                  + "a*=smoothstep(.07,.0,abs(uv.x-r1.x));"
                  + "uv+=a*vec2(sin((uv.y-r1.y)*400.-time*3.)*.001,cos((uv.x-r1.x)*350.-time*2.9)*.0009);"
                  + "}																	\n"
                  + "r1 =rad2/resolution;\n" + 
                  "if(abs(uv.x-r1.x)<.15){\n" + 
                  "float a=smoothstep(0.0,.1,uv.y-r1.y)*smoothstep(.5,.3,uv.y-r1.y);\n" + 
                  "a*=smoothstep(.07,.0,abs(uv.x-r1.x));\n" + 
                  "uv+=a*vec2(sin((uv.y-r1.y)*400.-time*3.)*.001,cos((uv.x-r1.x)*350.-time*2.9)*.0009);\n" + 
                  "}"
                  + "vec4 color = texture2D(u_texture, uv);													\n"
                  + "float r = ray(uv);"
                  + "if(r<0){"
                  + "vec2 u = floor(uv*20);\n" 
                  + "vec2 off1 = hash22(u);\n" + 
                  "vec2 off2 =  vec2(0,1)+hash22(u+vec2(0,1));\n" + 
                  "vec2 off3 =  vec2(1)+hash22(u+vec2(1));\n" + 
                  "vec2 off4 =  vec2(1,0)+hash22(u+vec2(1,0));\n"
                  + "u=fract(uv*20);" + 
                  "float dist = min(min(distance(u,off1),distance(u,off2)),min(distance(u,off3),distance(u,off4)));"
                  + "color.rgb=mix(vec3(.1,.1,.3),vec3(1),max(smoothstep(.06,0,dist),smoothstep(.06,.05,distance(uv,lp))));"
                  + "r=.5*-r;"
                  + "}"
                  + "gl_FragColor = vec4(mix(color.rgb*vec3(.45,.5,.8),vec3(.7,.7,.8),pow(r,.85)),1) ;								\n"
                  + "}";
	}
	void  loadShader(String frag) {
		String vertexShader = "attribute vec4 a_position;    	\n" + 
                "attribute vec4 a_color;						\n" +
                "attribute vec2 a_texCoord0;					\n" + 
                "uniform mat4 u_projTrans;						\n" + 
                "varying vec2 v_texCoords;						\n" + 
                "void main()                  					\n" + 
                "{                            					\n" +
                "   v_texCoords = a_texCoord0; 					\n" + 
                "   gl_Position =  u_projTrans * a_position;  	\n" + 
                "}" ;
		String fragmentShader =	frag;
		shader = new ShaderProgram(vertexShader, fragmentShader);
		if (!shader.isCompiled()) {
			System.err.println(shader.getLog());
			System.exit(0);
		}
		if (shader.getLog().length()!=0)
			System.out.println(shader.getLog());
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		batch.setShader(null);
		posCamera = camera.position;
		Vector2 rad1= new Vector2(-100,-100);
		Vector2 rad2= rad1.cpy();
		
		float depx =0;
		float depy =0;
		float mullx=1;
		float mully=1;
		switch(mapID)
		{
			case 1:
			case 2:
			case 3:
				break;
			case 4:
			case 5:
			{
				mullx=2;
				depx = Math.max(0,Math.min(perso.getBody().getPosition().x-7.5f,30-15f))*scale_factor*32;
			}
			case 7:
			{
				//mull_ = number of meters in the map/ number of meters in the frame
				mullx=29/15f;
				mully=10/8.44f;

				//dep_ max(min(0, position- (number of meters in the frame)/2),number of meters in the map-number of meters in the frame)
				depx = Math.max(0,Math.min(perso.getBody().getPosition().x-7.5f,29-15f))*scale_factor*32;
				depy = Math.max(0,Math.min(perso.getBody().getPosition().y-4.22f,10-8.43f))*scale_factor*32;
			}
		}
		float deltat = Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Fbtex = new TextureRegion(Frameb.getColorBufferTexture());
		Fbtex.flip(false,true);
		Frameb.begin();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		
		//debugRenderer.render(world, camera.combined);
		
		
		batch.draw(imback,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		
		batch.draw(img2,0-depx*.9f,0-depy*.9f,Gdx.graphics.getWidth()*mullx,Gdx.graphics.getHeight()*mully);
		for (Interactible block : blocks) {
			block.getBody().setLinearVelocity(block.getBody().getLinearVelocity().x/1.1f, block.getBody().getLinearVelocity().y);
			img = block.getImages()[block.tile];
			batch.draw(img, block.getBodyXToImage()-depx, block.getBodyYToImage()-depy, img.getRegionWidth() * scale_factor,
					img.getRegionHeight() * scale_factor);
		}
		for (Interactible block : sensors) {
			if (block instanceof Radiateur) {
				if(rad1.x<0.) {
				rad1 =new Vector2(block.getBody().getPosition().x*scale_factor*32-depx,block.getBody().getPosition().y*scale_factor*32-depy);
				}else {
				rad2 =new Vector2(block.getBody().getPosition().x*32*scale_factor-depx,block.getBody().getPosition().y*32*scale_factor-depy);
				}
			((Radiateur) block).setAnimTime(time+=deltat);
			batch.draw(((Radiateur) block).getCurrentFrame(), ((Radiateur) block).getBodyXToImage()-depx, ((Radiateur) block).getBodyYToImage()-depy
					,((Radiateur) block).getCurrentFrame().getRegionWidth()* scale_factor,((Radiateur) block).getCurrentFrame().getRegionHeight() * scale_factor);
			
			}
			else {
			img = block.getImages()[block.tile];
			batch.draw(img, block.getBodyXToImage()-depx, block.getBodyYToImage()-depy, img.getRegionWidth() * scale_factor,
					img.getRegionHeight() * scale_factor);
			}
		}
		for (Interactible block : items) {
			img = block.getImages()[block.tile];
			batch.draw(img, block.getBodyXToImage()-depx, block.getBodyYToImage()-depy, img.getRegionWidth() * scale_factor,
					img.getRegionHeight() * scale_factor);
		}


		perso.setAnimTime(time+=deltat);
		batch.draw(perso.getCurrentFrame(), perso.getBodyXToImage()+(1-(perso.getFlip()+1)/2f)*perso.getCurrentFrame().getRegionWidth() * scale_factor-depx,
				perso.getBodyYToImage()-depy
				,perso.getCurrentFrame().getRegionWidth()* scale_factor*perso.getFlip(),perso.getCurrentFrame().getRegionHeight() * scale_factor);
		
		batch.flush();
		Frameb.end();
		
		batch.setShader(shader);

		float[] t = new float[1];
		t[0]=time;
	    shader.setUniform1fv( shader.getUniformLocation("time"),t, 0, 1);
	    float[] r1 = {rad1.x,rad1.y};
	    shader.setUniform2fv( shader.getUniformLocation("rad1"),r1, 0, 2);
	    float[] r2 = {rad2.x,rad2.y};
	    shader.setUniform2fv( shader.getUniformLocation("rad2"),r2, 0, 2);

		batch.draw(Fbtex, 0, 0);

		batch.setShader(null);
		
		batch.draw(staminaE_L, 10, Gdx.graphics.getBackBufferHeight()-50);
		for(int i =0;i<32*9;batch.draw(staminaE_C, 10+(i+=32), Gdx.graphics.getBackBufferHeight()-50));
		batch.draw(staminaE_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-50);
		
		if(perso.getStamina()>0)batch.draw(stamina_L, 10, Gdx.graphics.getBackBufferHeight()-50);
		for(int i =32;i<(int)perso.getStamina()/perso.getStaminaMax()*290;batch.draw(stamina_C, 10+(i++), Gdx.graphics.getBackBufferHeight()-50));
		if(perso.getStamina()==perso.getStaminaMax())batch.draw(stamina_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-50);
		
		batch.draw(tempE_L, 10, Gdx.graphics.getBackBufferHeight()-100);
		for(int i =0;i<32*9;batch.draw(tempE_C, 10+(i+=32), Gdx.graphics.getBackBufferHeight()-100));
		batch.draw(tempE_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-100);
		
		if(perso.getStamina()>0)batch.draw(temp_L, 10, Gdx.graphics.getBackBufferHeight()-100);
		for(int i =32;i<(int)perso.getFroid()/perso.getFroidMax()*290;batch.draw(temp_C, 10+(i++), Gdx.graphics.getBackBufferHeight()-100));
		if(perso.getFroid()==perso.getFroidMax())batch.draw(temp_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-100);



		batch.end();
		
		checkTriggers();

        perso.controls(testGrab());
        if(perso.triggerReset) {
        	switch(mapID) {
        	case 1:
        		generateMap1();
        		break;
        	case 2:
        		generateMap2();
        		break;
        	case 3:
        		generateMap3();
        		break;
        	case 4:
        		generateMap4();
        		break;
        	case 5:
        		generateMap5();
        		break;
        	case 6:
        		//generateMap6();
        		break;
        	case 7:
        		generateMap7();
        		break;
        		
        	}
        }

		world.step(Math.min(deltat,.15f), 6, 2);
	}

	boolean testGrab() {
		Vector2 dec = perso.decal.cpy();
    	dec.scl(1.1f);
    	Vector2 p1 = perso.getBody().getPosition().cpy().add(perso.getFlip()*dec.x,dec.y);
    	Vector2 p2 = perso.getBody().getPosition().cpy().add(perso.getFlip()*dec.x,dec.y*.5f);
    	boolean b1 = false,b2=false;
    	for (Interactible i : blocks) {
    		Fixture fix = i.getFixture();
    		b1=b1||fix.testPoint(p1);
    		b2=b2||fix.testPoint(p2);
		}

    	return (!b1&&b2);
	}
	
	
	void checkTriggers() {
        perso.checkOK(this);
        Block i = null;
		Iterator<Interactible> iter = sensors.iterator();
		while(iter.hasNext()){
			Interactible y=iter.next();
			perso.checkCollision(y, 0, 0.01f, this);
			if(Block.faction)i=(Block) y;
			Block.faction=false;
		}
		if(i!=null)i.action2(perso,this);
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		batch.dispose();
		staminaE_C.dispose();
		staminaE_L.dispose();
		staminaE_R.dispose();
		stamina_C.dispose();
		stamina_L.dispose();
		stamina_R.dispose();
		
		tempE_C.dispose();
		tempE_L.dispose();
		tempE_R.dispose();
		temp_C.dispose();
		temp_L.dispose();
		temp_R.dispose();

		img2.dispose();
		imback.dispose();

	}
}
package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen  {
	final MyGdxGame game;
	private OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	Texture img2;

	public static World world;
	
	Controller cont;
	Box2DDebugRenderer debugRenderer;
	public Block block;
	public GameScreen (final MyGdxGame game) {
		this.game = game;
		// load the images for the monkeys
		img = new Texture("Lit.png");
		img2 = new Texture("Objet.png");
		// create the camera and the SpriteBatch
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
	

		cont = Controllers.getControllers().get(0);
		Box2D.init();
		world = new World(new Vector2(0,-9.81f),true);
		debugRenderer = new Box2DDebugRenderer();
		block = new Block("Chaise.png",1,1,150,150,0,0,0);
	}

	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		float deltat =Gdx.graphics.getDeltaTime();

		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		debugRenderer.render(world, camera.combined);
		batch.begin();
		batch.draw(block.getImages()[0].getTexture(),block.getBody().getPosition().x-block.getImages()[0].getTexture().getWidth()/2,block.getBody().getPosition().y-block.getImages()[0].getTexture().getHeight()/2);
		world.step(Math.min(.015f,deltat), 6, 2);
		
		batch.end();
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
	public void dispose () {
		// dispose of all the native resources
		batch.dispose();
		img.dispose();
		img2.dispose();		
	}
}

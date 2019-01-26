package com.mygdx.game;

import java.util.ArrayList;
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	final MyGdxGame game;
	private OrthographicCamera camera;
	SpriteBatch batch;
	TextureRegion img;
	Texture img2;

	public static float scale_factor;

	public static World world;

	Controller cont;
	Box2DDebugRenderer debugRenderer;
	public ArrayList<Interactible> blocks = new ArrayList<Interactible>();

	
	void generateMap() {
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, i++, 0f, 0, 0, 0)));
		blocks.add(new Movable("Chaise.png", 1, 1, 1f, 2f, 0, 0, .5f));
		blocks.add(new Block("lit.png", 1, 1, 13f, 1f, 0, 0, 0));
	}
	
	
	public GameScreen(final MyGdxGame game) {
		this.game = game;
		// load the images for the monkeys
		img = new TextureRegion();
		img2 = new Texture("Objet.png");
		// create the camera and the SpriteBatch
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		scale_factor = Gdx.graphics.getWidth() / (32 * 15f);

		if (!Controllers.getControllers().isEmpty())
			cont = Controllers.getControllers().get(0);
		Box2D.init();
		world = new World(new Vector2(0, -9.81f * scale_factor), true);
		debugRenderer = new Box2DDebugRenderer();
		generateMap();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		float deltat = Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world, camera.combined);
		batch.begin();
		for (Interactible block : blocks) {
			img = block.getImages()[block.tile];
			batch.draw(img, block.getBodyXToImage(), block.getBodyYToImage(), img.getRegionWidth() * scale_factor,
					img.getRegionHeight() * scale_factor);
			world.step(Math.min(.015f, deltat), 6, 2);
		}

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
	public void dispose() {
		// dispose of all the native resources
		batch.dispose();
		// img.dispose();
		img2.dispose();
	}
}

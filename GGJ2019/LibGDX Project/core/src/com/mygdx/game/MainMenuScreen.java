package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class MainMenuScreen implements Screen {

	final MyGdxGame game;
	OrthographicCamera camera;
	FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("SleeplessCity.ttf"));
	BitmapFont createFont(FreeTypeFontGenerator ftfg, int taille)
	{
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = taille;
		parameter.borderColor = Color.BLACK;
		parameter.color = Color.WHITE;
		parameter.borderWidth = 3f;
	    return ftfg.generateFont(parameter);
	}
	
	BitmapFont TitreFont = createFont(ftfg, 55);
	BitmapFont SousTitreFont = createFont(ftfg, 20);

	public MainMenuScreen(final MyGdxGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		TitreFont.draw(game.batch, "Je Veux Dormir ", 100, 700);
		SousTitreFont.draw(game.batch, "A Vegan Product", 100, 650);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
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
		// TODO Auto-generated method stub
		game.batch.dispose();
		
	}


}
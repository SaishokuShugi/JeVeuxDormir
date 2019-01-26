package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MainMenuScreen implements Screen {

	final MyGdxGame game;
	OrthographicCamera camera;
	FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("SleeplessCity.ttf"));
	BitmapFont createFont(FreeTypeFontGenerator ftfg, int taille, boolean inverseCouleur )
	{
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = taille;
		
		if (inverseCouleur)
		{
			parameter.borderColor = Color.WHITE;
			parameter.color = Color.BLACK;
		}
		else 
		{
			parameter.borderColor = Color.BLACK;
			parameter.color = Color.WHITE;
		}
		
		parameter.borderWidth = 3f;
	    return ftfg.generateFont(parameter);
	}
	
	BitmapFont TitreFont = createFont(ftfg, 55, false);
	BitmapFont SousTitreFont = createFont(ftfg, 15, false);
	BitmapFont ButtonFont = createFont(ftfg, 30, true);
	
	Texture ButtonImage = new Texture(Gdx.files.internal("Commode.png"));
	Vector3 MousePos = new Vector3(0f, 0f, 0f);

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
		MousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		camera.unproject(MousePos);
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		TitreFont.draw(game.batch, "Je Veux Dormir ", 100, 700);
		SousTitreFont.draw(game.batch, "A Vegan Product", 100, 625);
		game.batch.draw(ButtonImage, 0, 250, 320, 320);
		ButtonFont.draw(game.batch, "Play", 100, 450);
		ButtonFont.draw(game.batch, "Option", 100, 350);
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
		//game.batch.dispose();
		TitreFont.dispose();
		SousTitreFont.dispose();
		
	}


}
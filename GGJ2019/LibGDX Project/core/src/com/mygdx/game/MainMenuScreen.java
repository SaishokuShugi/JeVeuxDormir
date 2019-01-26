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
	BitmapFont createFont(FreeTypeFontGenerator ftfg, int taille, boolean inverseCouleur , boolean border)
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
		if (border)
		{
			parameter.borderWidth = 3f;
		}
		else
		{
			parameter.borderWidth = 0f;
		}
		
	    return ftfg.generateFont(parameter);
	}
	
	BitmapFont TitreFont = createFont(ftfg, 55, false, true);
	BitmapFont SousTitreFont = createFont(ftfg, 15, false, true);
	BitmapFont ButtonFont1 = createFont(ftfg, 30, true, true);
	BitmapFont ButtonFont2 = createFont(ftfg, 30, false, false);
	
	Texture ButtonImage = new Texture(Gdx.files.internal("Commode.png"));
	Vector3 MousePos = new Vector3(0f, 0f, 0f);
	
	Boolean onPlay, 
			onOption;

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
		TitreFont.draw(game.batch, "Je Veux Dormir ", 120, 700);
		SousTitreFont.draw(game.batch, "A Vegan Product", 140, 625);
		game.batch.draw(ButtonImage, 0, 255, 320, 320);
		if(MousePos.x> 50 && MousePos.x < 270 && MousePos.y> 390 && MousePos.y< 490)
		{
			ButtonFont1.draw(game.batch, "Play", 127, 450);
			onPlay = true;
		}
		else 
		{
			ButtonFont2.draw(game.batch, "Play", 127, 450);
			onPlay = false;
		}
		
		if(MousePos.x> 50 && MousePos.x < 270 && MousePos.y> 290 && MousePos.y< 390)
		{
			ButtonFont1.draw(game.batch, "Option", 105, 350);
			onOption = true;
		}
		else 
		{
			ButtonFont2.draw(game.batch, "Option", 105, 350);
			onOption = false;
		}
		
		game.batch.end();

		if (Gdx.input.isTouched()) {
			if (onPlay)
			{
				game.setScreen(new GameScreen(game));
			}
			else if (onOption)
			{
				game.setScreen(new OptionScreen(game));
			}
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
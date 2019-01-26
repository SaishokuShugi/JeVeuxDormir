package com.mygdx.game;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MainMenuScreen implements Screen
{

	final MyGdxGame game;
	OrthographicCamera camera;
	FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("SleeplessCity.ttf"));

	BitmapFont createFont(FreeTypeFontGenerator ftfg, int taille, boolean inverseCouleur, boolean border)
	{
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = taille;

		if (inverseCouleur)
		{
			parameter.borderColor = Color.WHITE;
			parameter.color = Color.BLACK;
		} else
		{
			parameter.borderColor = Color.BLACK;
			parameter.color = Color.WHITE;
		}
		if (border)
		{
			parameter.borderWidth = 3f;
		} else
		{
			parameter.borderWidth = 0f;
		}

		return ftfg.generateFont(parameter);
	}

	BitmapFont TitreFont = createFont(ftfg, 55, false, true);
	BitmapFont SousTitreFont = createFont(ftfg, 15, false, true);
	BitmapFont ButtonFont1 = createFont(ftfg, 30, true, true);
	BitmapFont ButtonFont2 = createFont(ftfg, 30, false, false);

	Texture LitImage = new Texture(Gdx.files.internal("Lit.png"));
	Texture ButtonImage = new Texture(Gdx.files.internal("Commode.png"));
	Texture SolTextureMap = new Texture(Gdx.files.internal("Sol.png"));
	Texture FondImage = new Texture(Gdx.files.internal("Background.png"));
	TextureRegion[][] SolImages = TextureRegion.split(SolTextureMap, 64, 32);
	Texture PersoRunTextureMap = new Texture(Gdx.files.internal("Personnage/run.png"));
	Animation<TextureRegion> RunAnim1 = Personnage.loadAnim("Personnage/run.png", 4, 2, 0.25f);
	Animation<TextureRegion> RunAnim2 = Personnage.loadAnim("Personnage/run.png", 4, 2, 0.5f);
	Animation<TextureRegion> RunAnim3 = Personnage.loadAnim("Personnage/run.png", 4, 2, 0.75f);
	Animation<TextureRegion> RunAnim4 = Personnage.loadAnim("Personnage/run.png", 4, 2, 1f);

	
	Vector3 MousePos = new Vector3(0f, 0f, 0f);
	Vector2 PersoPos = new Vector2(-320, 10);

	Boolean onPlay, onOption;
	
	float stateTime = 0f;

	public MainMenuScreen(final MyGdxGame game)
	{
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);

	}

	@Override
	public void show()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta)
	{
		// TODO Auto-generated method stub
		MousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		camera.unproject(MousePos);
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(FondImage, 0, 0, 1280, 720);
		TitreFont.draw(game.batch, "Je Veux Dormir ", 120, 700);
		SousTitreFont.draw(game.batch, "A Vegan Product", 140, 625);
		game.batch.draw(ButtonImage, 0, 255, 320, 320);
		
		
		int i = 0;
		while (i<1280)
		{
			game.batch.draw(SolImages[0][0], i*128,-30, 128, 64);
			i++;
		}
		game.batch.draw(LitImage, 1280 - 320, 10, 320, 320);

		if (MousePos.x > 50 && MousePos.x < 270 && MousePos.y > 390 && MousePos.y < 490)
		{
			ButtonFont1.draw(game.batch, "Play", 127, 450);
			onPlay = true;
		} else
		{
			ButtonFont2.draw(game.batch, "Play", 127, 450);
			onPlay = false;
		}

		if (MousePos.x > 50 && MousePos.x < 270 && MousePos.y > 290 && MousePos.y < 390)
		{
			ButtonFont1.draw(game.batch, "Option", 105, 350);
			onOption = true;
		} else
		{
			ButtonFont2.draw(game.batch, "Option", 105, 350);
			onOption = false;
		}

		stateTime += Gdx.graphics.getDeltaTime();
		
		TextureRegion currentFrame = RunAnim1.getKeyFrame(stateTime, true);
		game.batch.draw(currentFrame, PersoPos.x, PersoPos.y, 210, 330);
		
		game.batch.end();
		
		PersoPos.x += 2.5f;

		if (Gdx.input.isTouched())
		{
			if (onPlay)
			{
				game.setScreen(new GameScreen(game));
				dispose();
			} else if (onOption)
			{
				game.setScreen(new OptionScreen(game));
				dispose();
			}
		}
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		// game.batch.dispose();
		TitreFont.dispose();
		SousTitreFont.dispose();
		ButtonFont1.dispose();
		ButtonFont2.dispose();

	}

}
package com.mygdx.game;

import java.util.ArrayList;

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

	BitmapFont TitreFont = createFont(ftfg, 90, false, true);
	BitmapFont SousTitreFont = createFont(ftfg, 25, false, true);
	BitmapFont ButtonFont1 = createFont(ftfg, 30, true, true);
	BitmapFont ButtonFont2 = createFont(ftfg, 30, false, false);

	Texture LitImage = new Texture(Gdx.files.internal("Lit.png"));
	Texture ButtonImage = new Texture(Gdx.files.internal("Commode.png"));
	Texture SolTextureMap = new Texture(Gdx.files.internal("Sol.png"));
	Texture FondImage = new Texture(Gdx.files.internal("Background.png"));
	TextureRegion[][] SolImages = TextureRegion.split(SolTextureMap, 64, 32);
	Texture PersoRunTextureMap = new Texture(Gdx.files.internal("Personnage/run.png"));
	Animation<TextureRegion> RunAnim1 = Personnage.loadAnim("Personnage/run.png", 4, 2, 0.0625f);
	Animation<TextureRegion> RunAnim2 = Personnage.loadAnim("Personnage/run.png", 4, 2, 0.125f);
	Animation<TextureRegion> RunAnim3 = Personnage.loadAnim("Personnage/run.png", 4, 2, 0.25f);
	Animation<TextureRegion> RunAnim4 = Personnage.loadAnim("Personnage/run.png", 4, 2, 0.5f);
	ArrayList<Animation<TextureRegion>> ListAnimation = new ArrayList<Animation<TextureRegion>>();
	
	Vector3 MousePos = new Vector3(0f, 0f, 0f);
	Vector2 PersoPos = new Vector2(-320, 24);

	Boolean onPlay, onOption;
	
	int xButton = 0, yButton =0, numAnim = 0;
	
	float stateTime = 0f, stateTime2 = 0f;

	public MainMenuScreen(final MyGdxGame game)
	{
		this.game = game;
		ListAnimation.add(RunAnim1);
		ListAnimation.add(RunAnim2);
		ListAnimation.add(RunAnim3);
		ListAnimation.add(RunAnim4);
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
		int i = 0;
		while (i<1280)
		{
			game.batch.draw(SolImages[0][0], i*128,-30, 128, 64);
			i++;
		}
		TitreFont.draw(game.batch, "Je Veux Dormir ", 120, 700);
//		SousTitreFont.draw(game.batch, "A Vegan Product", 140, 615);
		SousTitreFont.draw(game.batch, Float.toString(stateTime2), 140, 615);
		game.batch.draw(ButtonImage, xButton = 0, yButton =24, 320, 320);
		
		
		
		game.batch.draw(LitImage, 1280 - 320, 24, 320, 320);

		if (MousePos.x > xButton+50 && MousePos.x < xButton+270 && MousePos.y > yButton +135 && MousePos.y < yButton+235)
		{
			ButtonFont1.draw(game.batch, "Play", xButton+127, yButton+195);
			onPlay = true;
		} else
		{
			ButtonFont2.draw(game.batch, "Play", xButton+127, yButton+195);
			onPlay = false;
		}

		if (MousePos.x > xButton+50 && MousePos.x < xButton+270 && MousePos.y > yButton +35 && MousePos.y < yButton+135)
		{
			ButtonFont1.draw(game.batch, "Option", xButton+105, yButton+95);
			onOption = true;
		} else
		{
			ButtonFont2.draw(game.batch, "Option", xButton+105, yButton+95);
			onOption = false;
		}

		stateTime += Gdx.graphics.getDeltaTime();
		stateTime2 += Gdx.graphics.getDeltaTime();
		if(numAnim<4)
		{
			TextureRegion currentFrame = ListAnimation.get(numAnim).getKeyFrame(stateTime, true);
			game.batch.draw(currentFrame, PersoPos.x, PersoPos.y, 210, 330);
			
		}

		game.batch.end();
		
		PersoPos.x += 2f/(numAnim+1);
		if(numAnim<4 && stateTime2 >3)
		{
			stateTime2=0;
			numAnim++;
		}

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
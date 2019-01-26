package com.mygdx.game;

import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
	static FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("SleeplessCity.ttf"));

	static BitmapFont createFont(FreeTypeFontGenerator ftfg, int taille, boolean inverseCouleur, boolean border)
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

	static BitmapFont TitreFont = createFont(ftfg, 83, false, true);
	static BitmapFont SousTitreFont = createFont(ftfg, 25, false, true);
	static BitmapFont ButtonFont1 = createFont(ftfg, 30, true, true);
	static BitmapFont ButtonFont2 = createFont(ftfg, 30, false, false);

	static Texture LitImage = new Texture(Gdx.files.internal("Lit.png"));
	static Texture ButtonImage = new Texture(Gdx.files.internal("Commode.png"));
	static Texture SolTextureMap = new Texture(Gdx.files.internal("Sol.png"));
	static Texture FondImage = new Texture(Gdx.files.internal("Background.png"));
	static TextureRegion[][] SolImages = TextureRegion.split(SolTextureMap, 64, 32);
	static TextureRegion Fallen = TextureRegion.split(new Texture(Gdx.files.internal("Personnage/ledgeGrab.png")), 20, 40)[1][2];
	Texture PersoRunTextureMap = new Texture(Gdx.files.internal("Personnage/run.png"));
	Animation<TextureRegion> RunAnim1 = Personnage.loadAnim("Personnage/run.png", 4, 2, 8, 0.0625f);
	Animation<TextureRegion> RunAnim2 = Personnage.loadAnim("Personnage/run.png", 4, 2, 8, 0.125f);
	Animation<TextureRegion> RunAnim3 = Personnage.loadAnim("Personnage/run.png", 4, 2, 8, 0.25f);
	ArrayList<Animation<TextureRegion>> ListAnimation = new ArrayList<Animation<TextureRegion>>();
	Texture Fall = new Texture(Gdx.files.internal("Personnage/jump2.png"));

	static Music BackMusic = Gdx.audio.newMusic(Gdx.files.internal("Back.ogg"));
	Sound RonfleSound = Gdx.audio.newSound(Gdx.files.internal("Ronflement.ogg"));
	
	Vector3 MousePos = new Vector3(0f, 0f, 0f);
	static Vector2 PersoPos = new Vector2(-210, 24);

	Boolean onPlay, onOption, IsRonfle = false;

	static int xButton = 0;
	static int yButton = 0;
	int numAnim = 0;

	static float stateTime = 0f, stateTime2 = 0f, fallRotation = 0f, MusicVolume = 0.5f, SoundVolume = 0.5f, sum = 0f;

	public MainMenuScreen(final MyGdxGame game)
	{
		this.game = game;
		ListAnimation.add(RunAnim1);
		ListAnimation.add(RunAnim2);
		ListAnimation.add(RunAnim3);
		BackMusic.setLooping(true);
		BackMusic.setVolume(0);
		BackMusic.play();
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
		while (i < 1280)
		{
			game.batch.draw(SolImages[0][0], i * 128, -30, 128, 64);
			i++;
		}
		TitreFont.draw(game.batch, "Je Veux Dormir ", 150, 642);
		SousTitreFont.draw(game.batch, "My Bed is Home", 335, 544);
		game.batch.draw(ButtonImage, xButton = 0, yButton = 24, 320, 320);

		game.batch.draw(LitImage, 1280 - 320, 24, 320, 320);

		if (MousePos.x > xButton + 50 && MousePos.x < xButton + 270 && MousePos.y > yButton + 135
				&& MousePos.y < yButton + 235)
		{
			ButtonFont1.draw(game.batch, "Play", xButton + 127, yButton + 195);
			onPlay = true;
		} else
		{
			ButtonFont2.draw(game.batch, "Play", xButton + 127, yButton + 195);
			onPlay = false;
		}

		if (MousePos.x > xButton + 50 && MousePos.x < xButton + 270 && MousePos.y > yButton + 35
				&& MousePos.y < yButton + 135)
		{
			ButtonFont1.draw(game.batch, "Options", xButton + 100, yButton + 95);
			onOption = true;
		} else
		{
			ButtonFont2.draw(game.batch, "Options", xButton + 100, yButton + 95);
			onOption = false;
		}

		stateTime += Gdx.graphics.getDeltaTime();
		stateTime2 += Gdx.graphics.getDeltaTime();
		sum += Gdx.graphics.getDeltaTime()/15;
		
		if (sum>1)
		{
			sum = 1;
		}
		
		if (numAnim < 3)
		{
			TextureRegion currentFrame = ListAnimation.get(numAnim).getKeyFrame(stateTime, true);
			game.batch.draw(currentFrame, PersoPos.x, PersoPos.y, 210, 330);
			PersoPos.x += 2f / (numAnim + 1);

		} else
		{
			if(fallRotation>=-90)
			{
				game.batch.draw(Fall, PersoPos.x, PersoPos.y, 60-fallRotation, 0, 210, 330, 1, 1, fallRotation, 0, 0 , 21, 33, false, false);
				fallRotation -=5f;	
				PersoPos.x+=2.5f;
			}
			else
			{
				game.batch.draw(Fallen, PersoPos.x, PersoPos.y, 155, 0, 200, 400, 1, 1, -80);
				if (!IsRonfle)
				{
					RonfleSound.loop(SoundVolume, 1.5f, 0f);
					IsRonfle = true;
				}
				
			}

		}

		game.batch.end();

		if (numAnim < 3 && stateTime2 > 2)
		{
			stateTime2 = 0;
			numAnim++;
		}

		if (Gdx.input.justTouched())
		{
			if (onPlay)
			{
				BackMusic.setVolume(MusicVolume);
				RonfleSound.stop();
				game.setScreen(new GameScreen(game));
				dispose();
			} else if (onOption)
			{
				numAnim = 3;
				BackMusic.setVolume(MusicVolume);
				game.setScreen(new OptionScreen(game));
				dispose();
			}
		}
		
		BackMusic.setVolume(MusicVolume*sum);
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


	}

}
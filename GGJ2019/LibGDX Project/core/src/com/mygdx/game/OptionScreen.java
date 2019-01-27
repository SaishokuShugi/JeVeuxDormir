package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class OptionScreen implements Screen
{
	final MyGdxGame game;
	private OrthographicCamera camera;
	SpriteBatch batch;

    static int xButton = 0;
    static int yButton = 0;
    Vector3 MousePos = new Vector3(0f, 0f, 0f);
    Boolean onRetour, onMusicUp, onMusicDown, onSoundUp, onSoundDown;

	
	public OptionScreen (final MyGdxGame game) {
		this.game = game;
		// create the camera and the SpriteBatch
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1280 , 720);
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
		game.batch.draw(MainMenuScreen.FondImage, 0, 0, 1280, 720);
		int i = 0;
		while (i < 1280)
		{
			game.batch.draw(MainMenuScreen.SolImages[0][0], i * 128, -30, 128, 64);
			i++;
		}
		game.batch.draw(MainMenuScreen.LitImage, 1280 - 320, 24, 320, 320);
        game.batch.draw(MainMenuScreen.ButtonImage, xButton = 0, yButton = 24, 320, 320);
		game.batch.draw(MainMenuScreen.Fallen, MainMenuScreen.PersoPos.x, MainMenuScreen.PersoPos.y, 155, 0, 200, 400, 1, 1, -80);

        if (MousePos.x > xButton + 50 && MousePos.x < xButton + 270 && MousePos.y > yButton + 135
                && MousePos.y < yButton + 235) {
            MainMenuScreen.ButtonFont1.draw(game.batch, "Retour", xButton + 127, yButton + 195);
            onRetour = true;
        } else {
            MainMenuScreen.ButtonFont2.draw(game.batch, "Retour", xButton + 127, yButton + 195);
            onRetour = false;
        }

        xButton = (int) (1280 * 0.3);
        yButton = (int) (720 * 0.6);
        MainMenuScreen.SousTitreFont.draw(game.batch, "Volume de la musique", xButton, yButton);
        xButton = (int) (1280 * 0.6);
        if (MousePos.x > xButton - 25 && MousePos.x < xButton + 25 && MousePos.y > yButton - 25
                && MousePos.y < yButton + 25) {
            MainMenuScreen.ButtonFont1.draw(game.batch, "<", xButton, yButton);
            onMusicDown = true;
        } else {
            MainMenuScreen.ButtonFont2.draw(game.batch, "<", xButton, yButton);
            onMusicDown = false;
        }
        xButton = (int) (1280 * 0.65);
        MainMenuScreen.SousTitreFont.draw(game.batch, String.format("%.0f", MainMenuScreen.MusicVolume * 100), xButton, yButton);
        xButton = (int) (1280 * 0.7);
        if (MousePos.x > xButton - 25 && MousePos.x < xButton + 25 && MousePos.y > yButton - 25
                && MousePos.y < yButton + 25) {
            MainMenuScreen.ButtonFont1.draw(game.batch, ">", xButton, yButton);
            onMusicUp = true;
        } else {
            MainMenuScreen.ButtonFont2.draw(game.batch, ">", xButton, yButton);
            onMusicUp = false;
        }

        xButton = (int) (1280 * 0.3);
        yButton = (int) (720 * 0.5);
        MainMenuScreen.SousTitreFont.draw(game.batch, "Volume des sons", xButton, yButton);
        xButton = (int) (1280 * 0.6);
        if (MousePos.x > xButton - 25 && MousePos.x < xButton + 25 && MousePos.y > yButton - 25
                && MousePos.y < yButton + 25) {
            MainMenuScreen.ButtonFont1.draw(game.batch, "<", xButton, yButton);
            onSoundDown = true;
        } else {
            MainMenuScreen.ButtonFont2.draw(game.batch, "<", xButton, yButton);
            onSoundDown = false;
        }
        xButton = (int) (1280 * 0.65);
        MainMenuScreen.SousTitreFont.draw(game.batch, String.format("%.0f", MainMenuScreen.SoundVolume * 100), xButton, yButton);
        xButton = (int) (1280 * 0.7);
        if (MousePos.x > xButton - 25 && MousePos.x < xButton + 25 && MousePos.y > yButton - 25
                && MousePos.y < yButton + 25) {
            MainMenuScreen.ButtonFont1.draw(game.batch, ">", xButton, yButton);
            onSoundUp = true;
        } else {
            MainMenuScreen.ButtonFont2.draw(game.batch, ">", xButton, yButton);
            onSoundUp = false;
        }
		game.batch.end();

        if (Gdx.input.justTouched()) {
            if (onRetour) {
                MainMenuScreen.BackMusic.setVolume(MainMenuScreen.MusicVolume);
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }
        if (Gdx.input.isTouched()) {
            if (onMusicUp) {
                if (MainMenuScreen.MusicVolume < 0.99f) {
                    MainMenuScreen.MusicVolume += 0.01;
                }
            }
            if (onMusicDown) {
                if (MainMenuScreen.MusicVolume > 0.01f) {
                    MainMenuScreen.MusicVolume -= 0.01;
                }
            }
            if (onSoundUp) {
                if (MainMenuScreen.SoundVolume < 0.99f) {
                    MainMenuScreen.SoundVolume += 0.01;
                }
            }
            if (onSoundDown) {
                if (MainMenuScreen.SoundVolume > 0.01f) {
                    MainMenuScreen.SoundVolume -= 0.01;
                }
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

	}

}

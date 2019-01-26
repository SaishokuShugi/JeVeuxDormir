package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

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

	Texture froisdq;

	Personnage perso;
	FrameBuffer Frameb;
	TextureRegion Fbtex;

	public static float scale_factor;

	public static World world;
	float time =0;
	Controller cont;
	Box2DDebugRenderer debugRenderer;
	public ArrayList<Interactible> blocks = new ArrayList<Interactible>();
	
	ShaderProgram shader;
	
	void generateMap() {
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, i++, 0f, 0, 0, 0)));
		blocks.add(new Movable("Chaise.png", 1, 1, 1, 1.58f, 2f, 0, 0, .5f));
		blocks.add(new Block("Lit.png", 1, 1, 1, 13f, 1f, 0, 0, 0));
		blocks.add(new Block("Armoire.png", 1, 1, 1, 8f, 1f, 0, 0, 0));
		blocks.add(new Block("Table.png", 1, 1, 1, 4f, 1f, 0, 0, 0));
		blocks.add(new Movable("Commode.png", 1, 1, 1, 4f, 2f, 0, 0, 0));
		perso = new Personnage(10, 10, 0f, 2f, 1, 10, 0,.5f);
	}
	
	
	void  loadShader() {
		String vertexShader = "attribute vec4 a_position;    \n" + 
                "attribute vec4 a_color;\n" +
                "attribute vec2 a_texCoord0;\n" + 
                "uniform mat4 u_projTrans;\n" + 
                "varying vec2 v_texCoords;" + 
                "void main()                  \n" + 
                "{                            \n" +
                "   v_texCoords = a_texCoord0; \n" + 
                "   gl_Position =  u_projTrans * a_position;  \n"      + 
                "}" ;
		String fragmentShader ="#version 120 \n"+
                "uniform float time;\n"+
				"varying vec2 v_texCoords;\n" + 
                  "uniform sampler2D u_texture;\n"+ 
                  "vec3 blur(vec2 uv){\n"+
                  "vec3 c= vec3(0);\n"
                  + "for(int i =-1;i<1;i++){\n"
                  + "c+=texture2D(u_texture,uv+i/100.).rgb;}"
                  + "return c/3.;"+
                  "}"+
                  "void main()                                  \n" + 
                  "{                                            \n"
                  + "vec2 uv = v_texCoords;"
                  + "uv.y+=.01*sin(uv.x*100.-time);"
                  +"vec4 color = texture2D(u_texture, uv);\n"+
                  "float avg =(color.x+color.y+color.z)/3.; \n"+
                  "  gl_FragColor = vec4(mix(vec3(v_texCoords,avg),color.rgb, 1),color.a) ;\n" +
                  "}";	
		shader = new ShaderProgram(vertexShader, fragmentShader);
		if (!shader.isCompiled()) {
			System.err.println(shader.getLog());
			System.exit(0);
		}
		if (shader.getLog().length()!=0)
			System.out.println(shader.getLog());
	}
	
	
	public GameScreen(final MyGdxGame game) {
		this.game = game;
		// load the images for the monkeys
		img = new TextureRegion();
		img2 =new Texture("Background.png");
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

		// create the camera and the SpriteBatch
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		scale_factor = Gdx.graphics.getWidth() / (32 * 15f);

		if (!Controllers.getControllers().isEmpty())
			cont = Controllers.getControllers().get(0);
		Box2D.init();
		world = new World(new Vector2(0, -9.81f * scale_factor*32), true);
		debugRenderer = new Box2DDebugRenderer();
		generateMap();
		
		Frameb = new FrameBuffer(Format.RGBA8888,Gdx.graphics.getWidth() , Gdx.graphics.getHeight(), false);
		loadShader();
		batch.setShader(null);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		batch.setShader(null);

		float deltat = Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 0, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Fbtex = new TextureRegion(Frameb.getColorBufferTexture());
		Fbtex.flip(false,true);
		Frameb.begin();
		batch.begin();
		
		
		batch.draw(img2,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		for (Interactible block : blocks) {
			img = block.getImages()[block.tile];
			batch.draw(img, block.getBodyXToImage(), block.getBodyYToImage(), img.getRegionWidth() * scale_factor,
					img.getRegionHeight() * scale_factor);
		}
		perso.setAnimTime(time+=deltat);
		batch.draw(perso.getCurrentFrame(), perso.getBodyXToImage(), perso.getBodyYToImage()
				,perso.getCurrentFrame().getRegionWidth() * scale_factor,perso.getCurrentFrame().getRegionHeight() * scale_factor);
		
		batch.draw(staminaE_L, 10, Gdx.graphics.getBackBufferHeight()-50);
		for(int i =0;i<32*9;batch.draw(staminaE_C, 10+(i+=32), Gdx.graphics.getBackBufferHeight()-50));
		batch.draw(staminaE_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-50);
		
		batch.draw(stamina_L, 10, Gdx.graphics.getBackBufferHeight()-50);
		for(int i =32;i<(int)perso.getStamina()/perso.getStaminaMax()*290;batch.draw(stamina_C, 10+(i++), Gdx.graphics.getBackBufferHeight()-50));
		if(perso.getStamina()==perso.getStaminaMax())batch.draw(stamina_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-50);
		
		batch.draw(tempE_L, 10, Gdx.graphics.getBackBufferHeight()-100);
		for(int i =0;i<32*9;batch.draw(tempE_C, 10+(i+=32), Gdx.graphics.getBackBufferHeight()-100));
		batch.draw(tempE_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-100);
		
		batch.draw(temp_L, 10, Gdx.graphics.getBackBufferHeight()-100);
		for(int i =32;i<(int)perso.getFroid()/perso.getFroidMax()*290;batch.draw(temp_C, 10+(i++), Gdx.graphics.getBackBufferHeight()-100));
		if(perso.getFroid()==perso.getFroidMax())batch.draw(temp_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-100);
		
		batch.flush();
		Frameb.end();
		
		batch.setShader(shader);

		float[] t = new float[1];
		t[0]=time;
	    shader.setUniform1fv( shader.getUniformLocation("time"),t, 0, 1);
	    
		batch.draw(Fbtex, 0, 0);





		//debugRenderer.render(world, camera.combined);

		batch.end();

		world.step(Math.min(.015f, deltat), 6, 2);
		controls();

	}
	
	void controls() {
		boolean isLeftPressed = Gdx.input.isKeyPressed(Keys.Q);
		boolean isRightPressed = Gdx.input.isKeyPressed(Keys.D);
		boolean isUpPressed = Gdx.input.isKeyPressed(Keys.SPACE);
		
		if(isRightPressed)
			perso.run(.2f);
		if(!(isRightPressed||isLeftPressed))
			perso.idle(.5f);
		if(isUpPressed)
			perso.jump(.5f);
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
	}
}

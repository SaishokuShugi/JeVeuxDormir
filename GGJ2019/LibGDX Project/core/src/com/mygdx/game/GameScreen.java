package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

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

    public static int mapID = 1;

    public static int rayIter = 40;

	float time =0;
	Controller cont;
	Box2DDebugRenderer debugRenderer;
	public ArrayList<Interactible> blocks = new ArrayList<Interactible>();
	public ArrayList<Interactible> sensors = new ArrayList<Interactible>();
	public ArrayList<Collectible> items = new ArrayList<Collectible>();
	
	ShaderProgram shader;
	
	Vector3 posCamera,newPosCamera;
	Vector3 posCameraInit = new Vector3(7.5f, 4.0f, 0f);
	int tailleMap3 = 0;


    public GameScreen(final MyGdxGame game) {
        this.game = game;
        // load the images for the monkeys
        img = new TextureRegion();
        img2 = new Texture("Background2.png");

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

        DeclareFragStrings();

        scale_factor = Gdx.graphics.getWidth() / (32 * 15f);

        // create the camera and the SpriteBatch
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 15, 15 * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
        


        if (!Controllers.getControllers().isEmpty())
            cont = Controllers.getControllers().get(0);
        Box2D.init();
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
		generateMap1();

        Frameb = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        loadShader(gameShader0);
        batch.setShader(null);
    }

    public void cleanMap() {
        blocks.clear();
        sensors.clear();
        items.clear();
        world = new World(new Vector2(0, -9.81f), true);
    }
    
    public void generateMap1() {
    	cleanMap();
        for (float i = 0; i < 15; blocks.add(new Block("Sol.png", 2, 2, 4, i++, 0f, 0.5f, 1, 0, false))) ;
        blocks.add(new Movable("Chaise.png", 1, 1, 1, 1.58f, 3f, 0, 1, .5f, false));
        sensors.add(new Block("Lit.png", 1, 1, 1, 13f, 1f, 0, 0, 0, true));
        blocks.add(new Block("Armoire.png", 1, 1, 1, 8f, 1f, 0, 0, 0, false));
        blocks.add(new Block("Table.png", 1, 1, 1, 4f, 1f, 0, 0, 0, false));
        blocks.add(new Movable("Commode.png", 1, 1, 1, 4f, 2f, 0.1f, 1, 0, false));
        perso = new Personnage(10, 10, 0f, 2f, 1, 3.5f, 0, .5f);
        
	}

    public void generateMap2() {
    	cleanMap();
		//Mur et sol
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, i++, 0f, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, -1f, i++, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, 15f, i++, 0f, 1, 0,false)));

		//Fleurs
		blocks.add(new Movable("Plante.png", 1, 1, 1, 5f, 3f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 7f, 3f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 6f, 4f, 0, 1, 0,false));

        //Tapis*
		blocks.add(new Movable("Objet.png", 5, 6, 27, 4f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 5f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 6f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 7f, 2f, 0.1f,1, 0,false));
		blocks.add(new Movable("Objet.png", 5, 6, 27, 6f, 3f, 0.1f,1, 0,false));

        //Static
		sensors.add(new Block("Lit.png", 1, 1, 1, 13f, 1f, 0, 1, 0,true));
		blocks.add(new Block("Armoire.png", 1, 1, 1, 10f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 6f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 4f, 1f, 0, 1, 0,false));

        //Personnage
        perso = new Personnage(10, 10, 2f, 1f, 1, 3.5f, 0, .5f);
	}
    public void generateMap3() {
		cleanMap();
		//Mur et sol
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, i++, 0f, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, -1f, i++, 0f, 1, 0,false)));
		for(float i = 0;i<15;blocks.add(new Block("Sol.png", 2, 2, 4, 15f, i++, 0f, 1, 0,false)));
		
		//Fleurs
		blocks.add(new Movable("Plante.png", 1, 1, 1, 9f, 2f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 8f, 2f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 7f, 2f, 0, 1, 0,false));
		blocks.add(new Movable("Plante.png", 1, 1, 1, 6f, 2f, 0, 1, 0,false));
		
		//Tapis*
		blocks.add(new Movable("Chaise.png", 1, 1, 1, 3f, 2f, 0.1f,0.7f, 0,false));
		blocks.add(new Movable("Chaise.png", 1, 1, 1, 3f, 1f, 0.1f,0.7f, 0,false));
		
		//Static
		sensors.add(new Block("Lit.png", 1, 1, 1, 13f, 1f, 0, 1, 0,true));
		blocks.add(new Block("Biblioth�que.png", 1, 1, 1, 11.1f, 4f, 0, 1, 0,false));
		blocks.add(new Block("Armoire.png", 1, 1, 1, 11.1f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 6f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Table.png", 1, 1, 1, 4f, 1f, 0, 1, 0,false));
		blocks.add(new Block("Commode.png", 1, 1, 1, 9.05f, 1f, 0, 1, 0,false));
		
		//Personnage
        perso = new Personnage(10, 10, 2f, 1f, 1, 3.5f, 0, .5f);
	}
	String gameShader0;
	String backMenuShader;
	
	void DeclareFragStrings() {
		backMenuShader ="#version 120 																			\n"
                  + "uniform float time;																		\n"
				  + "varying vec2 v_texCoords;																	\n" 
                  + "uniform sampler2D u_texture;																\n"
                  + "const vec2 resolution= vec2("+Gdx.graphics.getWidth()+","+Gdx.graphics.getHeight()+");		\n" 
                  + "vec3 blur(vec2 fc){																		\n"
                  + "vec3 c= vec3(0);																			\n"
                  + "for(int i =-6;i<=6;i++){																	\n"
                  + "for(int j =-6;j<=6;j++){																	\n"
                  + "c+=texture2D(u_texture,(fc+vec2(i,j)*2.)/resolution).rgb;}}								\n"
                  + "return c/169.;																				\n"
                  + "}																							\n"
                  + "void main()                                  												\n"
                  + "{     												                                       	\n"
                  + "vec2 uv = v_texCoords;																		\n"
                  + "vec2 fc = floor(uv*resolution);															\n"
                  + "vec4 color = texture2D(u_texture, uv);														\n"
                  + "gl_FragColor = vec4(mix(blur(fc),vec3(.5*smoothstep(1.5,0.,distance(uv,vec2(.5)))),.7),1);	\n"
                  + "}";
		
		gameShader0="#version 120 																			\n"
                  + "uniform float time;																	\n"
				  + "varying vec2 v_texCoords;																\n" 
                  + "uniform sampler2D u_texture;															\n"
                  + "const vec2 resolution= vec2("+Gdx.graphics.getWidth()+","+Gdx.graphics.getHeight()+");	\n"
                  		+ "float bayer2(vec2 v)\n" + 
                  		"{\n" + 
                  		"    v=floor(v);\n" + 
                  		" return fract(v.y*v.y*.75+v.x*.5);   \n" + 
                  		"}\n" + 
                  		"float bayer4(vec2 v)\n" + 
                  		"{\n" + 
                  		"    return bayer2(.5*v)*.25+bayer2(v);\n" + 
                  		"}\n" + 
                  		"float bayer8(vec2 v)\n" + 
                  		"{\n" + 
                  		"    return bayer4(.5*v)*.25+bayer2(v);\n" + 
                  		"}"
                  		+ "vec2 hash22(vec2 p)\n" + 
                  		"	{\n" + 
                  		"		vec3 p3 = fract(vec3(p.xyx) * vec3(443.897, 441.423, 437.195));\n" + 
                  		"	    p3 += dot(p3, p3.yzx+19.19);\n" + 
                  		"	    return fract((p3.xx+p3.yz)*p3.zy)-.5;\n" + 
                  		"\n" + 
                  		"	}\n" + 
                  		""
                  + "const int samples = "+rayIter+";"
                  + "float ray(vec2 uv){																	\n"
                  + "float dith = bayer8(uv*resolution);"
                  + "float a=0;"
                  + "vec2 lp = vec2(.75);"
                  + "vec2 ld = lp-uv;"
                + "if(texture2D(u_texture,uv).g>=.8 && length(texture2D(u_texture,uv).rb)<.1)return -1.;											\n"
                  + "uv+=ld/samples*dith;"
                  + "for(int i=0;i++<samples;){"
                  + "float d = texture2D(u_texture,uv).g>=.8"
                  + "&&length(texture2D(u_texture,uv).rb)<.1?1.:0.;"
                  + "a+=d/float(samples);"
                  + "uv+=ld/float(samples);"
                  + "}"
                  + "return a;"
                  + "}"
                  + "void main(){                                 											\n"
                  + "vec2 uv = v_texCoords;																	\n"
                  + "vec4 color = texture2D(u_texture, uv);													\n"
                  + "float r = ray(uv);"
                  + "if(r<0){"
                  + "vec2 u = floor(uv*20);\n" 
                  + "vec2 off1 = hash22(u);\n" + 
                  "vec2 off2 =  vec2(0,1)+hash22(u+vec2(0,1));\n" + 
                  "vec2 off3 =  vec2(1)+hash22(u+vec2(1));\n" + 
                  "vec2 off4 =  vec2(1,0)+hash22(u+vec2(1,0));\n"
                  + "u=fract(uv*20);" + 
                  "float dist = min(min(distance(u,off1),distance(u,off2)),min(distance(u,off3),distance(u,off4)));"
                  + "color.rgb=mix(vec3(.1,.1,.3),vec3(1),smoothstep(.06,0,dist));"
                  + "r=.15;"
                  + "}"
                  + "gl_FragColor = vec4(mix(color.rgb*vec3(.45,.5,.8),vec3(.7,.7,.8),pow(r,.85)),1) ;								\n"
                  + "}";
	}
	void  loadShader(String frag) {
		String vertexShader = "attribute vec4 a_position;    	\n" + 
                "attribute vec4 a_color;						\n" +
                "attribute vec2 a_texCoord0;					\n" + 
                "uniform mat4 u_projTrans;						\n" + 
                "varying vec2 v_texCoords;						\n" + 
                "void main()                  					\n" + 
                "{                            					\n" +
                "   v_texCoords = a_texCoord0; 					\n" + 
                "   gl_Position =  u_projTrans * a_position;  	\n" + 
                "}" ;
		String fragmentShader =	frag;
		shader = new ShaderProgram(vertexShader, fragmentShader);
		if (!shader.isCompiled()) {
			System.err.println(shader.getLog());
			System.exit(0);
		}
		if (shader.getLog().length()!=0)
			System.out.println(shader.getLog());
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		batch.setShader(null);
		posCamera = camera.position;
		switch(mapID)
		{
			case 1:
			{
			}
			case 2:
			{
				camera.position.lerp(posCameraInit, 0.1f);
				break;
			}
			
			case 3:
			{
				if (perso.getBody().getPosition().x<7.5)
				{
					camera.position.lerp(posCameraInit, 0.1f);
				}
				else if (perso.getBody().getPosition().x<tailleMap3-7.5)
				{
					newPosCamera = posCamera;
					newPosCamera.x = perso.getBody().getPosition().x;
					camera.position.lerp(newPosCamera, 0.1f);
				}
				else
				{
					newPosCamera = posCamera;
					newPosCamera.x = tailleMap3-7.5f;
					camera.position.lerp(newPosCamera, 0.1f);
				}
			}
		}
		float deltat = Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Fbtex = new TextureRegion(Frameb.getColorBufferTexture());
		Fbtex.flip(false,true);
		Frameb.begin();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		
		//debugRenderer.render(world, camera.combined);
		batch.draw(img2,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		for (Interactible block : blocks) {
			block.getBody().setLinearVelocity(block.getBody().getLinearVelocity().x/1.1f, block.getBody().getLinearVelocity().y);
			img = block.getImages()[block.tile];
			batch.draw(img, block.getBodyXToImage(), block.getBodyYToImage(), img.getRegionWidth() * scale_factor,
					img.getRegionHeight() * scale_factor);
		}
		for (Interactible block : sensors) {
			img = block.getImages()[block.tile];
			batch.draw(img, block.getBodyXToImage(), block.getBodyYToImage(), img.getRegionWidth() * scale_factor,
					img.getRegionHeight() * scale_factor);
		}
		for (Interactible block : items) {
			img = block.getImages()[block.tile];
			batch.draw(img, block.getBodyXToImage(), block.getBodyYToImage(), img.getRegionWidth() * scale_factor,
					img.getRegionHeight() * scale_factor);
		}


		perso.setAnimTime(time+=deltat);
		batch.draw(perso.getCurrentFrame(), perso.getBodyXToImage()+(1-(perso.getFlip()+1)/2f)*perso.getCurrentFrame().getRegionWidth() * scale_factor, perso.getBodyYToImage()
				,perso.getCurrentFrame().getRegionWidth()* scale_factor*perso.getFlip(),perso.getCurrentFrame().getRegionHeight() * scale_factor);
		
		batch.flush();
		Frameb.end();
		
		batch.setShader(shader);

		float[] t = new float[1];
		t[0]=time;
	    shader.setUniform1fv( shader.getUniformLocation("time"),t, 0, 1);
	    
		batch.draw(Fbtex, 0, 0);

		batch.setShader(null);
		
		batch.draw(staminaE_L, 10, Gdx.graphics.getBackBufferHeight()-50);
		for(int i =0;i<32*9;batch.draw(staminaE_C, 10+(i+=32), Gdx.graphics.getBackBufferHeight()-50));
		batch.draw(staminaE_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-50);
		
		if(perso.getStamina()>0)batch.draw(stamina_L, 10, Gdx.graphics.getBackBufferHeight()-50);
		for(int i =32;i<(int)perso.getStamina()/perso.getStaminaMax()*290;batch.draw(stamina_C, 10+(i++), Gdx.graphics.getBackBufferHeight()-50));
		if(perso.getStamina()==perso.getStaminaMax())batch.draw(stamina_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-50);
		
		batch.draw(tempE_L, 10, Gdx.graphics.getBackBufferHeight()-100);
		for(int i =0;i<32*9;batch.draw(tempE_C, 10+(i+=32), Gdx.graphics.getBackBufferHeight()-100));
		batch.draw(tempE_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-100);
		
		if(perso.getStamina()>0)batch.draw(temp_L, 10, Gdx.graphics.getBackBufferHeight()-100);
		for(int i =32;i<(int)perso.getFroid()/perso.getFroidMax()*290;batch.draw(temp_C, 10+(i++), Gdx.graphics.getBackBufferHeight()-100));
		if(perso.getFroid()==perso.getFroidMax())batch.draw(temp_R, 10+32*10, Gdx.graphics.getBackBufferHeight()-100);



		batch.end();
		
		checkTriggers();

        perso.controls();

		world.step(Math.min(deltat,.15f), 6, 2);
	}
	
	void checkTriggers() {
		for (Interactible a : sensors) {
			perso.checkCollision(a, 0, 0, this);
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
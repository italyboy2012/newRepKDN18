package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
//import com.mygdx.game.SuperKoalio.Koala.State;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Super Mario Brothers-like very basic platformer, using a tile map built using <a href="http://www.mapeditor.org/">Tiled</a> and a
 * tileset and sprites by <a href="http://www.vickiwenderlich.com/">Vicky Wenderlich</a></p>
 *
 * Shows simple platformer collision detection as well as on-the-fly map modifications through destructible blocks!
 * @author mzechner */
public class SuperKoalio extends ApplicationAdapter {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
        
	private Koala koala;
        private Texture koalaTexture;
        private Animation<TextureRegion> stand;
	private Animation<TextureRegion> walk;
	private Animation<TextureRegion> jump;
        
        private Koala koala2;
        
        private Koala activeKoala = null;
        
        private ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
        
        private boolean soundOnOff = true;
        private Sound backgroundMusic;
        private Sound jumpSound;
        private Sound shootSound;
        private long idBackgroundMusic;
        
        private float koalaPositionWhenShootedWidth = 0;
        private float koalaPositionWhenShootedHeight = 0;
        
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};
	private Array<Rectangle> tiles = new Array<Rectangle>();

	private static final float GRAVITY = -2.5f;

	private boolean debug = false;
	private ShapeRenderer debugRenderer;

	@Override
	public void create () {
		// load the koala frames, split them, and assign them to Animations
                koalaTexture = new Texture(activeKoala.texture);
                //FileHandle = new FileHandle ("core/assets/data/maps/tiled/super-koalio/koalio.png")); 
                TextureRegion[] regionsKoala = TextureRegion.split(koalaTexture, 18, 26)[0];

		stand = new Animation(0, regionsKoala[0]);
		jump = new Animation(0, regionsKoala[1]);
		walk = new Animation(0.15f, regionsKoala[2], regionsKoala[3], regionsKoala[4]);
		walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		// figure out the width and height of the koala for collision
		// detection and rendering by converting a koala frames pixel
		// size into world units (1 unit == 16 pixels)
		Koala.WIDTH = 1 / 16f * regionsKoala[0].getRegionWidth();
		Koala.HEIGHT = 1 / 16f * regionsKoala[0].getRegionHeight();

                //Set the Player-position for the projectile
                koalaPositionWhenShootedWidth = Koala.WIDTH;
                koalaPositionWhenShootedHeight = Koala.HEIGHT;
		// load the map, set the unit scale to 1/16 (1 unit == 16 pixels)
		map = new TmxMapLoader().load("flatmap2.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);

		// create an orthographic camera, shows us 30x20 units of the world
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();

		// create the Koala we want to move around the world
		koala = new Koala();
		koala.position.set(20, 20);
                
                koala2 = new Koala();
		koala2.position.set(18, 7);
                
                koala2.state = Koala.State.Standing;
                
                activeKoala = koala;
                
                shootSound = Gdx.audio.newSound(Gdx.files.internal("shootSound.mp3"));
                backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("backgroundMusic.mp3"));
                jumpSound = Gdx.audio.newSound(Gdx.files.internal("jumpSound.mp3"));
                
                Options optionWindow = new Options(this);
                optionWindow.setVisible(true);

		debugRenderer = new ShapeRenderer();
                
                playBackgroundMusic();
	}

	@Override
	public void render () {
		// clear the screen
		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// get the delta time
		float deltaTime = Gdx.graphics.getDeltaTime();

		// update the koala (process input, collision detection, position update)
		updateKoala(deltaTime);

		// let the camera follow the koala, x-axis only
		camera.position.x = activeKoala.position.x;
                camera.position.y = activeKoala.position.y + 4;
		camera.update();

		// set the TiledMapRenderer view based on what the
		// camera sees, and render the map
		renderer.setView(camera);
		renderer.render();

		// render the koala
		renderKoalas(deltaTime);
                
                // update the projectile (process input, collision detection, position update)
		updateProjectile(deltaTime);
                // render the projectile if it exists
                renderProjectile(deltaTime);
                
                try{
                    checkForActivePlayer();
                    
                } catch(Exception e) {
                    System.out.println("Fehler beim Ausführen von: checkForActivePlayer()");
                }
                
		// render debug rectangles
		if (debug) renderDebug();
	}

	private void updateKoala (float deltaTime) {
		if (deltaTime == 0) return;

		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		activeKoala.stateTime += deltaTime;

		// check input and apply to velocity & state
		if ((Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W) || isTouched(0.5f, 1)) && activeKoala.grounded) {
			activeKoala.velocity.y += activeKoala.JUMP_VELOCITY;
			activeKoala.state = Koala.State.Jumping;
			activeKoala.grounded = false;
                        playJumpSound();
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A) || isTouched(0, 0.25f)) {
			activeKoala.velocity.x = -Koala.MAX_VELOCITY;
			if (activeKoala.grounded) activeKoala.state = Koala.State.Walking;
			activeKoala.facesRight = false;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D) || isTouched(0.25f, 0.5f)) {
			activeKoala.velocity.x = Koala.MAX_VELOCITY;
			if (activeKoala.grounded) activeKoala.state = Koala.State.Walking;
			activeKoala.facesRight = true;
		}

		if (Gdx.input.isKeyJustPressed(Keys.B))
			debug = !debug;

		// apply gravity if we are falling
		activeKoala.velocity.add(0, GRAVITY);

		// clamp the velocity to the maximum, x-axis only
		activeKoala.velocity.x = MathUtils.clamp(activeKoala.velocity.x,
				-Koala.MAX_VELOCITY, Koala.MAX_VELOCITY);

		// If the velocity is < 1, set it to 0 and set state to Standing
		if (Math.abs(activeKoala.velocity.x) < 1) {
			activeKoala.velocity.x = 0;
			if (activeKoala.grounded) activeKoala.state = Koala.State.Standing;
		}

		// multiply by delta time so we know how far we go
		// in this frame
		activeKoala.velocity.scl(deltaTime);

		// perform collision detection & response, on each axis, separately
		// if the koala is moving right, check the tiles to the right of it's
		// right bounding box edge, otherwise check the ones to the left
		Rectangle koalaRect = rectPool.obtain();
		koalaRect.set(activeKoala.position.x, activeKoala.position.y, Koala.WIDTH, Koala.HEIGHT);
		int startX, startY, endX, endY;
		if (activeKoala.velocity.x > 0) {
			startX = endX = (int)(activeKoala.position.x + Koala.WIDTH + activeKoala.velocity.x);
		} else {
			startX = endX = (int)(activeKoala.position.x + activeKoala.velocity.x);
		}
		startY = (int)(activeKoala.position.y);
		endY = (int)(activeKoala.position.y + Koala.HEIGHT);
		getTiles(startX, startY, endX, endY, tiles);
		koalaRect.x += activeKoala.velocity.x;
		for (Rectangle tile : tiles) {
			if (koalaRect.overlaps(tile)) {
				activeKoala.velocity.x = 0;
				break;
			}
		}
		koalaRect.x = activeKoala.position.x;

		// if the koala is moving upwards, check the tiles to the top of its
		// top bounding box edge, otherwise check the ones to the bottom
		if (activeKoala.velocity.y > 0) {
			startY = endY = (int)(activeKoala.position.y + Koala.HEIGHT + activeKoala.velocity.y);
		} else {
			startY = endY = (int)(activeKoala.position.y + activeKoala.velocity.y);
		}
		startX = (int)(activeKoala.position.x);
		endX = (int)(activeKoala.position.x + Koala.WIDTH);
		getTiles(startX, startY, endX, endY, tiles);
		koalaRect.y += activeKoala.velocity.y;
		for (Rectangle tile : tiles) {
			if (koalaRect.overlaps(tile)) {
				// we actually reset the koala y-position here
				// so it is just below/above the tile we collided with
				// this removes bouncing :)
				if (activeKoala.velocity.y > 0) {
					activeKoala.position.y = tile.y - Koala.HEIGHT;
					// we hit a block jumping upwards, let's destroy it!
					TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("main");
					layer.setCell((int)tile.x, (int)tile.y, null);
				} else {
					activeKoala.position.y = tile.y + tile.height;
					// if we hit the ground, mark us as grounded so we can jump
					activeKoala.grounded = true;
				}
				activeKoala.velocity.y = 0;
				break;
			}
		}
		rectPool.free(koalaRect);

		// unscale the velocity by the inverse delta time and set
		// the latest position
		activeKoala.position.add(activeKoala.velocity);
		activeKoala.velocity.scl(1 / deltaTime);

		// Apply damping to the velocity on the x-axis so we don't
		// walk infinitely once a key was pressed
		activeKoala.velocity.x *= Koala.DAMPING;
	}
        
        private void updateProjectile(float deltaTime) {
            
            if (deltaTime == 0) {
                return;
            }

            if (deltaTime > 0.1f) deltaTime = 0.1f;
            
            
            
            if (Gdx.input.isKeyPressed(Keys.SPACE) && activeKoala.shootActive) {
                activeKoala.shootActive = false;
                
                Projectile projectile = new Projectile();
                
                Texture projectileTexture;
                Animation<TextureRegion> one;
                Animation<TextureRegion> two;
                Animation<TextureRegion> three;
                
                projectile.stateTime += deltaTime;
                
                projectileTexture = new Texture("koalio.png");
                TextureRegion[] regionsProjectile = TextureRegion.split(projectileTexture, 18, 26)[0];

		one = new Animation(0, regionsProjectile[0]);
		two = new Animation(0, regionsProjectile[1]);
		three = new Animation(0.15f, regionsProjectile[2], regionsProjectile[3], regionsProjectile[4]);
		//walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		// figure out the width and height of the koala for collision
		// detection and rendering by converting a koala frames pixel
		// size into world units (1 unit == 16 pixels)
		Projectile.WIDTH = 1 / 16f * regionsProjectile[0].getRegionWidth();
		Projectile.HEIGHT = 1 / 16f * regionsProjectile[0].getRegionHeight();
                
                projectile.positionx = activeKoala.position.x;
                
                projectile.positiony = activeKoala.position.y;
                
                playShootSound();
                
                if (activeKoala.facesRight) {
                    projectile.facing = "right";
                } else {
                    projectile.facing = "left";
                }
                
                //projectile.position.set(30, 20);
                projectileList.add(projectile);
                System.out.println(projectileList.size() + "Projektile");
                
                
            }
            
            
        }
        
        private void renderProjectile(float deltaTime) {
            Projectile currentProjectile = null;
            try {
                for(int i = 0; i < projectileList.size(); i++) {
                    currentProjectile = null;
                    currentProjectile = projectileList.get(i);

                    TextureRegion frame = null;
                    Batch batch = renderer.getBatch();
                    frame = stand.getKeyFrame(currentProjectile.stateTime);
                    batch.begin();
                    //batch.draw(frame, projectile.position.x, projectile.position.y, Projectile.WIDTH, Projectile.HEIGHT);

                    if(currentProjectile.facing == "right") {
                        currentProjectile.positionx = currentProjectile.positionx + Projectile.MAX_VELOCITY;
                        batch.draw(frame, currentProjectile.positionx, currentProjectile.positiony, Projectile.WIDTH, Projectile.HEIGHT);
                    } else {
                        currentProjectile.positionx = currentProjectile.positionx - Projectile.MAX_VELOCITY;
                        batch.draw(frame, currentProjectile.positionx + currentProjectile.WIDTH, currentProjectile.positiony, -Projectile.WIDTH, Projectile.HEIGHT);
                    }

                    batch.end();
                }
            
            } catch(Exception e) {
                System.out.println("FEHLER_______________");
                
            }
            
        }
        
        private void checkForActivePlayer() throws InterruptedException {
            if(!activeKoala.shootActive && activeKoala == koala) {
                
                activeKoala = null;
                activeKoala = koala2;
                koala.shootActive = true;
                //TimeUnit.SECONDS.sleep(1);
                //changeCameraPositionToActiveKoalaOnASmoothWay();
                Thread.sleep(150);
            } else if(!activeKoala.shootActive && activeKoala == koala2){
               
                activeKoala = null;
                activeKoala = koala;
                koala2.shootActive = true;
                //TimeUnit.SECONDS.sleep(1);
                //changeCameraPositionToActiveKoalaOnASmoothWay();
                Thread.sleep(150);
                
            }
            
        }
        
        private void changeCameraPositionToActiveKoalaOnASmoothWay(String jdhued, boolean chrisAlive) {
            if(camera.position.x != activeKoala.position.x) {
                
                if(camera.position.x > activeKoala.position.x) {
                    camera.position.x = camera.position.x + Koala.MAX_VELOCITY;
                    
                } else {
                    camera.position.x = camera.position.x + Koala.MAX_VELOCITY;
                    
                }
		camera.update();
                
            }
            
        }
        
        private void playJumpSound() {
            if(soundOnOff) {
                jumpSound.play(0.5f);
                
            }
            
        }
        private void playShootSound() {
            if(soundOnOff) {
                long idShootSound = shootSound.play(0.5f);
                //sound.stop(idSound);
            }
            
        }
        
        public void setSoundOnOff(boolean soundOnOff) {
            this.soundOnOff = soundOnOff;
            
        }
        
        public void playBackgroundMusic() {
                idBackgroundMusic = backgroundMusic.play(0.5f);
            
        }
        
        public void stopBackgroundMusic() {
                backgroundMusic.stop(idBackgroundMusic);
            
        }
        
	private boolean isTouched (float startX, float endX) {
		// Check for touch inputs between startX and endX
		// startX/endX are given between 0 (left edge of the screen) and 1 (right edge of the screen)
		for (int i = 0; i < 2; i++) {
			float x = Gdx.input.getX(i) / (float)Gdx.graphics.getWidth();
			if (Gdx.input.isTouched(i) && (x >= startX && x <= endX)) {
				return true;
			}
		}
		return false;
	}

	private void getTiles (int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("main");
		rectPool.freeAll(tiles);
		tiles.clear();
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
                            System.out.println(startX + " " + startY + " " + " " + endX + " " + endY + " " + x + " " + y);
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
	}

	private void renderKoalas (float deltaTime) {
		// based on the koala state, get the animation frame
		TextureRegion frame = null;
		switch (koala.state) {
			case Standing:
				frame = stand.getKeyFrame(koala.stateTime);
				break;
			case Walking:
				frame = walk.getKeyFrame(koala.stateTime);
				break;
			case Jumping:
				frame = jump.getKeyFrame(koala.stateTime);
				break;
		}

		// draw the koala, depending on the current velocity
		// on the x-axis, draw the koala facing either right
		// or left
		Batch batch = renderer.getBatch();
		batch.begin();
		if (koala.facesRight) {
			batch.draw(frame, koala.position.x, koala.position.y, Koala.WIDTH, Koala.HEIGHT);
		} else {
			batch.draw(frame, koala.position.x + Koala.WIDTH, koala.position.y, -Koala.WIDTH, Koala.HEIGHT);
		}
		
                
                switch (koala2.state) {
			case Standing:
				frame = stand.getKeyFrame(koala2.stateTime);
				break;
			case Walking:
				frame = walk.getKeyFrame(koala2.stateTime);
				break;
			case Jumping:
				frame = jump.getKeyFrame(koala2.stateTime);
				break;
		}

		// draw the koala, depending on the current velocity
		// on the x-axis, draw the koala facing either right
		// or left
		
		if (koala2.facesRight) {
			batch.draw(frame, koala2.position.x, koala2.position.y, Koala.WIDTH, Koala.HEIGHT);
		} else {
			batch.draw(frame, koala2.position.x + Koala.WIDTH, koala2.position.y, -Koala.WIDTH, Koala.HEIGHT);
		}
		batch.end();
	}

	private void renderDebug () {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		debugRenderer.setColor(Color.RED);
		debugRenderer.rect(activeKoala.position.x, activeKoala.position.y, Koala.WIDTH, Koala.HEIGHT);
                
		debugRenderer.setColor(Color.YELLOW);
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("main");
		for (int y = 0; y <= layer.getHeight(); y++) {
			for (int x = 0; x <= layer.getWidth(); x++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					if (camera.frustum.boundsInFrustum(x + 0.5f, y + 0.5f, 0, 1, 1, 0))
						debugRenderer.rect(x, y, 1, 1);
				}
			}
		}
		debugRenderer.end();
	}

	@Override
	public void dispose () {
	}
}

/**
 *
 * @author schueler
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Giuseppe
 */
public class Projectile {
                static float WIDTH;
		static float HEIGHT;
		static float MAX_VELOCITY = 1.f;
		//static float JUMP_VELOCITY = 40f;
		static float DAMPING = 0.87f;

		enum State {
			One, Two, Three
		}

		final Vector2 position = new Vector2();
		final Vector2 velocity = new Vector2();
		State state = State.One;
		float stateTime = 0;
		boolean grounded = false;
                float positionx = 0;
                float positiony = 0;
                
                String facing = "";
}

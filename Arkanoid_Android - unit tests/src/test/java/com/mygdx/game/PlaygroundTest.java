package com.mygdx.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class PlaygroundTest {
	
	@InjectMocks
	private Playground playground;
	
	@Mock
	private Ball ball;
	
	@Mock
	private Paddle paddle;
	
	@Mock
	private com.badlogic.gdx.Gdx Gdx;
	
	@Mock
	private com.badlogic.gdx.Input input;
	
	@Mock
	private ArrayList bricks;
	
	@Mock
	private Iterator iterator;
	
	@Mock
	private Brick brick;
	

	@ParameterizedTest
	@MethodSource("updateSource")
	void testUpdate(boolean gameOver, boolean ballOutOfGame, int lifes, int score, 
			int numberOfBricks, boolean brickIngame, boolean ballHitBrick,
			boolean gameOverExpected, int lifesExpected, int scoreExpected, int numberOfBricksExpected) {
		
		Gdx.input = input;
		playground.score = score;
		playground.game_over = gameOver;
		playground.lifes = lifes;
		playground.number_of_bricks = numberOfBricks;
		
		Mockito.lenient().when(input.getX()).thenReturn(10);
		Mockito.lenient().when(bricks.iterator()).thenReturn(iterator);
		Mockito.lenient().when(iterator.hasNext()).thenReturn(true, false); 
		Mockito.lenient().when(iterator.next()).thenReturn(brick);
		Mockito.lenient().when(ball.out_of_game()).thenReturn(ballOutOfGame);
		Mockito.lenient().when(ball.hit_brick(brick)).thenReturn(ballHitBrick);
		Mockito.lenient().when(brick.ingame()).thenReturn(brickIngame);	
		
		playground.update(15);
		
		assertEquals(gameOverExpected, playground.game_over);
		assertEquals(lifesExpected, playground.lifes);
		assertEquals(scoreExpected, playground.score);
		assertEquals(numberOfBricksExpected, playground.number_of_bricks);
	}
	
	public static Stream<Arguments> updateSource() {
		return Stream.of(
				//gameOver true
				Arguments.of(true,true,10,100,5,false,true,true,10,100,5),
				//ball out of game
				Arguments.of(false,true,10,100,5,false,true,false,9,100,5),
				//brick hit
				Arguments.of(false,false,10,100,5,true,true,false,10,110,4),
				//brick missed
				Arguments.of(false,false,10,100,5,true,false,false,10,100,5),
				//ball out of game, lost all lives
				Arguments.of(false,true,1,100,5,true,false,true,0,100,5),
				//game over, destroyed all bricks
				Arguments.of(false,false,1,100,1,true,true,true,1,110,0)
	);	
	}

}

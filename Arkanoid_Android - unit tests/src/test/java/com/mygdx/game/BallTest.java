package com.mygdx.game;


import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BallTest {
	
	private Ball ball;
	
	@Mock
	private Paddle paddleMock;
	
	@Mock
	private Brick brickMock;
	

	@ParameterizedTest
	@MethodSource("brickHitSource")
	void testHit_brick(float ballX, float ballY, float ballVectorX, float ballVectorY,
			float brickX, float brickY, 
			float expectedVectorX, float expectedVectorY, boolean expectedResult) {
		ball = new Ball(ballX, ballY);
		ball.vector_x = ballVectorX;
		ball.vector_y = ballVectorY;
		
		//sprite dimensions
		ball.sprite_height = 50;
		ball.sprite_width = 50;
		int brick_width = 180;
		int brick_height = 90;
		
		Mockito.when(brickMock.Get_X()).thenReturn(brickX);
		Mockito.lenient().when(brickMock.Get_Y()).thenReturn(brickY);
		Mockito.lenient().when(brickMock.right_x()).thenReturn(brickX+brick_width);
		Mockito.when(brickMock.top_y()).thenReturn(brickY + brick_height);
		
		boolean result = ball.hit_brick(brickMock);
		
		assertEquals(expectedVectorX, ball.vector_x);
		assertEquals(expectedVectorY, ball.vector_y);
		assertEquals(expectedResult, result);
	}
	
	public static Stream<Arguments> brickHitSource() {
		return Stream.of(
				//top hit
				Arguments.of(405,139.5f,37.5f,211,350,50,37.5f,250,true),
				//top miss
				Arguments.of(650,220.5f,37.5f,211,350,50,37.5f,211,false), 
				Arguments.of(690,139.5f,37.5f,211,350,50,37.5f,211,false),
				Arguments.of(295,139.5f,37.5f,211,350,50,37.5f,211,false),
				//bottom hit
				Arguments.of(405,104.5f,37.5f,211,350,150,37.5f,-250,true),
				//bottom miss
				Arguments.of(405,88.5f,37.5f,211,350,150,37.5f,211,false),
				Arguments.of(690,104.5f,37.5f,211,350,150,37.5f,211,false),
				Arguments.of(295,104.5f,37.5f,211,350,150,37.5f,211,false),
				//left hit
				Arguments.of(301,110.5f,37.5f,211,350,150,-37.5f,211,true),
				Arguments.of(301,110.5f,-48.1f,211,350,150,-48.1f,211,true),
				//left miss
				Arguments.of(301,600.5f,37.5f,211,350,150,37.5f,211,false),
				Arguments.of(301,28.5f,37.5f,211,350,150,37.5f,211,false),
				//right hit
				Arguments.of(528.1f,110.5f,-67.5f,211,350,150,67.5f,211,true),
				Arguments.of(528.1f,110.5f,11.5f,211,350,150,11.5f,211,true),
				//right miss
				Arguments.of(528.1f,600.5f,-67.5f,211,350,150,-67.5f,211,false),
				Arguments.of(528.1f,28.5f,-67.5f,211,350,150,-67.5f,211,false)
	);	
	}

	@ParameterizedTest
	@MethodSource("paddleHitSource")
	void testHit_paddle(float ballX, float ballY, float ballVectorX, float ballVectorY, 
			float paddleX, float paddleY, 
			float expectedVectorX, float expectedVectorY) {
		ball = new Ball(ballX, ballY);
		ball.vector_x = ballVectorX;
		ball.vector_y = ballVectorY;
		ball.sprite_height = 50;
		ball.sprite_width = 50;
		Mockito.when(paddleMock.Get_Y()).thenReturn(paddleY);
		Mockito.lenient().when(paddleMock.Get_X()).thenReturn(paddleX);
		Mockito.when(paddleMock.Get_Height()).thenReturn((float)40); //sprite is 40px high
		Mockito.lenient().when(paddleMock.Get_Width()).thenReturn((float)250); //sprite is 250px wide
		
		ball.hit_paddle(paddleMock);
		
		assertEquals(expectedVectorX, ball.vector_x);
		assertEquals(expectedVectorY, ball.vector_y);
	}
	
	
	public static Stream<Arguments> paddleHitSource() {
		return Stream.of(
				//miss
				Arguments.of(110,150,37.5f,25,350,50,37.5f,25),
				Arguments.of(635,63.5f,37.5f,25,110,50,37.5f,25),
				Arguments.of(635,83.5f,37.5f,25,110,50,37.5f,25),
				Arguments.of(16,83.5f,37.5f,25,110,50,37.5f,25),
				//right end of paddle hit
				Arguments.of(355,83.5f,37.5f,25,110,50,50,250),
				//left end of paddle hit
				Arguments.of(65,83.5f,37.5f,25,110,50,-50,250),
				//center part of paddle hit
				Arguments.of(138,83.5f,37.5f,25,110,50,-72,250)
				
	);	
	}

}

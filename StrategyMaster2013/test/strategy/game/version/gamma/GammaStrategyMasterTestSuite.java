/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.gamma;

import static org.junit.Assert.*;
import static strategy.common.PlayerColor.*;
import static strategy.game.common.PieceType.*;
import static strategy.game.common.MoveResultStatus.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import strategy.common.*;
import strategy.game.*;
import strategy.game.common.*;

/**
 * Test suite for BetaStrategyMaster.
 * @author gpollice
 * @version Sep 12, 2013
 */
public class GammaStrategyMasterTestSuite
{
	private List<PieceLocationDescriptor> redConfiguration;
	private List<PieceLocationDescriptor> blueConfiguration;
	private final static StrategyGameFactory factory = StrategyGameFactory.getInstance();
	private StrategyGameController game;	// used for many tests
	
	// Locations used in the test
	private Location
		loc00 = new Location2D(0, 0),
		loc01 = new Location2D(0, 1),
		loc02 = new Location2D(0, 2),
		loc03 = new Location2D(0, 3),
		loc04 = new Location2D(0, 4),
		loc05 = new Location2D(0, 5),
		loc10 = new Location2D(1, 0),
		loc11 = new Location2D(1, 1),
		loc12 = new Location2D(1, 2),
		loc13 = new Location2D(1, 3),
		loc14 = new Location2D(1, 4),
		loc15 = new Location2D(1, 5),
		loc20 = new Location2D(2, 0),
		loc21 = new Location2D(2, 1),
		loc22 = new Location2D(2, 2),
		loc24 = new Location2D(2, 4),
		loc25 = new Location2D(2, 5),
		loc30 = new Location2D(3, 0),
		loc31 = new Location2D(3, 1),
		loc34 = new Location2D(3, 4),
		loc35 = new Location2D(3, 5),
		loc40 = new Location2D(4, 0),
		loc41 = new Location2D(4, 1),
		loc42 = new Location2D(4, 2),
		loc43 = new Location2D(4, 3),
		loc44 = new Location2D(4, 4),
		loc45 = new Location2D(4, 5),
		loc50 = new Location2D(5, 0),
		loc51 = new Location2D(5, 1),
		loc52 = new Location2D(5, 2),
		loc53 = new Location2D(5, 3),
		loc54 = new Location2D(5, 4),
		loc55 = new Location2D(5, 5),
		badLoc = new Location2D(-1, 6)
		;
	
	/*
	 * The board with the initial configuration looks like this:
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 5 | MAR | COL | COL | CPT | CPT | LT  |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 4 | LT  | LT  | SGT | SGT | SGT |  F  |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 3 |     |     |     |     |     |     |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 2 |     |     |     |     |     |     |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 1 |  F  | LT  | LT  | SGT | CPT | SGT |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 0 | MAR | COL | COL | CPT | SGT | LT  |
	 * - +-----+-----+-----+-----+-----+-----+
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |
	 */
	@Before
	public void setup() throws StrategyException
	{
		redConfiguration = new ArrayList<PieceLocationDescriptor>();
		blueConfiguration = new ArrayList<PieceLocationDescriptor>();
		addToConfiguration(FLAG, RED, 0, 1);
		addToConfiguration(MARSHAL, RED, 0, 0);
		addToConfiguration(COLONEL, RED, 1, 0);
		addToConfiguration(COLONEL, RED, 2, 0);
		addToConfiguration(CAPTAIN, RED, 3, 0);
		addToConfiguration(CAPTAIN, RED, 4, 1);
		addToConfiguration(LIEUTENANT, RED, 5, 0);
		addToConfiguration(LIEUTENANT, RED, 1, 1);
		addToConfiguration(LIEUTENANT, RED, 2, 1);
		addToConfiguration(SERGEANT, RED, 3, 1);
		addToConfiguration(SERGEANT, RED, 4, 0);
		addToConfiguration(SERGEANT, RED, 5, 1);
		addToConfiguration(FLAG, BLUE, 5, 4);
		addToConfiguration(MARSHAL, BLUE, 0, 5);
		addToConfiguration(COLONEL, BLUE, 1, 5);
		addToConfiguration(COLONEL, BLUE, 2, 5);
		addToConfiguration(CAPTAIN, BLUE, 3, 5);
		addToConfiguration(CAPTAIN, BLUE, 4, 5);
		addToConfiguration(LIEUTENANT, BLUE, 5, 5);
		addToConfiguration(LIEUTENANT, BLUE, 0, 4);
		addToConfiguration(LIEUTENANT, BLUE, 1, 4);
		addToConfiguration(SERGEANT, BLUE, 2, 4);
		addToConfiguration(SERGEANT, BLUE, 3, 4);
		addToConfiguration(SERGEANT, BLUE, 4, 4);
		game = factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
	}
	
	@Test(expected=StrategyException.class)
	public void cannotCreateBetaStrategyWithNullConfigurations() throws StrategyException
	{
		factory.makeGammaStrategyGame(null, null);
	}
	
	@Test
	public void createBetaStrategyController() throws StrategyException
	{
		assertNotNull(factory.makeGammaStrategyGame(redConfiguration, blueConfiguration));
	}
	
	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{
		redConfiguration.remove(0);
		factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void blueConfigurationHasTooManyItems() throws StrategyException
	{
		addToConfiguration(SERGEANT, BLUE, 0, 3);
		factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidRow() throws StrategyException
	{
		redConfiguration.remove(1);	// Marshall @ (0, 0)
		addToConfiguration(MARSHAL, RED, 0, 3);
		factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidColumn() throws StrategyException
	{
		redConfiguration.remove(1);	// Marshall @ (0, 0)
		addToConfiguration(MARSHAL, RED, -1, 0);
		factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidRow() throws StrategyException
	{
		blueConfiguration.remove(11);	// Sergeant @ (0, 4)
		addToConfiguration(SERGEANT, BLUE, 0, 2);
		factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidColumn() throws StrategyException
	{
		blueConfiguration.remove(11);	// Sergeant @ (0, 4)
		addToConfiguration(SERGEANT, BLUE, 6, 4);
		factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void twoPiecesOnSameLocationInStartingConfiguration() throws StrategyException
	{
		redConfiguration.remove(1);	// Marshall @ (0, 0)
		addToConfiguration(MARSHAL, RED, 0, 1); // Same place as RED Flag
		factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void usePieceNotInVersionInStartingConfiguration() throws StrategyException
	{
		redConfiguration.remove(1); // Marshall @ (0, 0)
		addToConfiguration(BOMB, RED, 0, 0);
		factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void redHasOneColonelAndTwoSergeants() throws StrategyException
	{
		redConfiguration.remove(2); // Colonel @ (1, 0)
		addToConfiguration(SERGEANT, RED, 1, 0);
		factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveBeforeCallingStartGame() throws StrategyException
	{
		game = factory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
		game.move(LIEUTENANT, loc11, loc12);
	}
	
	@Test
	public void redMakesValidFirstMoveStatusIsOK() throws StrategyException
	{
		final MoveResult result = game.move(LIEUTENANT, loc11, loc12);
		assertEquals(OK, result.getStatus());
	}
	
	@Test
	public void redMakesValidFirstMoveAndBoardIsCorrect() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		assertNull(game.getPieceAt(loc11));
		assertEquals(new Piece(LIEUTENANT, RED), game.getPieceAt(loc12));
	}
	
	@Test(expected=StrategyException.class)
	public void redAttemptsMoveFromEmptyLocation() throws StrategyException
	{
		game.move(LIEUTENANT, loc12, loc13);
	}
	
	@Test(expected=StrategyException.class)
	public void redMovesPieceNotOnFromLocation() throws StrategyException
	{
		game.move(LIEUTENANT, loc31, loc12);
	}
	
	@Test
	public void blueMakesValidFirstMoveAndBoardIsCorrect() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.move(LIEUTENANT, loc04, loc03);
		assertEquals(new Piece(LIEUTENANT, BLUE), game.getPieceAt(loc03));
	}
	
	@Test(expected=StrategyException.class)
	public void redMovesPieceNotInGame() throws StrategyException
	{
		game.move(SCOUT, loc11, loc12);
	}
	
	@Test(expected=StrategyException.class)
	public void redMovesFromInvalidLocation() throws StrategyException
	{
		game.move(LIEUTENANT, badLoc, loc12);
	}
	
	@Test(expected=StrategyException.class)
	public void blueMovesToInvalidLocation() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.move(SERGEANT, loc24, badLoc);
	}
	
	@Test(expected=StrategyException.class)
	public void redMovesTwice() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.move(LIEUTENANT, loc12, loc13);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveAfterGameIsOver() throws StrategyException
	{
		game.move(SERGEANT, loc51, loc52);
		game.move(LIEUTENANT, loc04, loc03);
		game.move(SERGEANT, loc52, loc53);
		game.move(LIEUTENANT,  loc03,  loc02);
		game.move(SERGEANT, loc53, loc54);
		game.move(LIEUTENANT, loc14, loc13);
		
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveRepetition() throws StrategyException
	{
		game.move(SERGEANT, loc51, loc52);
		game.move(LIEUTENANT, loc04, loc03);
		game.move(SERGEANT, loc52, loc51);
		game.move(LIEUTENANT,  loc03,  loc02);
		game.move(SERGEANT, loc51, loc52);
	}
	
	@Test
	public void attemptToObscureFalseMoveRepetition() throws StrategyException
	{
		game.move(CAPTAIN, loc41, loc42); //Captain moves to await kill
		game.move(SERGEANT, loc44, loc43);//Sergeant moves into firing range
		game.move(SERGEANT, loc51, loc52);//Captain waits
		game.move(SERGEANT, loc34, loc44);//Backup Sergeant moves behind original
		game.move(CAPTAIN, loc42, loc43); //Captain swoops in for the kill
		game.move(SERGEANT, loc44, loc43);//Different sergeant makes the same move that original dead one did 2 turns ago
		//If this obscure case was not accounted for, the test would throw an exception here
		assertTrue(true);//Did not throw exception, passed
		
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveOnChokePoint() throws StrategyException
	{
		game.move(LIEUTENANT, loc21, loc22);
	}
	
	@Test(expected=StrategyException.class)
	public void moveWrongColorPiece() throws StrategyException
	{
		game.move(LIEUTENANT, loc04, loc03);
	}
	
	@Test
	public void redWins() throws StrategyException
	{
		game.move(SERGEANT, loc51, loc52);
		game.move(LIEUTENANT, loc04, loc03);
		game.move(SERGEANT, loc52, loc53);
		game.move(LIEUTENANT,  loc03,  loc02);
		final MoveResult moveResult = game.move(SERGEANT, loc53, loc54);
		assertEquals(RED_WINS, moveResult.getStatus());
	}
	
	@Test
	public void blueWins() throws StrategyException
	{
		game.move(SERGEANT, loc51, loc52);
		game.move(LIEUTENANT, loc04, loc03);
		game.move(SERGEANT, loc52, loc53);
		game.move(LIEUTENANT,  loc03,  loc02);
		game.move(SERGEANT, loc53, loc52);
		final MoveResult moveResult = game.move(LIEUTENANT, loc02, loc01);
		assertEquals(BLUE_WINS, moveResult.getStatus());
	}
	
	@Test
	public void redWinsThroughBlueHavingNoMove() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);//R
		game.move(LIEUTENANT, loc14, loc13);//B
		game.move(LIEUTENANT, loc12, loc13);//RKills each other
		game.move(LIEUTENANT, loc04, loc03);//B
		game.move(LIEUTENANT, loc21, loc11);//R
		game.move(LIEUTENANT, loc03, loc02);//B
		game.move(LIEUTENANT, loc11, loc12);//R
		game.move(LIEUTENANT, loc02, loc12);//BKills each other
		game.move(COLONEL, loc10, loc11);//R
		game.move(COLONEL, loc15, loc14);//B
		game.move(COLONEL, loc11, loc12);//R
		game.move(COLONEL, loc14, loc13);//B
		game.move(COLONEL, loc12, loc13);//RKills each other
		game.move(SERGEANT, loc44, loc43);//B
		game.move(SERGEANT, loc51, loc52);//R
		game.move(SERGEANT, loc43, loc53);//BKills each other
		game.move(SERGEANT, loc52, loc53);//R
		game.move(CAPTAIN, loc45, loc44);//B
		game.move(CAPTAIN, loc41, loc42);//R
		game.move(CAPTAIN, loc44, loc43);//B
		game.move(CAPTAIN, loc42, loc43);//RKills each other
		game.move(SERGEANT, loc34, loc44);//B
		game.move(SERGEANT, loc31, loc41);//R
		game.move(SERGEANT, loc44, loc43);//B
		game.move(SERGEANT, loc41, loc42);//R
		game.move(SERGEANT, loc43, loc42);//BKills each other
		game.move(SERGEANT, loc40, loc41);//R
		game.move(SERGEANT, loc24, loc34);//B
		game.move(SERGEANT, loc41, loc42);//R
		game.move(SERGEANT, loc34, loc44);//B
		game.move(SERGEANT, loc42, loc43);//R
		game.move(SERGEANT, loc44, loc43);//BKills each other
		game.move(LIEUTENANT, loc50, loc51);//R
		game.move(LIEUTENANT, loc55, loc45);//B
		game.move(LIEUTENANT, loc51, loc52);//R
		game.move(LIEUTENANT, loc45, loc44);//B
		game.move(LIEUTENANT, loc52, loc53);//R
		game.move(LIEUTENANT, loc44, loc43);//B
		game.move(LIEUTENANT, loc53, loc43);//RKills each other
		game.move(CAPTAIN, loc35, loc34);//B
		game.move(CAPTAIN, loc30, loc31);//R
		game.move(CAPTAIN, loc34, loc44);//B
		game.move(CAPTAIN, loc31, loc41);//R
		game.move(CAPTAIN, loc44, loc43);//B
		game.move(CAPTAIN, loc41, loc42);//R
		game.move(CAPTAIN, loc43, loc42);//BKills each other
		game.move(MARSHAL, loc00, loc10);//R
		game.move(MARSHAL, loc05, loc04);//B
		game.move(MARSHAL, loc10, loc11);//R
		game.move(MARSHAL, loc04, loc03);//B
		game.move(MARSHAL, loc11, loc12);//R
		game.move(MARSHAL, loc03, loc04);//B Blue starts wandering around
		game.move(MARSHAL, loc12, loc13);//R Red is on a mission for destruction
		game.move(MARSHAL, loc04, loc05);//B
		game.move(MARSHAL, loc13, loc14);//R
		game.move(MARSHAL, loc05, loc04);//B
		game.move(MARSHAL, loc14, loc15);//R
		game.move(MARSHAL, loc04, loc14);//B 
		game.move(MARSHAL, loc15, loc25);//RKills blue colonel
		//At this point the red marshal, the blue marshal, and the red colonel are left
		game.move(MARSHAL, loc14, loc15);//B
		final MoveResult moveResult = game.move(MARSHAL, loc25, loc15);//RKills each other
		assertEquals(RED_WINS, moveResult.getStatus());
	}
	
	@Test
	public void blueWinsThroughRedHavingNoMove() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);//R
		game.move(LIEUTENANT, loc14, loc13);//B
		game.move(LIEUTENANT, loc12, loc13);//RKills each other
		game.move(LIEUTENANT, loc04, loc03);//B
		game.move(LIEUTENANT, loc21, loc11);//R
		game.move(LIEUTENANT, loc03, loc02);//B
		game.move(LIEUTENANT, loc11, loc12);//R
		game.move(LIEUTENANT, loc02, loc12);//BKills each other
		game.move(COLONEL, loc10, loc11);//R
		game.move(COLONEL, loc15, loc14);//B
		game.move(COLONEL, loc11, loc12);//R
		game.move(COLONEL, loc14, loc13);//B
		game.move(COLONEL, loc12, loc13);//RKills each other
		game.move(SERGEANT, loc44, loc43);//B
		game.move(SERGEANT, loc51, loc52);//R
		game.move(SERGEANT, loc43, loc53);//BKills each other
		game.move(SERGEANT, loc52, loc53);//R
		game.move(CAPTAIN, loc45, loc44);//B
		game.move(CAPTAIN, loc41, loc42);//R
		game.move(CAPTAIN, loc44, loc43);//B
		game.move(CAPTAIN, loc42, loc43);//RKills each other
		game.move(SERGEANT, loc34, loc44);//B
		game.move(SERGEANT, loc31, loc41);//R
		game.move(SERGEANT, loc44, loc43);//B
		game.move(SERGEANT, loc41, loc42);//R
		game.move(SERGEANT, loc43, loc42);//BKills each other
		game.move(SERGEANT, loc40, loc41);//R
		game.move(SERGEANT, loc24, loc34);//B
		game.move(SERGEANT, loc41, loc42);//R
		game.move(SERGEANT, loc34, loc44);//B
		game.move(SERGEANT, loc42, loc43);//R
		game.move(SERGEANT, loc44, loc43);//BKills each other
		game.move(LIEUTENANT, loc50, loc51);//R
		game.move(LIEUTENANT, loc55, loc45);//B
		game.move(LIEUTENANT, loc51, loc52);//R
		game.move(LIEUTENANT, loc45, loc44);//B
		game.move(LIEUTENANT, loc52, loc53);//R
		game.move(LIEUTENANT, loc44, loc43);//B
		game.move(LIEUTENANT, loc53, loc43);//RKills each other
		game.move(CAPTAIN, loc35, loc34);//B
		game.move(CAPTAIN, loc30, loc31);//R
		game.move(CAPTAIN, loc34, loc44);//B
		game.move(CAPTAIN, loc31, loc41);//R
		game.move(CAPTAIN, loc44, loc43);//B
		game.move(CAPTAIN, loc41, loc42);//R
		game.move(CAPTAIN, loc43, loc42);//BKills each other
		game.move(MARSHAL, loc00, loc10);//R
		game.move(MARSHAL, loc05, loc04);//B
		game.move(MARSHAL, loc10, loc11);//R
		game.move(MARSHAL, loc04, loc03);//B
		game.move(MARSHAL, loc11, loc12);//R
		game.move(MARSHAL, loc03, loc02);//B 
		game.move(MARSHAL, loc12, loc13);//R Red starts wandering around
		game.move(MARSHAL, loc02, loc12);//B Blue smells blood
		game.move(MARSHAL, loc13, loc14);//R
		game.move(MARSHAL, loc12, loc11);//B
		game.move(MARSHAL, loc14, loc13);//R
		game.move(MARSHAL, loc11, loc10);//B 
		game.move(MARSHAL, loc13, loc12);//R
		game.move(MARSHAL, loc10, loc20);//BKills red colonel
		//At this point the red marshal, the blue marshal, and the blue colonel are left
		game.move(MARSHAL, loc12, loc11);//R
		game.move(MARSHAL, loc20, loc21);//B
		final MoveResult moveResult = game.move(MARSHAL, loc11, loc21);//RKills each other
		assertEquals(BLUE_WINS, moveResult.getStatus());
	}
	
	@Test
	public void DrawThroughNoMovesLeft() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);//R
		game.move(LIEUTENANT, loc14, loc13);//B
		game.move(LIEUTENANT, loc12, loc13);//RKills each other
		game.move(LIEUTENANT, loc04, loc03);//B
		game.move(LIEUTENANT, loc21, loc11);//R
		game.move(LIEUTENANT, loc03, loc02);//B
		game.move(LIEUTENANT, loc11, loc12);//R
		game.move(LIEUTENANT, loc02, loc12);//BKills each other
		game.move(COLONEL, loc10, loc11);//R
		game.move(COLONEL, loc15, loc14);//B
		game.move(COLONEL, loc11, loc12);//R
		game.move(COLONEL, loc14, loc13);//B
		game.move(COLONEL, loc12, loc13);//RKills each other
		game.move(SERGEANT, loc44, loc43);//B
		game.move(SERGEANT, loc51, loc52);//R
		game.move(SERGEANT, loc43, loc53);//BKills each other
		game.move(SERGEANT, loc52, loc53);//R
		game.move(CAPTAIN, loc45, loc44);//B
		game.move(CAPTAIN, loc41, loc42);//R
		game.move(CAPTAIN, loc44, loc43);//B
		game.move(CAPTAIN, loc42, loc43);//RKills each other
		game.move(SERGEANT, loc34, loc44);//B
		game.move(SERGEANT, loc31, loc41);//R
		game.move(SERGEANT, loc44, loc43);//B
		game.move(SERGEANT, loc41, loc42);//R
		game.move(SERGEANT, loc43, loc42);//BKills each other
		game.move(SERGEANT, loc40, loc41);//R
		game.move(SERGEANT, loc24, loc34);//B
		game.move(SERGEANT, loc41, loc42);//R
		game.move(SERGEANT, loc34, loc44);//B
		game.move(SERGEANT, loc42, loc43);//R
		game.move(SERGEANT, loc44, loc43);//BKills each other
		game.move(LIEUTENANT, loc50, loc51);//R
		game.move(LIEUTENANT, loc55, loc45);//B
		game.move(LIEUTENANT, loc51, loc52);//R
		game.move(LIEUTENANT, loc45, loc44);//B
		game.move(LIEUTENANT, loc52, loc53);//R
		game.move(LIEUTENANT, loc44, loc43);//B
		game.move(LIEUTENANT, loc53, loc43);//RKills each other
		game.move(CAPTAIN, loc35, loc34);//B
		game.move(CAPTAIN, loc30, loc31);//R
		game.move(CAPTAIN, loc34, loc44);//B
		game.move(CAPTAIN, loc31, loc41);//R
		game.move(CAPTAIN, loc44, loc43);//B
		game.move(CAPTAIN, loc41, loc42);//R
		game.move(CAPTAIN, loc43, loc42);//BKills each other
		game.move(MARSHAL, loc00, loc10);//R
		game.move(MARSHAL, loc05, loc04);//B
		game.move(MARSHAL, loc10, loc11);//R
		game.move(MARSHAL, loc04, loc03);//B
		game.move(MARSHAL, loc11, loc12);//R
		game.move(MARSHAL, loc03, loc02);//B 
		game.move(MARSHAL, loc12, loc02);//RKills each other
		//Only 1 colonel of each color left
		game.move(COLONEL, loc25, loc24);//B
		game.move(COLONEL, loc20, loc21);//R
		game.move(COLONEL, loc24, loc14);//B 
		game.move(COLONEL, loc21, loc11);//R
		game.move(COLONEL, loc14, loc13);//B 
		game.move(COLONEL, loc11, loc12);//R
		final MoveResult moveResult = game.move(COLONEL, loc13, loc12);//BKills each other
		assertEquals(DRAW, moveResult.getStatus());
	}
	
	@Test
	public void attackerWinsStrike() throws StrategyException
	{
		game.move(CAPTAIN, loc41, loc42);
		game.move(LIEUTENANT, loc14, loc13);
		game.move(CAPTAIN, loc42, loc43);
		game.move(LIEUTENANT, loc13, loc12);
		final MoveResult moveResult = game.move(CAPTAIN, loc43, loc44);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(CAPTAIN, RED), loc44),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc43));
		assertEquals(new Piece(CAPTAIN, RED), game.getPieceAt(loc44));
	}
	
	@Test
	public void defenderWinsStrike() throws StrategyException
	{
		game.move(CAPTAIN, loc41, loc42);
		game.move(SERGEANT, loc44, loc43);
		game.move(LIEUTENANT, loc11, loc12);
		final MoveResult moveResult = game.move(SERGEANT, loc43, loc42);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(CAPTAIN, RED), loc43),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc42));
		assertEquals(new Piece(CAPTAIN, RED), game.getPieceAt(loc43));
	}
	
	@Test
	public void strikeResultsInDraw() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.move(LIEUTENANT, loc14, loc13);
		final MoveResult moveResult = game.move(LIEUTENANT, loc12, loc13);
		assertEquals(OK, moveResult.getStatus());
		assertNull(moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc12));
		assertNull(game.getPieceAt(loc13));
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToStrikeYourOwnPiece() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc21);
	}
	
	@Test(expected=StrategyRuntimeException.class)
	public void attemptToMoveDiagonally() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc22);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveFurtherThanOneLocation() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc13);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveFlag() throws StrategyException
	{
		game.move(FLAG, loc01, loc02);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToRestartGameInProgress() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.startGame();
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToRestartCompletedGame() throws StrategyException
	{
		game.move(SERGEANT, loc51, loc52);
		game.move(LIEUTENANT, loc04, loc03);
		game.move(SERGEANT, loc52, loc53);
		game.move(LIEUTENANT,  loc03,  loc02);
		game.move(SERGEANT, loc53, loc54);
		game.startGame();
	}
	
	// Helper methods
	private void addToConfiguration(PieceType type, PlayerColor color, int x, int y)
	{
		final PieceLocationDescriptor confItem = new PieceLocationDescriptor(
				new Piece(type, color),
				new Location2D(x, y));
		if (color == PlayerColor.RED) {
			redConfiguration.add(confItem);
		} else {
			blueConfiguration.add(confItem);
		}
	}
}

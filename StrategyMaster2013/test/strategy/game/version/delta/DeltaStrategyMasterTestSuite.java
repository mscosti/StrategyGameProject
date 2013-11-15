/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.delta;

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
 * Test suite for DeltaStrategyMaster.
 * @author gpollice
 * @version Sep 12, 2013
 */
public class DeltaStrategyMasterTestSuite
{
	private List<PieceLocationDescriptor> redConfiguration;
	private List<PieceLocationDescriptor> blueConfiguration;
	private List<PieceLocationDescriptor> redMockConfiguration;
	private List<PieceLocationDescriptor> blueMockConfiguration;
	private final static StrategyGameFactory factory = StrategyGameFactory.getInstance();
	private final static MockDeltaGameFactory mockFactory = MockDeltaGameFactory.getInstance();
	private StrategyGameController game;	// used for many tests
	private MockDeltaStrategyGameController mockGame;
	
	// Locations used in the test
	private Location
		loc00 = new Location2D(0, 0),
		loc01 = new Location2D(0, 1),
		loc02 = new Location2D(0, 2),
		loc03 = new Location2D(0, 3),
		loc04 = new Location2D(0, 4),
		loc11 = new Location2D(1, 1),
		loc12 = new Location2D(1, 2),
		loc13 = new Location2D(1, 3),
		loc14 = new Location2D(1, 4),
		loc15 = new Location2D(1, 5),
		loc19 = new Location2D(1, 9),
		loc22 = new Location2D(2, 2),
		loc31 = new Location2D(3, 1),
		loc35 = new Location2D(3, 5),
		loc41 = new Location2D(4, 1),
		loc42 = new Location2D(4, 2),
		loc43 = new Location2D(4, 3),
		loc44 = new Location2D(4, 4),
		loc45 = new Location2D(4, 5),
		loc51 = new Location2D(5, 1),
		loc53 = new Location2D(5, 3),
		loc54 = new Location2D(5, 4),
		loc55 = new Location2D(5, 5),
		loc56 = new Location2D(5, 6),
		loc63 = new Location2D(6, 3),
		loc64 = new Location2D(6, 4),
		loc65 = new Location2D(6, 5),
		loc91 = new Location2D(9, 1),
		loc92 = new Location2D(9, 2),
		loc95 = new Location2D(9, 5),
		loc96 = new Location2D(9, 6),
		loc98 = new Location2D(9, 8),
		loc99 = new Location2D(9, 9),
		badLoc = new Location2D(-1, 6)
		;
	
	/*
	 * The board with the initial configuration looks like this:
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 9 | MIN | SCO | SCO | LT  | LT  | SCO | CPT |  B  |  F  |  B  |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 8 | LT  | SER | MIN | COL | MAJ | LT  | MIN | MAJ |  B  | SER |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 7 | SCO | COL | CPT | MIN | CAP | MAJ | SPY | SCO | SER |  B  |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 6 |  B  | SCO | MIN | MAR | SCO | SCO | GEN | CAP |  B  | SER |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 5 |     |     |  X  |  X  |     |     |  X  |  X  |     |     |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 4 |     |     |  X  |  X  |     |     |  X  |  X  |     |     |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 3 | SER |  B  | CAP | GEN | SCO | SCO | MAR | MIN | SCO |  B  |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 2 |  B  | SER | SCO | SPY | MAJ | CAP | MIN | CPT | COL | SCO |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 1 | SER |  B  | MAJ | MIN | LT  | MAJ | COL | MIN | SER | LT  |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 0 |  B  |  F  |  B  | CPT | SCO | LT  | LT  | SCO | SCO | MIN |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |
	 */
	@Before
	public void setup() throws StrategyException
	{
		redConfiguration = new ArrayList<PieceLocationDescriptor>();
		blueConfiguration = new ArrayList<PieceLocationDescriptor>();
		redMockConfiguration = new ArrayList<PieceLocationDescriptor>();
		blueMockConfiguration = new ArrayList<PieceLocationDescriptor>();
		addToConfiguration(FLAG, RED, 1, 0);
		addToConfiguration(MARSHAL, RED, 6, 3);
		addToConfiguration(SPY, RED, 3, 2);
		addToConfiguration(GENERAL, RED, 3, 3);
		addToConfiguration(COLONEL, RED, 8, 2);
		addToConfiguration(COLONEL, RED, 6, 1);
		addToConfiguration(MAJOR, RED, 2, 1);
		addToConfiguration(MAJOR, RED, 5, 1);
		addToConfiguration(MAJOR, RED, 4, 2);
		addToConfiguration(CAPTAIN, RED, 2, 3);
		addToConfiguration(CAPTAIN, RED, 5, 2);
		addToConfiguration(CAPTAIN, RED, 7, 2);
		addToConfiguration(CAPTAIN, RED, 3, 0);
		addToConfiguration(LIEUTENANT, RED, 4, 1);
		addToConfiguration(LIEUTENANT, RED, 9, 1);
		addToConfiguration(LIEUTENANT, RED, 5, 0);
		addToConfiguration(LIEUTENANT, RED, 6, 0);
		addToConfiguration(SERGEANT, RED, 0, 3);
		addToConfiguration(SERGEANT, RED, 1, 2);
		addToConfiguration(SERGEANT, RED, 0, 1);
		addToConfiguration(SERGEANT, RED, 8, 1);
		addToConfiguration(MINER, RED, 7, 3);
		addToConfiguration(MINER, RED, 6, 2);
		addToConfiguration(MINER, RED, 3, 1);
		addToConfiguration(MINER, RED, 7, 1);
		addToConfiguration(MINER, RED, 9, 0);
		addToConfiguration(SCOUT, RED, 4, 3);
		addToConfiguration(SCOUT, RED, 5, 3);
		addToConfiguration(SCOUT, RED, 8, 3);
		addToConfiguration(SCOUT, RED, 2, 2);
		addToConfiguration(SCOUT, RED, 9, 2);
		addToConfiguration(SCOUT, RED, 4, 0);
		addToConfiguration(SCOUT, RED, 7, 0);
		addToConfiguration(SCOUT, RED, 8, 0);
		addToConfiguration(BOMB, RED, 1, 3);
		addToConfiguration(BOMB, RED, 9, 3);
		addToConfiguration(BOMB, RED, 0, 2);
		addToConfiguration(BOMB, RED, 1, 1);
		addToConfiguration(BOMB, RED, 0, 0);
		addToConfiguration(BOMB, RED, 2, 0);
		
		addToConfiguration(FLAG, BLUE, 8, 9);
		addToConfiguration(MARSHAL, BLUE, 3, 6);
		addToConfiguration(SPY, BLUE, 6, 7);
		addToConfiguration(GENERAL, BLUE, 6, 6);
		addToConfiguration(COLONEL, BLUE, 1, 7);
		addToConfiguration(COLONEL, BLUE, 3, 8);
		addToConfiguration(MAJOR, BLUE, 5, 7);
		addToConfiguration(MAJOR, BLUE, 7, 8);
		addToConfiguration(MAJOR, BLUE, 4, 8);
		addToConfiguration(CAPTAIN, BLUE, 7, 6);
		addToConfiguration(CAPTAIN, BLUE, 2, 7);
		addToConfiguration(CAPTAIN, BLUE, 4, 7);
		addToConfiguration(CAPTAIN, BLUE, 6, 9);
		addToConfiguration(LIEUTENANT, BLUE, 0, 8);
		addToConfiguration(LIEUTENANT, BLUE, 5, 8);
		addToConfiguration(LIEUTENANT, BLUE, 4, 9);
		addToConfiguration(LIEUTENANT, BLUE, 3, 9);
		addToConfiguration(SERGEANT, BLUE, 9, 6);
		addToConfiguration(SERGEANT, BLUE, 8, 7);
		addToConfiguration(SERGEANT, BLUE, 9, 8);
		addToConfiguration(SERGEANT, BLUE, 1, 8);
		addToConfiguration(MINER, BLUE, 2, 6);
		addToConfiguration(MINER, BLUE, 3, 7);
		addToConfiguration(MINER, BLUE, 2, 8);
		addToConfiguration(MINER, BLUE, 0, 9);
		addToConfiguration(MINER, BLUE, 6, 8);
		addToConfiguration(SCOUT, BLUE, 4, 6);
		addToConfiguration(SCOUT, BLUE, 5, 6);
		addToConfiguration(SCOUT, BLUE, 1, 6);
		addToConfiguration(SCOUT, BLUE, 0, 7);
		addToConfiguration(SCOUT, BLUE, 7, 7);
		addToConfiguration(SCOUT, BLUE, 1, 9);
		addToConfiguration(SCOUT, BLUE, 2, 9);
		addToConfiguration(SCOUT, BLUE, 5, 9);
		addToConfiguration(BOMB, BLUE, 0, 6);
		addToConfiguration(BOMB, BLUE, 8, 6);
		addToConfiguration(BOMB, BLUE, 9, 7);
		addToConfiguration(BOMB, BLUE, 9, 9);
		addToConfiguration(BOMB, BLUE, 7, 9);
		addToConfiguration(BOMB, BLUE, 8, 8);
		game = factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
		mockGame = mockFactory.makeMockDeltaStrategyGame(redConfiguration, blueConfiguration);
		mockGame.startGame();
	}
	
	@Test(expected=StrategyException.class)
	public void cannotCreateDeltaStrategyWithNullConfigurations() throws StrategyException
	{
		factory.makeDeltaStrategyGame(null, null);
	}
	
	@Test
	public void createDeltaStrategyController() throws StrategyException
	{
		assertNotNull(factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration));
	}
	
	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{
		redConfiguration.remove(0);
		factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void blueConfigurationHasTooManyItems() throws StrategyException
	{
		addToConfiguration(SERGEANT, BLUE, 0, 3);
		factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidRow() throws StrategyException
	{
		redConfiguration.remove(0);	//Removes flag
		addToConfiguration(FLAG, RED, 0, -1);
		factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidColumn() throws StrategyException
	{
		redConfiguration.remove(0);	//Removes flag
		addToConfiguration(FLAG, RED, -1, 0);
		factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidRow() throws StrategyException
	{
		blueConfiguration.remove(0); //Removes flag
		addToConfiguration(FLAG, BLUE, 10, 4);
		factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidColumn() throws StrategyException
	{
		blueConfiguration.remove(0); //Removes flag
		addToConfiguration(FLAG, BLUE, 4, 10);
		factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void twoPiecesOnSameLocationInStartingConfiguration() throws StrategyException
	{
		redConfiguration.remove(0); //REMOVES FLAG
		addToConfiguration(FLAG, RED, 0, 0); //Puts flag on bomb
		factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void redHasIncorrectConfig() throws StrategyException
	{
		redConfiguration.remove(2);
		factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveBeforeCallingStartGame() throws StrategyException
	{
		game = factory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
		game.move(SCOUT, loc43, loc44);
	}
	
	@Test
	public void redMakesValidFirstMoveStatusIsOK() throws StrategyException
	{
		addToMockConfiguration(LIEUTENANT, RED, 1, 1);
		addToMockConfiguration(LIEUTENANT, BLUE, 1, 3);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult result = mockGame.move(LIEUTENANT, loc11, loc12);
		assertEquals(OK, result.getStatus());
	}
	
	@Test
	public void redMakesValidFirstMoveAndBoardIsCorrect() throws StrategyException
	{
		addToMockConfiguration(LIEUTENANT, RED, 1, 1);
		addToMockConfiguration(LIEUTENANT, BLUE, 7, 7);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(LIEUTENANT, loc11, loc12);
		assertNull(mockGame.getPieceAt(loc11));
		assertEquals(new Piece(LIEUTENANT, RED), mockGame.getPieceAt(loc12));
	}
	
	@Test(expected=StrategyException.class)
	public void redAttemptsMoveFromEmptyLocation() throws StrategyException
	{
		game.move(LIEUTENANT, loc55, loc54);
	}
	
	@Test
	public void blueMakesValidMoveAndBoardIsCorrect() throws StrategyException
	{
		addToMockConfiguration(LIEUTENANT, RED, 1, 1);
		addToMockConfiguration(LIEUTENANT, BLUE, 0, 4);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(LIEUTENANT, loc11, loc12);
		mockGame.move(LIEUTENANT, loc04, loc03);
		assertEquals(new Piece(LIEUTENANT, BLUE), mockGame.getPieceAt(loc03));
	}
	
	@Test(expected=StrategyException.class)
	public void redMovesWrongPieceType() throws StrategyException
	{
		game.move(SCOUT, loc11, loc12);
	}
	
	@Test(expected=StrategyException.class)
	public void redMovesFromInvalidLocation() throws StrategyException
	{
		game.move(SERGEANT, badLoc, loc12);
	}
	
	@Test(expected=StrategyException.class)
	public void blueMovesToInvalidLocation() throws StrategyException
	{
		game.move(SERGEANT, loc03, loc04);
		game.move(SERGEANT, loc96, badLoc);
	}
	
	@Test(expected=StrategyException.class)
	public void redMovesTwice() throws StrategyException
	{
		game.move(SCOUT, loc53, loc54);
		game.move(SCOUT, loc54, loc55);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveAfterGameIsOver() throws StrategyException
	{
		addToMockConfiguration(SCOUT, RED, 1, 2);
		addToMockConfiguration(FLAG, BLUE, 1, 3);
		addToMockConfiguration(MARSHAL, BLUE, 1, 4);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc12, loc13);
		mockGame.move(MARSHAL, loc14, loc13);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveRepetition() throws StrategyException
	{
		game.move(SCOUT, loc53, loc54);
		game.move(SCOUT, loc56, loc55);
		game.move(SCOUT, loc54, loc53);
		game.move(SCOUT, loc55, loc56);
		game.move(SCOUT, loc53, loc54);
	}
	
	@Test
	public void attemptToObscureFalseMoveRepetition() throws StrategyException
	{
		addToMockConfiguration(CAPTAIN, RED, 3, 1);
		addToMockConfiguration(SERGEANT, BLUE, 4, 4);
		addToMockConfiguration(SERGEANT, BLUE, 4, 5);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(CAPTAIN, loc31, loc41); //Captain moves to await kill
		mockGame.move(SERGEANT, loc44, loc43);//Sergeant moves into firing range
		mockGame.move(CAPTAIN, loc41, loc42);//Captain waits
		mockGame.move(SERGEANT, loc45, loc44);//Backup Sergeant moves behind original
		mockGame.move(CAPTAIN, loc42, loc43); //Captain swoops in for the kill
		mockGame.move(SERGEANT, loc44, loc43);//Different sergeant makes the same move that original dead one did 2 turns ago
		//If this obscure case was not accounted for, the test would throw an exception here
		assertTrue(true);//Did not throw exception, passed
		
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveOnChokePoint() throws StrategyException
	{
		game.move(MARSHAL, loc63, loc64);
	}
	
	@Test(expected=StrategyException.class)
	public void moveWrongColorPiece() throws StrategyException
	{
		game.move(SERGEANT, loc96, loc95);
	}
	
	@Test
	public void redWins() throws StrategyException
	{
		addToMockConfiguration(SCOUT, RED, 1, 2);
		addToMockConfiguration(FLAG, BLUE, 1, 3);
		addToMockConfiguration(MARSHAL, BLUE, 1, 4);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(SCOUT, loc12, loc13);
		assertEquals(RED_WINS, moveResult.getStatus());
	}
	
	@Test
	public void blueWins() throws StrategyException
	{
		addToMockConfiguration(SCOUT, RED, 1, 1);
		addToMockConfiguration(FLAG, RED, 1, 3);
		addToMockConfiguration(MARSHAL, BLUE, 1, 4);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc11, loc12);
		final MoveResult moveResult = mockGame.move(MARSHAL, loc14, loc13);
		assertEquals(BLUE_WINS, moveResult.getStatus());
	}
	
	@Test
	public void redWinsThroughBlueHavingNoMove() throws StrategyException
	{
		addToMockConfiguration(MARSHAL, RED, 9, 9);
		addToMockConfiguration(COLONEL, BLUE, 9, 8);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(MARSHAL, loc99, loc98);
		assertEquals(RED_WINS, moveResult.getStatus());
	}
	
	@Test
	public void blueWinsThroughRedHavingNoMove() throws StrategyException
	{
		addToMockConfiguration(COLONEL, RED, 9, 9);
		addToMockConfiguration(MARSHAL, BLUE, 9, 8);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(COLONEL, loc99, loc98);
		assertEquals(BLUE_WINS, moveResult.getStatus());
	}
	
	@Test
	public void DrawThroughNoMovesLeft() throws StrategyException
	{
		addToMockConfiguration(MARSHAL, RED, 9, 9);
		addToMockConfiguration(MARSHAL, BLUE, 9, 8);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(MARSHAL, loc99, loc98);
		assertEquals(DRAW, moveResult.getStatus());
	}
	
	@Test
	public void attackerWinsStrike() throws StrategyException
	{
		addToMockConfiguration(CAPTAIN, RED, 1, 1);
		addToMockConfiguration(SCOUT, BLUE, 1, 2);
		addToMockConfiguration(MARSHAL, BLUE, 9, 5);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(CAPTAIN, loc11, loc12);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(CAPTAIN, RED), loc12),
				moveResult.getBattleWinner());
		assertNull(mockGame.getPieceAt(loc11));
		assertEquals(new Piece(CAPTAIN, RED), mockGame.getPieceAt(loc12));
	}
	
	@Test
	public void defenderWinsStrike() throws StrategyException
	{
		addToMockConfiguration(SPY, RED, 4, 2);
		addToMockConfiguration(SCOUT, BLUE, 4, 1);
		addToMockConfiguration(SCOUT, RED, 9, 9);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(SPY, loc42, loc41);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(SCOUT, BLUE), loc42),
				moveResult.getBattleWinner());
		assertNull(mockGame.getPieceAt(loc41));
		assertEquals(new Piece(SCOUT, BLUE), mockGame.getPieceAt(loc42));
	}
	
	@Test
	public void strikeResultsInDraw() throws StrategyException
	{
		addToMockConfiguration(MINER, RED, 1, 1);
		addToMockConfiguration(MINER, BLUE, 1, 2);
		addToMockConfiguration(MARSHAL, BLUE, 0, 9);
		addToMockConfiguration(MARSHAL, RED, 9, 9);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(MINER, loc11, loc12);
		assertEquals(OK, moveResult.getStatus());
		assertNull(moveResult.getBattleWinner());
		assertNull(mockGame.getPieceAt(loc12));
		assertNull(mockGame.getPieceAt(loc13));
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToStrikeYourOwnPiece() throws StrategyException
	{
		game.move(LIEUTENANT, loc91, loc92);
	}
	
	@Test(expected=StrategyRuntimeException.class)
	public void attemptToMoveDiagonally() throws StrategyException
	{
		addToMockConfiguration(LIEUTENANT, RED, 1, 1);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(LIEUTENANT, loc11, loc22);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveFurtherThanOneLocation() throws StrategyException
	{
		addToMockConfiguration(LIEUTENANT, RED, 1, 1);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(LIEUTENANT, loc11, loc13);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveFlag() throws StrategyException
	{
		game.move(FLAG, loc01, loc02);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveBomb() throws StrategyException
	{
		game.move(BOMB, loc00, loc01);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToRestartGameInProgress() throws StrategyException
	{
		game.move(SCOUT, loc43, loc44);
		game.startGame();
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToRestartCompletedGame() throws StrategyException
	{
		addToMockConfiguration(SCOUT, RED, 1, 2);
		addToMockConfiguration(FLAG, BLUE, 1, 3);
		addToMockConfiguration(MARSHAL, BLUE, 1, 4);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc12, loc13);
		mockGame.startGame();
	}
	
	@Test
	public void spyDefeatsMarshal() throws StrategyException
	{
		addToMockConfiguration(SPY, RED, 1, 2);
		addToMockConfiguration(SPY, BLUE, 1, 7);
		addToMockConfiguration(MARSHAL, BLUE, 1, 3);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(SPY, loc12, loc13);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(SPY, RED), loc13),
				moveResult.getBattleWinner());
		assertNull(mockGame.getPieceAt(loc12));
		assertEquals(new Piece(SPY, RED), mockGame.getPieceAt(loc13));
	}
	
	@Test
	public void marshalDefeatsSpy() throws StrategyException
	{
		addToMockConfiguration(SPY, BLUE, 1, 2);
		addToMockConfiguration(SPY, BLUE, 1, 7);
		addToMockConfiguration(MARSHAL, RED, 1, 3);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(MARSHAL, loc13, loc12);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(MARSHAL, RED), loc12),
				moveResult.getBattleWinner());
		assertNull(mockGame.getPieceAt(loc13));
		assertEquals(new Piece(MARSHAL, RED), mockGame.getPieceAt(loc12));
	}
	
	@Test
	public void bombWinsAgainstMarshalAndDoesntMove() throws StrategyException
	{
		addToMockConfiguration(BOMB, BLUE, 1, 2);
		addToMockConfiguration(SPY, RED, 1, 7);
		addToMockConfiguration(MARSHAL, RED, 1, 3);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(MARSHAL, loc13, loc12);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(BOMB, BLUE), loc12),
				moveResult.getBattleWinner());
		assertNull(mockGame.getPieceAt(loc13));
		assertEquals(new Piece(BOMB, BLUE), mockGame.getPieceAt(loc12));
	}
	
	@Test
	public void minerKillsBomb() throws StrategyException
	{
		addToMockConfiguration(BOMB, BLUE, 1, 2);
		addToMockConfiguration(SPY, BLUE, 1, 7);
		addToMockConfiguration(MINER, RED, 1, 3);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(MINER, loc13, loc12);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(MINER, RED), loc12),
				moveResult.getBattleWinner());
		assertNull(mockGame.getPieceAt(loc13));
		assertEquals(new Piece(MINER, RED), mockGame.getPieceAt(loc12));
	}
	
	@Test
	public void scoutMovesUp() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 9, 9);
		addToMockConfiguration(SCOUT, RED, 1, 5);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(SCOUT, loc15, loc19);
		assertEquals(OK, moveResult.getStatus());
		assertNull(mockGame.getPieceAt(loc15));
		assertEquals(new Piece(SCOUT, RED), mockGame.getPieceAt(loc19));
	}
	
	@Test
	public void scoutMovesDown() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 9, 9);
		addToMockConfiguration(SCOUT, RED, 1, 5);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(SCOUT, loc15, loc11);
		assertEquals(OK, moveResult.getStatus());
		assertNull(mockGame.getPieceAt(loc15));
		assertEquals(new Piece(SCOUT, RED), mockGame.getPieceAt(loc11));
	}
	
	@Test
	public void scoutMovesRight() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 9, 9);
		addToMockConfiguration(SCOUT, RED, 1, 1);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(SCOUT, loc11, loc51);
		assertEquals(OK, moveResult.getStatus());
		assertNull(mockGame.getPieceAt(loc11));
		assertEquals(new Piece(SCOUT, RED), mockGame.getPieceAt(loc51));
	}
	
	@Test
	public void scoutMovesLeft() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 9, 9);
		addToMockConfiguration(SCOUT, RED, 5, 1);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		final MoveResult moveResult = mockGame.move(SCOUT, loc51, loc11);
		assertEquals(OK, moveResult.getStatus());
		assertNull(mockGame.getPieceAt(loc55));
		assertEquals(new Piece(SCOUT, RED), mockGame.getPieceAt(loc11));
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotMoveOnAlliedPiece() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 9, 9);
		addToMockConfiguration(SCOUT, RED, 5, 1);
		addToMockConfiguration(SCOUT, RED, 1, 1);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc51, loc11);
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotAttackAtARange() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 1, 1);
		addToMockConfiguration(SCOUT, RED, 5, 1);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc51, loc11);
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotJumpOverPieceOnBottom() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 1, 1);
		addToMockConfiguration(SCOUT, RED, 5, 3);
		addToMockConfiguration(SCOUT, RED, 5, 2);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc53, loc51);
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotJumpOverPieceOnTop() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 1, 1);
		addToMockConfiguration(SCOUT, RED, 5, 1);
		addToMockConfiguration(SCOUT, BLUE, 5, 3);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc51, loc55);
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotJumpOverPieceOnLeft() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 9, 9);
		addToMockConfiguration(SCOUT, RED, 5, 1);
		addToMockConfiguration(SCOUT, RED, 3, 1);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc51, loc11);
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotJumpOverPieceOnRight() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 9, 9);
		addToMockConfiguration(SCOUT, RED, 1, 1);
		addToMockConfiguration(SCOUT, RED, 3, 1);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc11, loc51);
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotMoveToSelf() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 9, 9);
		addToMockConfiguration(SCOUT, RED, 1, 1);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc11, loc11);
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotMoveDiagonally() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 9, 9);
		addToMockConfiguration(SCOUT, RED, 1, 1);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc11, loc22);
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotJumpOverChokePoints() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 1, 1);
		addToMockConfiguration(SCOUT, RED, 5, 5);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc55, loc95);
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotMoveOntoChokePointsFromDistance() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 1, 1);
		addToMockConfiguration(SCOUT, RED, 5, 5);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc55, loc35);
	}
	
	@Test(expected=StrategyException.class)
	public void scoutCannotMoveOntoChokePoint() throws StrategyException
	{
		addToMockConfiguration(SCOUT, BLUE, 1, 1);
		addToMockConfiguration(SCOUT, RED, 5, 5);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(SCOUT, loc55, loc65);
	}
	
	@Test(expected=StrategyException.class)
	public void FlagCannotMove() throws StrategyException
	{
		addToMockConfiguration(FLAG, RED, 1, 1);
		addToMockConfiguration(SCOUT, BLUE, 5, 5);
		mockGame.setConfigurations(redMockConfiguration, blueMockConfiguration);
		mockGame.move(FLAG, loc11, loc12);
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
	
	private void addToMockConfiguration(PieceType type, PlayerColor color, int x, int y)
	{
		final PieceLocationDescriptor confItem = new PieceLocationDescriptor(
				new Piece(type, color),
				new Location2D(x, y));
		if (color == PlayerColor.RED) {
			redMockConfiguration.add(confItem);
		} else {
			blueMockConfiguration.add(confItem);
		}
	}
}

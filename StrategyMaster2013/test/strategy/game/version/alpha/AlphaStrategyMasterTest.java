/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.alpha;

import static org.junit.Assert.*;
import org.junit.*;
import strategy.common.*;
import strategy.game.StrategyGameController;
import strategy.game.common.*;

/**
 * Description
 * @author gpollice
 * @version Sep 6, 2013
 */
public class AlphaStrategyMasterTest
{
	private  StrategyGameController game;
	private final Location redMarshalLocation = new Location2D(0, 0);
	private final Location redFlagLocation = new Location2D(1, 0);
	private final Location blueFlagLocation = new Location2D(0, 1);
	private final Location blueMarshalLocation = new Location2D(1, 1);
	private final Piece redMarshal = new Piece(PieceType.MARSHAL, PlayerColor.RED);
	private final Piece redFlag = new Piece(PieceType.FLAG, PlayerColor.RED);
	private final Piece blueFlag = new Piece(PieceType.FLAG, PlayerColor.BLUE);
	private final Piece blueMarshal = new Piece(PieceType.MARSHAL, PlayerColor.BLUE);
	
	/**
	 * Method setup.
	 */
	@Before
	public  void setup()
	{
		game = new AlphaStrategyGameController();
	}
	
	/**
	 * Method makeMoveBeforeInitialization.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void makeMoveBeforeInitialization() throws StrategyException
	{
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
	}
	
	/**
	 * Method makeValidFirstMoveRedWins.
	 * @throws StrategyException
	 */
	@Test
	public void makeValidFirstMoveRedWins() throws StrategyException
	{
		game.startGame();
		final MoveResult result = 
				game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());
		assertEquals(new PieceLocationDescriptor(redMarshal, blueFlagLocation), 
				result.getBattleWinner());
	}
	
	/**
	 * Method makeMoveWithWrongPiece.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void makeMoveWithWrongPiece() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.CAPTAIN, redMarshalLocation, blueFlagLocation);
	}
	
	/**
	 * Method makeMoveWithIncorrectFromXCoordinate.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void makeMoveWithIncorrectFromXCoordinate() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(1, 0), blueFlagLocation);
	}
	
	/**
	 * Method makeMoveWithIncorrectFromYCoordinate.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void makeMoveWithIncorrectFromYCoordinate() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(0, 1), blueFlagLocation);
	}
	
	/**
	 * Method makeMoveWithIncorrectToXCoordinate.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void makeMoveWithIncorrectToXCoordinate() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, new Location2D(1, 0));
	}
	
	/**
	 * Method makeMoveWithIncorrectToYCoordinate.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void makeMoveWithIncorrectToYCoordinate() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, new Location2D(0, 0));
	}
	
	/**
	 * Method makeMoveAfterGameIsComplete.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void makeMoveAfterGameIsComplete() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
	}
	
	/**
	 * Method playTwoGames.
	 * @throws StrategyException
	 */
	@Test
	public void playTwoGames() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
	}
	
	/**
	 * Method initialPositionHasCorrectPiecesPlaced.
	 * @throws StrategyException
	 */
	@Test
	public void initialPositionHasCorrectPiecesPlaced() throws StrategyException
	{
		game.startGame();
		assertEquals(redMarshal, game.getPieceAt(redMarshalLocation));
		assertEquals(redFlag, game.getPieceAt(redFlagLocation));
		assertEquals(blueFlag, game.getPieceAt(blueFlagLocation));
		assertEquals(blueMarshal, game.getPieceAt(blueMarshalLocation));
	}
	
	/**
	 * Method boardIsCorrectAfterFirstMove.
	 * @throws StrategyException
	 */
	@Test
	public void boardIsCorrectAfterFirstMove() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
		assertNull(game.getPieceAt(redMarshalLocation));
		assertEquals(redFlag, game.getPieceAt(redFlagLocation));
		assertEquals(redMarshal, game.getPieceAt(blueFlagLocation));
		assertEquals(blueMarshal, game.getPieceAt(blueMarshalLocation));
	}
}


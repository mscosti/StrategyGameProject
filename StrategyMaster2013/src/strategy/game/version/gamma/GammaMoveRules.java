/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Created by Matt Costi and Joshua Morse
 *******************************************************************************/

package strategy.game.version.gamma;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.common.Board;
import strategy.game.version.common.HistoryLogger;
import strategy.game.version.common.PieceMove;
import strategy.game.version.common.StrategyMoveRules;

/**
 * @author Matt and Josh
 * @version gamma
 */
public class GammaMoveRules implements StrategyMoveRules {

	final Board board;
	final Location from, to;
	final int from_x, from_y, to_x, to_y;
	final PieceLocationDescriptor fromPiece;
	final HistoryLogger history;
	final PieceMove move;
	
	
	/**
	 * @param board
	 * @param fromPiece
	 * @param to
	 * @param history
	 * @param move
	 */
	public GammaMoveRules(Board board, PieceLocationDescriptor fromPiece, Location to, 
			HistoryLogger history, PieceMove move)
	{
		this.board = board;
		this.fromPiece = fromPiece;
		this.to = to;
		this.history = history;
		this.move = move;
		
		from = fromPiece.getLocation();
		from_x = from.getCoordinate(Coordinate.X_COORDINATE);
		from_y = from.getCoordinate(Coordinate.Y_COORDINATE);
		to_x = to.getCoordinate(Coordinate.X_COORDINATE);
		to_y = to.getCoordinate(Coordinate.Y_COORDINATE);
		
	}
	
	/* (non-Javadoc)
	 * @see strategy.game.version.common.StrategyMoveRules#checkValidMove()
	 */
	public void checkValidMove() throws StrategyException
	{
		checkBoundaries(); //Makes sure it isn't moving out of bounds
		checkValidPieceMove(); //Makes sure it isn't a flag or it isn't moving more than 1 space
		checkRepetitionRule(); //Makes sure the repetition rule is not being violated
	}
	
	
	/* (non-Javadoc)
	 * @see strategy.game.version.common.StrategyMoveRules#checkBoundaries()
	 */
	public void checkBoundaries() throws StrategyException
	{
		if (!board.inBounds(to))
		{
			throw new StrategyException("The to location is out of bounds");
		}
	}
	
	
	/**
	 * @throws StrategyException
	 */
	public void checkValidPieceMove() throws StrategyException
	{
		if (fromPiece.getPiece().getType() == PieceType.FLAG)
		{
			throw new StrategyException("The flag can not move!");
		}
		
		if (from.distanceTo(to) != 1)
		{
			throw new StrategyException("That move is invalid");
		}
	}
	
	/* (non-Javadoc)
	 * @see strategy.game.version.common.StrategyMoveRules#checkValidGameState(boolean, boolean)
	 */
	public void checkValidGameState(boolean gameStarted, boolean gameOver) 
			throws StrategyException
	{
		if (!gameStarted) 
		{
			throw new StrategyException("You must start the game!");
		}
		if (gameOver)
		{
			throw new StrategyException("Can't move after the game is over!");
		}
	}
	
	/* (non-Javadoc)
	 * @see strategy.game.version.common.StrategyMoveRules#checkValidTurnOrder(int)
	 */
	public void checkValidTurnOrder(int turn) 
			throws StrategyException
	{
		if (turn == 0 && fromPiece.getPiece().getOwner() != PlayerColor.RED)
		{
			throw new StrategyException("Red must go first!");
		}
		if ((fromPiece.getPiece().getOwner() == PlayerColor.RED && (turn % 2) == 1) || 
			(fromPiece.getPiece().getOwner() == PlayerColor.BLUE && (turn % 2) == 0))
		{
			throw new StrategyException("Can't play two times in a row!");
		}
	}
	
	/**
	 * @throws StrategyException
	 */
	public void checkRepetitionRule() throws StrategyException
	{
		if (history.getTurnCount() > 3)
		{
			final PieceMove prevMove1 = history.getHistory().get(history.getTurnCount() - 2);
			final PieceMove prevMove2 = history.getHistory().get(history.getTurnCount() - 4);
			 
			if ((prevMove2.getType().equals(move.getType()) &&
				prevMove2.getFrom().equals(move.getFrom()) &&
				prevMove2.getTo().equals(move.getTo())) 
				&&
				prevMove1.getType().equals(move.getType()) &&
				prevMove1.getFrom().equals(move.getTo()) &&
				prevMove1.getTo().equals(move.getFrom())) 
			{
				throw new StrategyException("Move Repetition! Invalid!");
			}
		}
	}
}

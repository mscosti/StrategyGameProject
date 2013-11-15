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

package strategy.game.version.epsilon;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.Location2D;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.common.Board;
import strategy.game.version.common.HistoryLogger;
import strategy.game.version.common.PieceHandler;
import strategy.game.version.common.PieceMove;
import strategy.game.version.common.StrategyMoveRules;

/**
 * @author Matt and Josh
 * @version gamma
 */
public class EpsilonMoveRules implements StrategyMoveRules {

	final Board board;
	final Location from, to;
	final int from_x, from_y, to_x, to_y;
	final PieceLocationDescriptor fromPiece;
	final HistoryLogger history;
	final PieceMove move;
	final PieceHandler piecehandler;
	
	/**
	 * @param board
	 * @param fromPiece
	 * @param to
	 * @param history
	 * @param move
	 * @param piecehandler PieceHandler
	 */
	public EpsilonMoveRules(Board board, PieceLocationDescriptor fromPiece, Location to, 
			HistoryLogger history, PieceMove move, PieceHandler piecehandler)
	{
		this.board = board;
		this.fromPiece = fromPiece;
		this.to = to;
		this.history = history;
		this.move = move;
		this.piecehandler = piecehandler;
		
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
	
	 * @throws StrategyException */
	public void checkValidPieceMove() throws StrategyException
	{
		if (fromPiece.getPiece().getType() == PieceType.FLAG)
		{
			throw new StrategyException("The flag can not move!");
		}
		
		if (fromPiece.getPiece().getType() == PieceType.BOMB)
		{
			throw new StrategyException("The bomb can not move!");
		}
		
		if (fromPiece.getPiece().getType() == PieceType.SCOUT)
		{
			checkScoutMove();
		}
		else if(fromPiece.getPiece().getType() == PieceType.FIRST_LIEUTENANT)
		{
			checkFirstLieutenantMove();
		}
		else if (from.distanceTo(to) != 1)
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
	
	 * @throws StrategyException */
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
	
	/**
	 * Method checkFirstLieutenantMove.
	 * @throws StrategyException
	 */
	public void checkFirstLieutenantMove() throws StrategyException
	{
		if (from.distanceTo(to) == 2 && piecehandler.getPieceDescriptorAt(to) == null)
		{
			throw new StrategyException(
					"First Lieutenant cannot move 2 spaces forward unless attacking!");
		}
		if (from.distanceTo(to) > 2)
		{
			throw new StrategyException(
					"cannot move more than 2 spaces away");
		}
		checkPieceJumping();
	}
	
	
	/**
	 * Method checkScoutMove.
	
	 * @throws StrategyException */
	public void checkScoutMove() throws StrategyException 
	{
		if (from_x == to_x && from_y == to_y)
		{
			throw new StrategyException("Scout can not move to itself!");
		}
		if (from_x != to_x && from_y != to_y)
		{
			throw new StrategyException("Scout must move in a straight line!");
		}
		if (((Math.abs(from_x - to_x) > 1) || Math.abs(from_y - to_y) > 1) &&
			(piecehandler.getPieceDescriptorAt(to) != null)) 
		{
			throw new StrategyException("Scout can not move on to piece at a distance!");
		}
		checkPieceJumping();
	}
	
	/**
	 * Method checkPieceJumping.
	 * @throws StrategyException
	 */
	public void checkPieceJumping() throws StrategyException
	{
		if (from_x < to_x)
		{
			for (int i = from_x + 1; i < to_x; i++)
			{
				if (piecehandler.getPieceDescriptorAt(new Location2D(i, to_y)) != null) 
				{
					throw new StrategyException("Piece can not jump over others pieces!");
				}
			}
		}
		else if (from_x > to_x)
		{
			for (int i = from_x - 1; i > to_x; i--)
			{
				if (piecehandler.getPieceDescriptorAt(new Location2D(i, to_y)) != null) 
				{
					throw new StrategyException("Piece can not jump over other pieces!");
				}
			}
		}
		else if (from_y < to_y)
		{
			for (int i = from_y + 1; i < to_y; i++)
			{
				if (piecehandler.getPieceDescriptorAt(new Location2D(to_x, i)) != null) 
				{
					throw new StrategyException("Piece can not jump over other pieces!");
				}
			}
		}
		else if (from_y > to_y)
		{
			for (int i = from_y - 1; i > to_y; i--)
			{
				if (piecehandler.getPieceDescriptorAt(new Location2D(to_x, i)) != null) 
				{
					throw new StrategyException("Piece can not jump over other pieces!");
				}
			}
		}
	}
}

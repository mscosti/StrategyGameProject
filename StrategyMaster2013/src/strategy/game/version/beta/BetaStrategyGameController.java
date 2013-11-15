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

package strategy.game.version.beta;

import java.util.List;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

/**
 * @author Josh, Matt
 * @version $Revision: 1.0 $
 */
public class BetaStrategyGameController implements StrategyGameController {

	private boolean gameStarted;
	private boolean gameOver;
	private PlayerColor previousTurn;
	private int turnCount;
	private final List<PieceLocationDescriptor> bluePieces;
	private final List<PieceLocationDescriptor> redPieces;
	
	/**
	 * Constructor for BetaStrategyGameController.
	 * @param redConfigurationList ArrayList<PieceLocationDescriptor>
	 * @param blueConfigurationList ArrayList<PieceLocationDescriptor>
	 */
	public BetaStrategyGameController(List<PieceLocationDescriptor> redConfigurationList, 
			List<PieceLocationDescriptor> blueConfigurationList)
	{
		gameStarted = false;
		gameOver = false;
		previousTurn = null;
		turnCount = 0;
		bluePieces = redConfigurationList;
		redPieces = blueConfigurationList;
	}
	
	@Override
	public void startGame() throws StrategyException {
		
		if (gameStarted)
		{
			throw new StrategyException(
					"Game has already been started!");
		}
		gameStarted = true; 
		gameOver = false;
		
	}

	@Override
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException 
			{
		
		MoveResult result = null;
		final int from_x = from.getCoordinate(Coordinate.X_COORDINATE);
		final int from_y = from.getCoordinate(Coordinate.Y_COORDINATE);
		final int to_x = to.getCoordinate(Coordinate.X_COORDINATE);
		final int to_y = to.getCoordinate(Coordinate.Y_COORDINATE);
		final PieceLocationDescriptor fromPiece = getPieceDescriptorAt(from);
		
		if (!gameStarted) 
		{
			throw new StrategyException("You must start the game!");
		}
		if (gameOver)
		{
			throw new StrategyException("Can't move after the game is over!");
		}
		if (piece == PieceType.FLAG)
		{
			throw new StrategyException("The flag can not move!");
		}
		if (from_x > 5 || from_x < 0 || from_y > 5 || from_y < 0)
		{
			throw new StrategyException("The from location is out of bounds");
		}
		if (to_x > 5 || to_x < 0 || to_y > 5 || to_y < 0)
		{
			throw new StrategyException("The to location is out of bounds");
		}
		if (!((Math.abs(from_x - to_x) == 1 && Math.abs(from_y - to_y) == 0) ||
			(Math.abs(from_x - to_x) == 0 && Math.abs(from_y - to_y) == 1)))
		{
			throw new StrategyException("That move is invalid");
		}
		if (getPieceAt(from) == null || getPieceAt(from).getType() != piece) 
		{
			throw new StrategyException("The piece you are trying to move does not exist");
		}
		if (previousTurn == null && getPieceAt(from).getOwner() != PlayerColor.RED)
		{
			throw new StrategyException("Red must go first!");
		}
		if (fromPiece.getPiece().getOwner() == previousTurn)
		{
			throw new StrategyException("Can't play two times in a row!");
		}
		if (getPieceAt(to) == null)
		{
			final PieceLocationDescriptor movedPiece = 
					new PieceLocationDescriptor(getPieceAt(from), to);
			updatePieces(fromPiece, movedPiece);
			result = new MoveResult(MoveResultStatus.OK, movedPiece);
		}
		else 
		{
			if (fromPiece.getPiece().getOwner() == getPieceDescriptorAt(to).getPiece().getOwner())
			{
				throw new StrategyException("Can't move piece onto another allied piece");
			}
			else //do battle
			{
				result = doBattle(fromPiece, getPieceDescriptorAt(to));
			} 
		}
		previousTurn = fromPiece.getPiece().getOwner();
		turnCount++;
		
		if (turnCount == 12) 
		{
			gameOver = true;
			if (result.getStatus() != MoveResultStatus.RED_WINS && result.getStatus() != MoveResultStatus.BLUE_WINS)
			{
				result = new MoveResult(MoveResultStatus.DRAW, null);
			}
		}
		return result;
	}

	/**
	 * Method doBattle.
	 * @param striker PieceLocationDescriptor
	 * @param opponent PieceLocationDescriptor
	
	 * @return MoveResult */
	public MoveResult doBattle(PieceLocationDescriptor striker, PieceLocationDescriptor opponent)
	{
		final BattleRules rules = new BattleRules();
		MoveResult battleWinner = null;
		final int strikerPower = rules.getPowerLevel(striker.getPiece().getType());
		final int opponentPower = rules.getPowerLevel(opponent.getPiece().getType());
		
		if (opponent.getPiece().getType() == PieceType.FLAG) 
		{
			if (striker.getPiece().getOwner() == PlayerColor.RED)
			{
				battleWinner = new MoveResult(MoveResultStatus.RED_WINS, striker);
			}
			else 
			{
				battleWinner = new MoveResult(MoveResultStatus.BLUE_WINS, striker);
			}
			gameOver = true;
		}
		else if (strikerPower - opponentPower > 0) //striker wins battle
		{
			final PieceLocationDescriptor takeOpponent = 
					new PieceLocationDescriptor(striker.getPiece(), opponent.getLocation());
			updatePieces(striker, takeOpponent);
			removePiece(opponent);
			battleWinner = new MoveResult(MoveResultStatus.OK, takeOpponent);
		}
		else if(strikerPower - opponentPower < 0) //opponent wins battle
		{
			final PieceLocationDescriptor takeStriker = 
					new PieceLocationDescriptor(opponent.getPiece(), striker.getLocation());
			updatePieces(opponent, takeStriker);
			removePiece(striker);
			battleWinner = new MoveResult(MoveResultStatus.OK, takeStriker);
		}
		else //if strikerPower-opponentPower is 0 ; nobody wins
		{
			removePiece(striker);
			removePiece(opponent);
			battleWinner = new MoveResult(MoveResultStatus.OK, null);
		}
		return battleWinner;
	}
	
	@Override
	public Piece getPieceAt(Location location) 
	{
		
		for (PieceLocationDescriptor piece: bluePieces)
		{
			if (piece.getLocation().equals(location))
			{
				return piece.getPiece();
			}
		} 
		
		for (PieceLocationDescriptor piece: redPieces)
		{
			if (piece.getLocation().equals(location))
			{
				return piece.getPiece();
			}
		}
		
		return null;
	}
	
	//This method is given a location and returns the
	//PieceLocationDescriptor of the piece at that location
	//Returns null if no piece is present
	/**
	 * Method getPieceDescriptorAt.
	 * @param location Location
	
	 * @return PieceLocationDescriptor */
	public PieceLocationDescriptor getPieceDescriptorAt(Location location) 
	{
		for (PieceLocationDescriptor piece: redPieces)
		{
			if (piece.getLocation().equals(location))
			{
				return piece;
			}
		}
		for (PieceLocationDescriptor piece: bluePieces)
		{
			if (piece.getLocation().equals(location))
			{
				return piece;
			}
		} 
		return null;
	}
	
	/**
	 * Method updatePieces.
	 * @param oldPiece PieceLocationDescriptor
	 * @param newPiece PieceLocationDescriptor
	 */
	public void updatePieces(PieceLocationDescriptor oldPiece, PieceLocationDescriptor newPiece) 
	{
		
		for (int i = 0; i < redPieces.size(); i++) 
		{
			if (oldPiece.equals(redPieces.get(i)))
			{
				redPieces.set(i, newPiece);
			}
		}
		for (int i = 0; i < bluePieces.size(); i++) 
		{
			if (oldPiece.equals(bluePieces.get(i)))
			{
				bluePieces.set(i, newPiece);
			}
		}
	}
	
	/**
	 * Method removePiece.
	 * @param deadPiece PieceLocationDescriptor
	 */
	public void removePiece(PieceLocationDescriptor deadPiece) 
	{
		
		for (int i = 0; i < redPieces.size(); i++) 
		{
			if (deadPiece.equals(redPieces.get(i)))
			{
				redPieces.remove(deadPiece);
			}
		}
		for (int i = 0; i < bluePieces.size(); i++) 
		{
			if (deadPiece.equals(bluePieces.get(i)))
			{
				bluePieces.remove(deadPiece);
			}
		}
	}
}

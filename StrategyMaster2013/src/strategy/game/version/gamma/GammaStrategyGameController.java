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

import java.util.List;

import static strategy.common.PlayerColor.*;
import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import static strategy.game.common.Coordinate.*;
import static strategy.game.common.MoveResultStatus.*;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.common.BattleResult;
import strategy.game.version.common.HistoryLogger;
import strategy.game.version.common.PieceMove;
import strategy.game.version.common.StrategyBattleRules;
import strategy.game.version.common.Board;
import strategy.game.version.common.PieceHandler;
import strategy.game.version.common.StrategyMoveRules;
import strategy.game.version.common.validation.StrategyValidConfiguration;

/**
 * @author Josh, Matt
 * @version $Revision: 2.0GAMMA $
 */
public class GammaStrategyGameController implements StrategyGameController {

	private boolean gameStarted;
	private boolean gameOver;
	private final PieceHandler pieceHandler;
	private final Board board;
	private final StrategyBattleRules battleRules;
	private final HistoryLogger history;

	/**
	 * @param redConfigurationList
	 * @param blueConfigurationList
	 * @param chokePointConfigurationList
	 * @throws StrategyException
	 */
	public GammaStrategyGameController(List<PieceLocationDescriptor> redConfigurationList, 
			List<PieceLocationDescriptor> blueConfigurationList,
			List<PieceLocationDescriptor> chokePointConfigurationList) throws StrategyException
	{
		gameStarted = false;
		gameOver = false;
		pieceHandler = new PieceHandler(redConfigurationList, blueConfigurationList, chokePointConfigurationList);
		battleRules = new GammaBattleRules();
		board = new Board(6, 6);
		history = new HistoryLogger();

		final StrategyValidConfiguration gammaValidator = new GammaValidConfiguration(
				redConfigurationList, blueConfigurationList, board);

		if (!gammaValidator.isCorrectPieceInput(BLUE) ||
				!gammaValidator.isCorrectPieceInput(RED))
		{
			throw new StrategyException("Incorrect Piece Input");
		}

		final int blueLocationCheck = gammaValidator.isCorrectLocationInput(BLUE, 2);
		final int redLocationCheck = gammaValidator.isCorrectLocationInput(RED, 2);
		if (blueLocationCheck == -1 || redLocationCheck == -1)
		{
			throw new StrategyException("Pieces are stacked");
		}
			
		if (blueLocationCheck == -2 || redLocationCheck == -2) 
		{
			throw new StrategyException("Pieces are out of bounds");
		}
	}

	/* (non-Javadoc)
	 * @see strategy.game.StrategyGameController#startGame()
	 */
	@Override
	public void startGame() throws StrategyException
	{
		if (gameStarted)
		{
			throw new StrategyException(
					"Game has already been started!");
		}
		gameStarted = true; 
		gameOver = false;
	}

	/* (non-Javadoc)
	 * @see strategy.game.StrategyGameController#move(strategy.game.common.PieceType, strategy.game.common.Location, strategy.game.common.Location)
	 */
	@Override
	public MoveResult move(PieceType piece, Location from, Location to) throws StrategyException 
	{
		final PieceMove thisMove = new PieceMove(piece, from, to);
		final PieceLocationDescriptor fromPiece = pieceHandler.getPieceDescriptorAt(from);
		MoveResult result = null;

		if (getPieceAt(from) == null || getPieceAt(from).getType() != piece) 
		{
			throw new StrategyException("The piece you are trying to move does not exist");
		}

		final StrategyMoveRules gammaMoveRules = new GammaMoveRules(board, fromPiece, to, history, thisMove);
		gammaMoveRules.checkValidGameState(gameStarted, gameOver);
		gammaMoveRules.checkValidTurnOrder(history.getTurnCount());
		gammaMoveRules.checkValidMove();

		if (getPieceAt(to) == null) //No piece on to location
		{
			final PieceLocationDescriptor movedPiece = new PieceLocationDescriptor(getPieceAt(from), to);
			pieceHandler.updatePiece(fromPiece, movedPiece);
			result = new MoveResult(OK, movedPiece);
		}
		else //Piece at to location
		{
			if(getPieceAt(to).getType() == PieceType.CHOKE_POINT)
			{
				throw new StrategyException("Can't move to a choke point!");
			}
			if (fromPiece.getPiece().getOwner() == pieceHandler.getPieceDescriptorAt(to).getPiece().getOwner())
			{
				throw new StrategyException("Can't move piece onto another allied piece");
			}
			else //Hit another piece, battle time
			{
				result = doBattle(fromPiece, pieceHandler.getPieceDescriptorAt(to));
			}
		}
		history.add(thisMove);
		//Check to see if a player can no longer move
		result = endOfMoveChecker(result);
		
		return result;
	}

	/**
	 * Method doBattle.
	 * @param striker PieceLocationDescriptor
	 * @param opponent PieceLocationDescriptor
	 * @return MoveResult
	 * Computes the result of a battle between 2 pieces and moves/removes the pieces
	 * */
	public MoveResult doBattle(PieceLocationDescriptor striker, PieceLocationDescriptor opponent)
	{
		MoveResult battleWinner = null;
		final BattleResult result = battleRules.determineBattleWinner(striker, opponent);

		if (result.getWinner() != null) //If a winner exists
		{
			battleWinner = new MoveResult(OK, result.getWinnerLocation());
			pieceHandler.updatePiece(result.getWinner(), result.getWinnerLocation());
			pieceHandler.removePiece(result.getLoser());
		}
		else //Both pieces died
		{
			battleWinner = new MoveResult(OK, null);
			pieceHandler.removePiece(striker);
			pieceHandler.removePiece(opponent);
		}

		if (opponent.getPiece().getType() == PieceType.FLAG) //If flag, you win 
		{
			if (striker.getPiece().getOwner() == RED)
			{
				battleWinner = new MoveResult(RED_WINS, striker);
			}
			else 
			{
				battleWinner = new MoveResult(BLUE_WINS, striker);
			}

			gameOver = true;
		}
		return battleWinner;
	}

	/* (non-Javadoc)
	 * @see strategy.game.StrategyGameController#getPieceAt(strategy.game.common.Location)
	 */
	@Override
	public Piece getPieceAt(Location location) 
	{
		final PieceLocationDescriptor piece = pieceHandler.getPieceDescriptorAt(location);
		if (piece == null)
		{
			return null;
		}
		else 
		{
			return piece.getPiece();
		}
	}
	
	/**
	 * @param battleResult
	 * @return MoveResult
	 * Checks to see if the game is over due to one player not being able to make a move
	 */
	public MoveResult endOfMoveChecker(MoveResult battleResult) 
	{
		MoveResult result = battleResult;
		boolean redCanMove = false;
		boolean blueCanMove = false;
		
		if (result.getStatus() == OK) //If someone has not already won
		{
			for (PieceLocationDescriptor piece: pieceHandler.getRedPieces())
			{
				if (canMove(piece))
				{
					redCanMove = true;
					break;
				}
			}
			for (PieceLocationDescriptor piece: pieceHandler.getBluePieces())
			{
				if (canMove(piece)) 
				{
					blueCanMove = true;
					break;
				}
			}
			if (!redCanMove && !blueCanMove) //Neither team can move, draw
			{
				result = new MoveResult(DRAW, result.getBattleWinner());
				gameOver = true;
			}
			else if (!redCanMove) //Red can not move, blue wins
			{
				result = new MoveResult(BLUE_WINS, result.getBattleWinner());
				gameOver = true;
			}
			else if (!blueCanMove) //Blue can not move, red wins
			{
				result = new MoveResult(RED_WINS, result.getBattleWinner());
				gameOver = true;
			}
		}
		return result;
	}
	
	/**
	 * @param pieceDescriptor
	 * @return boolean if the piece can move
	 * Helper function that determines if a piece can move
	 */
	public boolean canMove(PieceLocationDescriptor pieceDescriptor)
	{
		final Piece piece = pieceDescriptor.getPiece();
		final Location location = pieceDescriptor.getLocation();
		final int xCoord = location.getCoordinate(X_COORDINATE);
		final int yCoord = location.getCoordinate(Y_COORDINATE);
		if (piece.getType() == PieceType.FLAG)
		{
			return false;
		}
		final Location up = new Location2D(xCoord, yCoord + 1);
		final Location left = new Location2D(xCoord - 1, yCoord);
		final Location down = new Location2D(xCoord, yCoord - 1);
		final Location right = new Location2D(xCoord + 1, yCoord);
		final PlayerColor color = piece.getOwner();
		//Return true if the piece can move to any surrounding space
		return (movableSpace(color, up) || movableSpace(color, left) ||
				movableSpace(color, down) || movableSpace(color, right)); 
	}
	
	/**
	 * @param movedPieceColor
	 * @param to
	 * @return boolean if the space is movable
	 * Helper function that computes if a space if movable on or not by a given player
	 */
	public boolean movableSpace(PlayerColor movedPieceColor, Location to)
	{
		if (board.inBounds(to))
		{
			if (getPieceAt(to) != null)
			{
				if (getPieceAt(to).getType() == PieceType.CHOKE_POINT)
				{
					return false; //Choke Point
				}
				else if (getPieceAt(to).getOwner() == movedPieceColor) 
				{
					return false; //Same color
				}
				else return true; //Enemy piece
			}
			else return true; //Empty location
		}
		return false; //Out of bounds
	}
}
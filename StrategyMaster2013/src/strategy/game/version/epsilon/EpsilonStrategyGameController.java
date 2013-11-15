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

import java.util.ArrayList;
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
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObservable;
import strategy.game.common.StrategyGameObserver;
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
public class EpsilonStrategyGameController implements StrategyGameController, StrategyGameObservable {

	private boolean gameStarted;
	private boolean gameOver;
	protected PieceHandler pieceHandler;
	private final Board board;
	private final StrategyBattleRules battleRules;
	private final HistoryLogger history;
	private int flagCount;
	private List<StrategyGameObserver> observerList;

	/**
	 * @param redConfigurationList
	 * @param blueConfigurationList
	 * @param chokePointConfigurationList
	
	 * @throws StrategyException */
	public EpsilonStrategyGameController(List<PieceLocationDescriptor> redConfigurationList, 
			List<PieceLocationDescriptor> blueConfigurationList,
			List<PieceLocationDescriptor> chokePointConfigurationList) throws StrategyException
	{
		gameStarted = false;
		gameOver = false;
		pieceHandler = new PieceHandler(redConfigurationList, blueConfigurationList, chokePointConfigurationList);
		battleRules = new EpsilonBattleRules();
		board = new Board(10, 10);
		history = new HistoryLogger();
		flagCount = 2;

		final StrategyValidConfiguration epsilonValidator = new EpsilonValidConfiguration(
				redConfigurationList, blueConfigurationList, board);

		if (!epsilonValidator.isCorrectPieceInput(BLUE) ||
				!epsilonValidator.isCorrectPieceInput(RED))
		{
			throw new StrategyException("Incorrect Piece Input");
		}

		final int blueLocationCheck = epsilonValidator.isCorrectLocationInput(BLUE, 4);
		final int redLocationCheck = epsilonValidator.isCorrectLocationInput(RED, 4);
		if (blueLocationCheck == -1 || redLocationCheck == -1)
		{
			throw new StrategyException("Pieces are stacked");
		}
			
		if (blueLocationCheck == -2 || redLocationCheck == -2) 
		{
			throw new StrategyException("Pieces are out of bounds");
		}
		
		observerList = new ArrayList<StrategyGameObserver>();
		
	}

	/* (non-Javadoc)
	 * @see strategy.game.StrategyGameController#startGame()
	 */
	@Override
	public void startGame() throws StrategyException
	{
		try
		{
			if (gameStarted)
			{
				throw new StrategyException(
						"Game has already been started!");
			}
			gameStarted = true; 
			gameOver = false;
			notifyAllObserversOfGameStart();
		}catch(StrategyException e)
		{
			notifyAllObserversOfMove(null,null,null,null,e);
			throw e;
		}
		
	}

	/* (non-Javadoc)
	 * @see strategy.game.StrategyGameController#move(strategy.game.common.PieceType, strategy.game.common.Location, strategy.game.common.Location)
	 */
	@Override
	public MoveResult move(PieceType piece, Location from, Location to) throws StrategyException 
	{
		try{
			final PieceMove thisMove = new PieceMove(piece, from, to);
			final PieceLocationDescriptor fromPiece = pieceHandler.getPieceDescriptorAt(from);
			MoveResult result = null;
			final MoveResult resign = resign(piece, from, to);
			
			if (resign != null) return resign;
			
			if (getPieceAt(from) == null || getPieceAt(from).getType() != piece) 
			{
				throw new StrategyException("The piece you are trying to move does not exist");
			}

			final StrategyMoveRules epsilonMoveRules = new EpsilonMoveRules(board, fromPiece, to, history, thisMove, pieceHandler);
			epsilonMoveRules.checkValidGameState(gameStarted, gameOver);
			epsilonMoveRules.checkValidTurnOrder(history.getTurnCount());
			epsilonMoveRules.checkValidMove();

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
			notifyAllObserversOfMove(piece, from, to, result, null);
			return result;
			
		}catch(StrategyException e){
			notifyAllObserversOfMove(null,null,null,null,e);
			throw e;
		}	
	}

	/**
	 * Method resign.
	 * @param piece PieceType
	 * @param from Location
	 * @param to Location
	 * @return MoveResult
	 */
	public MoveResult resign(PieceType piece, Location from, Location to)
	{
		if (piece == null && from == null && to == null)
		{
			final int turnCount = history.getTurnCount();
			//blue moved last, so blue wins
			if (turnCount %2 == 0) return new MoveResult(MoveResultStatus.BLUE_WINS, null);
			//red moved last, so red wins
			else return new MoveResult(MoveResultStatus.RED_WINS, null);
		}
		else return null;
	}
	/** 
	 * Method doBattle.
	 * @param striker PieceLocationDescriptor
	 * @param opponent PieceLocationDescriptor
	
	 * * @return MoveResult
	 * Computes the result of a battle between 2 pieces and moves/removes the pieces */
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

		if (opponent.getPiece().getType() == PieceType.FLAG && flagCount == 1) //If flag, you win 
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
		else if(opponent.getPiece().getType() == PieceType.FLAG && flagCount == 2)
		{
//			System.out.println(result.getWinner() + "" + result.getWinnerLocation());
			pieceHandler.updatePiece(result.getWinner(), result.getWinnerLocation());
			pieceHandler.removePiece(opponent);
			battleWinner = new MoveResult(FLAG_CAPTURED, striker);
			flagCount--;
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
	 * Checks to see if the game is over due to one player not being able to make a move */
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
	 * Helper function that determines if a piece can move */
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
	 * Helper function that computes if a space if movable on or not by a given player */
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

	@Override
	public void register(StrategyGameObserver observer) {
		
		if (!observerList.contains(observer)) {
			observerList.add(observer);
		}
		
	}

	@Override
	public void unregister(StrategyGameObserver observer) {
		
		if (observerList.contains(observer)) {
			observerList.remove(observer);
		}
		
	}
	
	/**
	 * @param observers
	 */
	public void setObservers(List<StrategyGameObserver> observers)
	{
		observerList = observers;
	}
	
	/**
	 * notifies all the observers of a move in the game
	 * @param piece
	 * @param from
	 * @param to
	 * @param result
	 * @param fault
	 */
	public void notifyAllObserversOfMove(PieceType piece, Location from, Location to, 
			 MoveResult result, StrategyException fault)
	{
		if (observerList != null)
		{
			for (StrategyGameObserver observer: observerList) 
			{
				observer.moveHappened(piece, from, to, result, fault);
			}
		}
	}
	
	/**
	 * notifies all observers of the game starting
	 */
	public void notifyAllObserversOfGameStart()
	{
		if (observerList != null)
		{
			for (StrategyGameObserver observer: observerList) 
			{
				observer.gameStart(pieceHandler.getRedPieces(),pieceHandler.getBluePieces());
			}
		}
	}
}
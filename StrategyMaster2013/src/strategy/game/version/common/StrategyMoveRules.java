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

package strategy.game.version.common;

import strategy.common.StrategyException;

/**
 * @author Matt and Josh
 * @version 1.0
 * 
 * A strategy pattern for MoveRules. It contains validators for 
 * valid moves, and can be implemented differently for different
 * types of pieces that may exist in other versions of the game
 */
public interface StrategyMoveRules 
{
	/**
	 * checks to see if a move is valid
	 * @throws StrategyException
	 */
	void checkValidMove() throws StrategyException;
	
	/**
	 * checks to see if a move is in the boundaries
	 * @throws StrategyException
	 */
	void checkBoundaries() throws StrategyException;
	
	/**
	 * checks to see if the game is in a valid state
	 * @param gameStarted
	 * @param gameOver
	 * @throws StrategyException
	 */
	void checkValidGameState(boolean gameStarted, boolean gameOver) 
			throws StrategyException;
	
	/**
	 * checks to see if the turn order is valid
	 * @param turn
	 * @throws StrategyException
	 */
	void checkValidTurnOrder(int turn) throws StrategyException;
	
	
}

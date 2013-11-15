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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matt and Josh
 * @version 1.0
 * A logger that keeps track of all of the moves played in 
 * a game of strategy
 */
public class HistoryLogger 
{
	
	private final List<PieceMove> history;
	int turnCount;
	
	/**
	 * constructor for a history logger
	 */
	public HistoryLogger() 
	{
		history = new ArrayList<PieceMove>();
		turnCount = 0; 
	}
		
	/**
	 * @param nextmove
	 * @return int number of moves so far
	 * 
	 * Adds a piece to the log
	 */
	public int add(PieceMove nextmove)
	{
		history.add(turnCount, nextmove);
		turnCount++;
		return turnCount;
	}
	
	/**
	 * @return int number of moves so far
	 */
	public int getTurnCount() 
	{
		return turnCount;
	}
	
	/**
	 * @return the history log 
	 */
	public List<PieceMove> getHistory()
	{
		return history;
	}
}

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

import strategy.game.common.PieceLocationDescriptor;

/** 
 * A class for holding the winner and loser of a battle
 * and the updated location the winer will take
 * @author Matt and Josh
 * @version 1.0
 */
public class BattleResult 
{
	private final PieceLocationDescriptor 
			winnerTakeLoserLocation,
			winner,
			loser;
	
	/**
	 * constructor the a battle result object
	 * @param winnerTakeLoserLocation
	 * @param winner
	 * @param loser
	 */
	public BattleResult(PieceLocationDescriptor winnerTakeLoserLocation,
			PieceLocationDescriptor winner,
			PieceLocationDescriptor loser)
	{
		this.winnerTakeLoserLocation = winnerTakeLoserLocation;
		this.winner = winner;
		this.loser = loser;
	}
	
	/**
	 * @return the PieceLocationDescriptor with the winner in the
	 * 			new location
	 */
	public PieceLocationDescriptor getWinnerLocation()
	{
		return winnerTakeLoserLocation;
	}
	
	/**
	 * @return the winner of the battle
	 */
	public PieceLocationDescriptor getWinner()
	{
		return winner;
	}
	
	/**
	 * @return the loser of the battle
	 */
	public PieceLocationDescriptor getLoser()
	{
		return loser;
	}
}

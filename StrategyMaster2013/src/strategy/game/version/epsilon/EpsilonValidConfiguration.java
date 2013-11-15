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

import java.util.Collection;

import strategy.common.PlayerColor;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.common.Board;
import strategy.game.version.common.validation.StrategyValidConfiguration;
import strategy.game.version.common.validation.GeneralValidator;

/**
 * @author Matt and Josh
 * @version gamma
 */
public class EpsilonValidConfiguration implements StrategyValidConfiguration{
	
	Collection <PieceLocationDescriptor> blueConfiguration;
	Collection <PieceLocationDescriptor> redConfiguration;
	Board board;

	
	/**
	 * @param blueConfiguration
	 * @param redConfiguration
	 * @param board
	 */
	public EpsilonValidConfiguration(Collection <PieceLocationDescriptor> blueConfiguration, 
			Collection <PieceLocationDescriptor> redConfiguration, Board board)
	{
		this.blueConfiguration = blueConfiguration;
		this.redConfiguration = redConfiguration;
		this.board = board;
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.common.validation.StrategyValidConfiguration#isCorrectPieceInput(strategy.common.PlayerColor)
	 */
	public boolean isCorrectPieceInput(PlayerColor team)
	{
		Collection <PieceLocationDescriptor> configuration;
		
		if(team == PlayerColor.BLUE) configuration = blueConfiguration;
		else configuration = redConfiguration;
				
		int flagCounter = 0;
		int marshalCounter = 0;
		int spyCounter = 0;
		int generalCounter = 0;
		int colonelCounter = 0;
		int majorCounter = 0;
		int captainCounter = 0;
		int lieutenantCounter = 0;
		int firstLieutenantCounter = 0;
		int sergeantCounter = 0;
		int minerCounter = 0;
		int scoutCounter = 0;
		int bombCounter = 0;
		
		for (PieceLocationDescriptor piece: configuration) 
		{
			if (piece.getPiece().getType() == PieceType.FLAG) flagCounter++;
			else if (piece.getPiece().getType() == PieceType.MARSHAL) marshalCounter++;
			else if (piece.getPiece().getType() == PieceType.SPY) spyCounter++;
			else if (piece.getPiece().getType() == PieceType.GENERAL) generalCounter++;
			else if (piece.getPiece().getType() == PieceType.COLONEL) colonelCounter++;
			else if (piece.getPiece().getType() == PieceType.MAJOR) majorCounter++;
			else if (piece.getPiece().getType() == PieceType.CAPTAIN) captainCounter++;
			else if (piece.getPiece().getType() == PieceType.LIEUTENANT) lieutenantCounter++;
			else if (piece.getPiece().getType() == PieceType.FIRST_LIEUTENANT) firstLieutenantCounter++;
			else if (piece.getPiece().getType() == PieceType.SERGEANT) sergeantCounter++;
			else if (piece.getPiece().getType() == PieceType.MINER) minerCounter++;
			else if (piece.getPiece().getType() == PieceType.SCOUT) scoutCounter++;
			else if (piece.getPiece().getType() == PieceType.BOMB) bombCounter++;
		}
		
		//Will return true if there are the appropriate number of each piece
		return (flagCounter == 2 && marshalCounter <= 1 &&
				spyCounter <= 1 && generalCounter <= 1 &&
				colonelCounter <= 2 && majorCounter <= 3 &&
				captainCounter <= 4 && lieutenantCounter <= 4 && 
				sergeantCounter <= 4 && minerCounter <= 5 &&
				scoutCounter <= 8 && bombCounter <= 6) && 
				firstLieutenantCounter <= 2 &&configuration.size() == 40;
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.common.validation.StrategyValidConfiguration#isCorrectLocationInput(strategy.common.PlayerColor, int)
	 */
	public int isCorrectLocationInput(PlayerColor team, int rows)
	{
		Collection <PieceLocationDescriptor> configuration;

		if(team == PlayerColor.BLUE) configuration = blueConfiguration;
		else configuration = redConfiguration;
		
		return GeneralValidator.correctPieceLocationsInput(board, team, configuration, rows);
	}
}

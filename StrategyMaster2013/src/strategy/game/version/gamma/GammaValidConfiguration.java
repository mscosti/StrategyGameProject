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
public class GammaValidConfiguration implements StrategyValidConfiguration{
	
	Collection <PieceLocationDescriptor> blueConfiguration;
	Collection <PieceLocationDescriptor> redConfiguration;
	Board board;

	
	/**
	 * @param blueConfiguration
	 * @param redConfiguration
	 * @param board
	 */
	public GammaValidConfiguration(Collection <PieceLocationDescriptor> blueConfiguration, 
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
		int colonelCounter = 0;
		int captainCounter = 0;
		int lieutenantCounter = 0;
		int sergeantCounter = 0;
		
		for (PieceLocationDescriptor piece: configuration) 
		{
			if (piece.getPiece().getType() == PieceType.FLAG) flagCounter++;
			else if (piece.getPiece().getType() == PieceType.MARSHAL) marshalCounter++;
			else if (piece.getPiece().getType() == PieceType.COLONEL) colonelCounter++;
			else if (piece.getPiece().getType() == PieceType.CAPTAIN) captainCounter++;
			else if (piece.getPiece().getType() == PieceType.LIEUTENANT) lieutenantCounter++;
			else if (piece.getPiece().getType() == PieceType.SERGEANT) sergeantCounter++;
		}
		
		//Will return true if there are the appropriate number of each piece
		return (flagCounter == 1 && marshalCounter == 1 &&
				colonelCounter == 2 && captainCounter == 2 &&
				lieutenantCounter == 3 && sergeantCounter == 3);
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

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

package strategy.game.version.common.validation;

import strategy.common.PlayerColor;

/**
 * @author Matt and Josh
 * @version 1.0
 */
public interface StrategyValidConfiguration {

	/**
	 * determines if a player's input piece configuration is correct
	 * @param team
	 * @return a boolean for marking piece input as correct
	 */
	boolean isCorrectPieceInput(PlayerColor team);
	
	/**
	 * determines if a player's input location configuration is correct
	 * @param team
	 * @param rows
	 * @return 0 if valid configuration, 
	 * 			-1 if pieces are stacked, 
	 * 			or -2 if piece is out of bounds
	 */
	int isCorrectLocationInput(PlayerColor team, int rows);
}

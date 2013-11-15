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
import strategy.game.common.PieceType;

/**
 * @author Matt and Josh
 * @version 1.0
 * 
 * A strategy pattern for Battle Rules. Used for implementing
 * different types of battle rules in different versions of the game
 */
public interface StrategyBattleRules {
	
	/**
	 * @param type
	 * @return an integer represent the powerlevel of the piece type
	 * 
	 * gets the powerLevel of a piece Type
	 */
	int getPowerLevel(PieceType type);
	
	/**
	 * @param striker
	 * @param defender
	 * @return the result of the battle
	 * 
	 * determines the winner of a battle of 2 pieces
	 */
	BattleResult determineBattleWinner(
			PieceLocationDescriptor striker, PieceLocationDescriptor defender);
}

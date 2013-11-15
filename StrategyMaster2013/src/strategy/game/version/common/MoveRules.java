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

import strategy.game.common.Location2D;
import strategy.game.common.PieceType;

/**
 * @author Josh & Matt
 * @version $Revision: 1.0 $
 */
public interface MoveRules 
{
	
	/**
	 * Method validPieceMove.
	 * @param piece PieceType
	 * @param from Location2D
	 * @param to Location2D
	
	 * @return boolean */
	boolean validPieceMove(PieceType piece, Location2D from, Location2D to);
	
}

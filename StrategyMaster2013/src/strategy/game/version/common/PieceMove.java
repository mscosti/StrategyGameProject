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

import strategy.game.common.Location;
import strategy.game.common.PieceType;

/**
 *
 * @author Matt and Josh
 * @version 1.0
 * 
 *  A class representing one move on the board.
 * 	used for the logging functionality.
 */
public class PieceMove {
	
	private final PieceType type;
	private final Location from;
	private final Location to;

	/**
	 * creates a PieceMove
	 * @param type
	 * @param from
	 * @param to
	 */
	public PieceMove(PieceType type, Location from, Location to)
	{
		this.type = type;
		this.from = from;
		this.to = to;
	}

	/**
	 * @return the pieceType
	 */
	public PieceType getType() {
		return type;
	}

	/**
	 * @return the from location
	 */
	public Location getFrom() {
		return from;
	}

	/**
	 * @return the to location
	 */
	public Location getTo() {
		return to;
	}

}

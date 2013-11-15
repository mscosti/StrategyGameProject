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
import static strategy.game.common.Coordinate.*;

/**
 * A class for holding the size of the board
 * @author Matt and Josh
 * @version 1.0
 */
public class Board 
{
	
	private final int width;
	private final int height;
	
	/**
	 * @param width
	 * @param height
	 */
	public Board(int width, int height) 
	{
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @return int width of board
	 */
	public int getWidth() 
	{
		return width;
	}
	
	/**
	 * @return int height of board
	 */
	public int getHeight() 
	{
		return height;
	}
	
	/**
	 * @param location
	 * @return boolean if location is in bounds
	 */
	public boolean inBounds(Location location)
	{
		return (location.getCoordinate(X_COORDINATE) >= 0 &&
				location.getCoordinate(X_COORDINATE) < height &&
				location.getCoordinate(Y_COORDINATE) >= 0 &&
				location.getCoordinate(Y_COORDINATE) < width);
	}

}

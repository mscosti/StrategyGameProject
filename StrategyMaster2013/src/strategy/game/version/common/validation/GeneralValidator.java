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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import strategy.common.PlayerColor;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.version.common.Board;

/**
 * @author Matt and Josh
 * @version 1.0
 */
public class GeneralValidator {

	/**
	 * A static method that is variablized to check if a players pieces
	 * are in valid starting configurations on the board, in any version of
	 * the game. 
	 * @param board 
	 * @param team
	 * @param configuration
	 * @param rows
	 * @return 0 if valid configuration, 
	 * 			-1 if pieces are stacked, 
	 * 			or -2 if piece is out of bounds
	 */
	public static int correctPieceLocationsInput(Board board, PlayerColor team, 
			Collection<PieceLocationDescriptor> configuration, int rows)
	{
		final List<Location> locationList = new ArrayList<Location>();
		int upperBound, lowerBound;
		if (team == PlayerColor.RED) 
		{
			//set the upper and lower bounds for the red starting area
			upperBound = board.getHeight();
			lowerBound = board.getHeight() - rows;
		}
		else 
		{
			//set the upper and lower bounds for the blue starting area
			upperBound = rows;
			lowerBound = 0;
		}
		for(PieceLocationDescriptor piece: configuration)
		{
			if (isWithinBounds(piece, upperBound, lowerBound, board))
				{
					if (!locationList.contains(piece.getLocation()))
					{
						locationList.add(piece.getLocation());
					}
					else return -1; //Stacked piece error
				}
			else return -2; //Piece out of bounds error
		}
		return 0; //Valid configuration
	}
	
	/**
	 * @param piece
	 * @param upperBound
	 * @param lowerBound
	 * @param board
	 * @return boolean whether piece is within the bounds of the starting area
	 */
	private static boolean isWithinBounds(PieceLocationDescriptor piece, 
									int upperBound, int lowerBound, Board board)
	{
		return (piece.getLocation().getCoordinate(Coordinate.X_COORDINATE) < board.getWidth() &&
				piece.getLocation().getCoordinate(Coordinate.X_COORDINATE) >= 0 &&
				piece.getLocation().getCoordinate(Coordinate.Y_COORDINATE) < upperBound &&
				piece.getLocation().getCoordinate(Coordinate.Y_COORDINATE) >= lowerBound);
	}
}


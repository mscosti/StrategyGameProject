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

import java.util.List;

import strategy.game.common.Location;
import strategy.game.common.PieceLocationDescriptor;

/**
 * @author Matt and Josh
 * @version 1.0
 * 
 * PieceHandler is a class that holds all of the piece information
 * of the game. It keeps track of all the red and blue pieces,
 * and where the chokepoints are located. It contains functions or
 * updating the state of these pieces
 */
public class PieceHandler {
	
	private final List<PieceLocationDescriptor> bluePieces;
	private final List<PieceLocationDescriptor> redPieces;
	private final List<PieceLocationDescriptor> chokePoints;
	
	/**
	 * @param redConfigurationList
	 * @param blueConfigurationList
	 * @param chokePointConfigurationList
	 */
	public PieceHandler(List<PieceLocationDescriptor> redConfigurationList, 
			List<PieceLocationDescriptor> blueConfigurationList,
			List<PieceLocationDescriptor> chokePointConfigurationList)
	{
		bluePieces = blueConfigurationList;
		redPieces = redConfigurationList;
		chokePoints = chokePointConfigurationList;
	}
	
	/**
	 * @param oldPiece
	 * @param newPiece
	 * 
	 * updates the location of a piece
	 */
	public void updatePiece(PieceLocationDescriptor oldPiece, PieceLocationDescriptor newPiece) 
	{
		//loops through red and blue lists of pieces until it finds the oldpiece.
		//then, replace it in the list with the new piece
		for (int i = 0; i < redPieces.size(); i++) 
		{
			if (oldPiece.equals(redPieces.get(i)))
			{
				redPieces.set(i, newPiece);
			}
		}
		for (int i = 0; i < bluePieces.size(); i++) 
		{
			if (oldPiece.equals(bluePieces.get(i)))
			{
				bluePieces.set(i, newPiece);
			}
		}
	}
	
	/**
	 * @param deadPiece
	 * 
	 * Removes a dead piece from the game
	 */
	public void removePiece(PieceLocationDescriptor deadPiece) 
	{
		//loops through red and blue lists of pieces until it finds the deadpiece.
		//then, remove it in the list
		for (int i = 0; i < redPieces.size(); i++) 
		{
			if (deadPiece.equals(redPieces.get(i)))
			{
				redPieces.remove(deadPiece);
			}
		}
		for (int i = 0; i < bluePieces.size(); i++) 
		{
			if (deadPiece.equals(bluePieces.get(i)))
			{
				bluePieces.remove(deadPiece);
			}
		}
	}
	
	
	/**
	 * Method getPieceDescriptorAt.
	 * @param location Location
	 * @return a PieceLocationDescriptor. Returns null if no piece is present
	 * This method is given a location and returns the
	 * PieceLocationDescriptor of the piece at that location
	 * */
	public PieceLocationDescriptor getPieceDescriptorAt(Location location) 
	{
		for (PieceLocationDescriptor piece: bluePieces)
		{
			if (piece.getLocation().equals(location))
			{
				return piece;
			}
		}
		for (PieceLocationDescriptor piece: redPieces)
		{
			if (piece.getLocation().equals(location))
			{
				return piece;
			}
		}
		for (PieceLocationDescriptor piece: chokePoints)
		{
			if (piece.getLocation().equals(location))
			{
				return piece;
			}
		}
		return null;
	}
	
	/**
	 * @return A list of blue Pieces
	 */
	public List<PieceLocationDescriptor> getBluePieces()
	{
		return bluePieces;
	}
	
	/**
	 * @return a list of red pieces
	 */
	public List<PieceLocationDescriptor> getRedPieces()
	{
		return redPieces;
	}
}

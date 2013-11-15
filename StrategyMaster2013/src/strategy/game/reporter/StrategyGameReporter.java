/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.reporter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static strategy.common.PlayerColor.*;
import strategy.common.StrategyException;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObserver;

/**
 * 
 * @author Matt Josh
 * @version 1.0
 */
public class StrategyGameReporter implements StrategyGameObserver {

	List<PieceLocationDescriptor> redPieces;
	List<PieceLocationDescriptor> bluePieces;
	Boolean redsTurn;

	@Override
	public void gameStart(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) {

		redPieces = new ArrayList<PieceLocationDescriptor>(redConfiguration);
		bluePieces = new ArrayList<PieceLocationDescriptor>(blueConfiguration);
		redsTurn = false;
		System.out.println("Game Started");

	}

	@Override
	public void moveHappened(PieceType piece, Location from, Location to,
			MoveResult result, StrategyException fault) {

		redsTurn = !redsTurn;
		if (fault != null) {
			System.out.println(fault.getMessage());
		}
		else {
			switch (result.getStatus())
			{
			case RED_WINS:
				System.out.println("Red wins!");
				break;
			case BLUE_WINS:
				System.out.println("Blue wins!");
				break;
			case DRAW:
				System.out.println("Its a draw!");
				break;
			case FLAG_CAPTURED:
				if (redsTurn) {
					System.out.println("Red captured the first of blue's flags at " + to.toString());
				}
				else {
					System.out.println("Blue captured the first of red's flags at " + to.toString());
				}
				break;
			case OK:
				if (result.getBattleWinner() == null) {
					System.out.println("Both " + piece + " pieces died in combat at " + to.toString());
				}
				else if (redsTurn) {
					if (result.getBattleWinner().getPiece().getOwner() != RED) {
						System.out.println("Red Lost piece " + piece + " in battle to at " + to.toString());
					}
					else {
						System.out.println("Red moved successfully to " + to.toString());
					}
				}
				else { //Blue's turn
					if (result.getBattleWinner().getPiece().getOwner() != BLUE) {
						System.out.println("Blue Lost piece " + piece + " in battle to at " + to.toString());
					}
					else {
						System.out.println("Blue moved successfully to " + to.toString());
					}
				}
			}
		}
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
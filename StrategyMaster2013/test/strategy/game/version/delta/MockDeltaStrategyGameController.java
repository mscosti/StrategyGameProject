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

package strategy.game.version.delta;

import java.util.List;

import strategy.common.StrategyException;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.common.PieceHandler;

/**
 * @author Josh & Matt
 * @version 1.0
 */
public class MockDeltaStrategyGameController extends DeltaStrategyGameController{

	final private List<PieceLocationDescriptor> chokePointConfigurationList;
	
	/**
	 * Constructor for MockDeltaStrategyGameController.
	 * @param redConfigurationList List<PieceLocationDescriptor>
	 * @param blueConfigurationList List<PieceLocationDescriptor>
	 * @param chokePointConfigurationList List<PieceLocationDescriptor>
	 * @throws StrategyException
	 */
	public MockDeltaStrategyGameController(List<PieceLocationDescriptor> redConfigurationList, 
			List<PieceLocationDescriptor> blueConfigurationList,
			List<PieceLocationDescriptor> chokePointConfigurationList) throws StrategyException
	{
		super(redConfigurationList, blueConfigurationList, chokePointConfigurationList);
		this.chokePointConfigurationList = chokePointConfigurationList;
	}
	

	@Override
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException 
	{
		return super.move(piece, from, to);
	}

	@Override
	public Piece getPieceAt(Location location) 
	{
		return super.getPieceAt(location);
	}
	
	/**
	 * Method setConfigurations.
	 * @param redConfigurationList List<PieceLocationDescriptor>
	 * @param blueConfigurationList List<PieceLocationDescriptor>
	 */
	public void setConfigurations(List<PieceLocationDescriptor> redConfigurationList,
								List<PieceLocationDescriptor> blueConfigurationList)
	{
		super.pieceHandler = new PieceHandler(redConfigurationList, blueConfigurationList, chokePointConfigurationList);
	}

}

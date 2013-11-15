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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import strategy.common.StrategyException;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

/**
 * @author Josh & Matt
 * @version 1.0
 */
public class MockDeltaGameFactory {

private static final MockDeltaGameFactory instance = new MockDeltaGameFactory();
	
	/**
	 * Default private constructor to ensure this is a singleton.
	 */
	private MockDeltaGameFactory()
	{
		// Intentionally left empty.
	}

	/**
	 * @return the instance
	 */
	public static MockDeltaGameFactory getInstance()
	{
		return instance;
	}
	
	/**
	 * Method makeMockDeltaStrategyGame.
	 * @param redConfiguration Collection<PieceLocationDescriptor>
	 * @param blueConfiguration Collection<PieceLocationDescriptor>
	 * @return MockDeltaStrategyGameController
	 * @throws StrategyException
	 */
	public MockDeltaStrategyGameController makeMockDeltaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
		throws StrategyException
	{
		if (redConfiguration == null || blueConfiguration == null)
		{
			throw new StrategyException("starting configurations cannot be null");
		}
		final List<PieceLocationDescriptor> redConfigurationList = 
				new ArrayList<PieceLocationDescriptor>(redConfiguration);
		final List<PieceLocationDescriptor> blueConfigurationList = 
				new ArrayList<PieceLocationDescriptor>(blueConfiguration);
		final List<PieceLocationDescriptor> chokePointConfigurationList;
		
		chokePointConfigurationList = initializeDeltaChokePoints();
		
		final MockDeltaStrategyGameController deltaController = 
				new MockDeltaStrategyGameController(redConfigurationList, blueConfigurationList,chokePointConfigurationList);
		return deltaController;
	}
	
	/**
	 * Method initializeGammaChokePoints.
	
	 * @return List<PieceLocationDescriptor> */
	public List<PieceLocationDescriptor> initializeDeltaChokePoints() 
	{
		
		final List<PieceLocationDescriptor> chokePoints = new ArrayList<PieceLocationDescriptor>();
		final Piece chokePiece = new Piece(PieceType.CHOKE_POINT, null);
		
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(3,4)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(2,4)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(2,5)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(3,5)));
		
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(6,4)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(6,5)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(7,4)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(7,5)));
		
		return chokePoints;
		
	}
}

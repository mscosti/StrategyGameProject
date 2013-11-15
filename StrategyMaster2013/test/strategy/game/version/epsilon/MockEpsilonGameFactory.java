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

package strategy.game.version.epsilon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import strategy.common.StrategyException;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObserver;

/**
 * @author Josh & Matt
 * @version 1.0
 */
public class MockEpsilonGameFactory {

private static final MockEpsilonGameFactory instance = new MockEpsilonGameFactory();
	
	/**
	 * Default private constructor to ensure this is a singleton.
	 */
	private MockEpsilonGameFactory()
	{
		// Intentionally left empty.
	}

	/**
	 * @return the instance
	 */
	public static MockEpsilonGameFactory getInstance()
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
	public MockEpsilonStrategyGameController makeMockEpsilonStrategyGame(
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
		
		chokePointConfigurationList = initializeEpsilonChokePoints();
		
		final MockEpsilonStrategyGameController epsilonController = 
				new MockEpsilonStrategyGameController(redConfigurationList, blueConfigurationList,chokePointConfigurationList);
		return epsilonController;
	}
	
	public MockEpsilonStrategyGameController makeMockEpsilonStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration,
			Collection<StrategyGameObserver> observers)
		throws StrategyException
	{
		final MockEpsilonStrategyGameController epsilonController = 
				makeMockEpsilonStrategyGame(redConfiguration,blueConfiguration);
		
		if (observers != null)
		{	
			final List<StrategyGameObserver> observerList =
				new ArrayList<StrategyGameObserver>(observers);
			
			epsilonController.setObservers(observerList);
			
			for (StrategyGameObserver observer : observers)
			{
				epsilonController.register(observer);
			}	
		}
		return epsilonController;
	}
	
	/**
	 * Method initializeGammaChokePoints.
	
	 * @return List<PieceLocationDescriptor> */
	public List<PieceLocationDescriptor> initializeEpsilonChokePoints() 
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

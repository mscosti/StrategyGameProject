/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Created by Joshua Morse and Matt Costi
 *******************************************************************************/

package strategy.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import strategy.common.*;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObserver;
import strategy.game.version.alpha.AlphaStrategyGameController;
import strategy.game.version.beta.BetaStrategyGameController;
import strategy.game.version.delta.DeltaStrategyGameController;
import strategy.game.version.epsilon.EpsilonStrategyGameController;
import strategy.game.version.gamma.GammaStrategyGameController;

/**
 * <p>
 * Factory to produce various versions of the Strategy game. This is implemented
 * as a singleton.
 * </p><p>
 * NOTE: If an error occurs creating any game, that is not specified in the particular
 * factory method's documentation, the factory method should throw a 
 * StrategyRuntimeException.
 * </p>
 * 
 * @author gpollice
 * @version Sep 10, 2013
 */
public class StrategyGameFactory
{
	private static final StrategyGameFactory instance = new StrategyGameFactory();
	
	/**
	 * Default private constructor to ensure this is a singleton.
	 */
	private StrategyGameFactory()
	{
		// Intentionally left empty.
	}

	/**
	 * @return the instance
	 */
	public static StrategyGameFactory getInstance()
	{
		return instance;
	}
	
	/**
	 * Create an Alpha Strategy game.
	
	 * @return the created Alpha Strategy game */
	public StrategyGameController makeAlphaStrategyGame()
	{
		return new AlphaStrategyGameController();
	}
	
	/**
	 * Create a new Beta Strategy game given the 
	 * @param redConfiguration the initial starting configuration for the RED pieces
	 * @param blueConfiguration the initial starting configuration for the BLUE pieces
	 * @return the Beta Strategy game instance with the initial configuration of pieces 
	 * @throws StrategyException if either configuration is incorrect
	 */
	public StrategyGameController makeBetaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
		throws StrategyException
	{
		if (redConfiguration == null || blueConfiguration == null)
		{
			throw new StrategyException("starting configurations cannot be null");
		}
		if (!isCorrectPieceInput(redConfiguration) || !isCorrectPieceInput(blueConfiguration)) 
		{
			throw new StrategyException("Incorrect pieces");
		}
		final int locationCheck = correctPieceLocationsInput(redConfiguration, blueConfiguration);
		if (locationCheck == -1) throw new StrategyException("Pieces are stacked");
		if (locationCheck == -2) throw new StrategyException("Pieces are out of bounds");
		
		final List<PieceLocationDescriptor> redConfigurationList = 
				new ArrayList<PieceLocationDescriptor>(redConfiguration);
		final List<PieceLocationDescriptor> blueConfigurationList = 
				new ArrayList<PieceLocationDescriptor>(blueConfiguration);
		
		return new BetaStrategyGameController(redConfigurationList, blueConfigurationList);
	}
	
	/**
	 * @param redConfiguration
	 * @param blueConfiguration
	 * @return StrategyGameController 
	 * @throws StrategyException
	 */
	public StrategyGameController makeGammaStrategyGame(
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
		
		chokePointConfigurationList = initializeGammaChokePoints();
		
		final StrategyGameController gammaController = 
				new GammaStrategyGameController(redConfigurationList, blueConfigurationList, chokePointConfigurationList);
		return gammaController;
	}
	
	/**
	 * Method makeDeltaStrategyGame.
	 * @param redConfiguration Collection<PieceLocationDescriptor>
	 * @param blueConfiguration Collection<PieceLocationDescriptor>
	 * @return StrategyGameController 
	 * @throws StrategyException */
	public StrategyGameController makeDeltaStrategyGame(
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
		
		final StrategyGameController deltaController = 
				new DeltaStrategyGameController(redConfigurationList, blueConfigurationList, chokePointConfigurationList);
		return deltaController;
	}
	
	/**
	 * Method makeEpsilonStrategyGame.
	 * @param redConfiguration Collection<PieceLocationDescriptor>
	 * @param blueConfiguration Collection<PieceLocationDescriptor>
	 * @return StrategyGameController 
	 * @throws StrategyException */
	public StrategyGameController makeEpsilonStrategyGame(
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
		
		final EpsilonStrategyGameController epsilonController = 
				new EpsilonStrategyGameController(redConfigurationList, blueConfigurationList, chokePointConfigurationList);
		
		return epsilonController;
	}
	
	/**
	 * @param redConfiguration
	 * @param blueConfiguration
	 * @param observers
	 * @return StrategyGameController the game that was just made in the factory
	 * @throws StrategyException
	 */
	public StrategyGameController makeEpsilonStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration,
			Collection<StrategyGameObserver> observers)
		throws StrategyException
	{
		final EpsilonStrategyGameController epsilonController = 
				(EpsilonStrategyGameController) makeEpsilonStrategyGame(redConfiguration,blueConfiguration);
		
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
	 * Method isCorrectPieceInput.
	 * @param Configuration Collection<PieceLocationDescriptor>
	
	 * @return boolean */
	public boolean isCorrectPieceInput(Collection <PieceLocationDescriptor> Configuration)
	{
		
		int flagCounter = 0;
		int marshalCounter = 0;
		int colonelCounter = 0;
		int captainCounter = 0;
		int lieutenantCounter = 0;
		int sergeantCounter = 0;
		
		for (PieceLocationDescriptor piece: Configuration) 
		{
			if (piece.getPiece().getType() == PieceType.FLAG) flagCounter++;
			else if (piece.getPiece().getType() == PieceType.MARSHAL) marshalCounter++;
			else if (piece.getPiece().getType() == PieceType.COLONEL) colonelCounter++;
			else if (piece.getPiece().getType() == PieceType.CAPTAIN) captainCounter++;
			else if (piece.getPiece().getType() == PieceType.LIEUTENANT) lieutenantCounter++;
			else if (piece.getPiece().getType() == PieceType.SERGEANT) sergeantCounter++;
		}
		
		//Will return true if there are the appropriate number of each piece
		return (flagCounter == 1 && marshalCounter == 1 &&
				colonelCounter == 2 && captainCounter == 2 &&
				lieutenantCounter == 3 && sergeantCounter == 3);
		
	}
	
	//helper function that checks piece locations on the board
	//returns 0 if pieces are in a correct configuration
	//returns -1 if pieces are stacked
	//returns -2 if pieces are out of bounds
	/**
	 * Method correctPieceLocationsInput.
	 * @param redLocations Collection<PieceLocationDescriptor>
	 * @param blueLocations Collection<PieceLocationDescriptor>
	 * @return int */
	public int correctPieceLocationsInput(Collection <PieceLocationDescriptor> redLocations, 
			Collection<PieceLocationDescriptor> blueLocations)
	{
		final Collection<Location> redLocationList = new ArrayList<Location>();
		final Collection<Location> blueLocationList = new ArrayList<Location>();
		for(PieceLocationDescriptor piece: redLocations)
		{
			if (piece.getLocation().getCoordinate(Coordinate.X_COORDINATE) < 6 &&
				piece.getLocation().getCoordinate(Coordinate.X_COORDINATE) >= 0 &&
				piece.getLocation().getCoordinate(Coordinate.Y_COORDINATE) < 2 &&
				piece.getLocation().getCoordinate(Coordinate.Y_COORDINATE) >= 0)
				{
					if (!redLocationList.contains(piece.getLocation()))
					{
						redLocationList.add(piece.getLocation());
					}
					else return -1;
				}
			else return -2;
		}
		
		for(PieceLocationDescriptor piece: blueLocations)
		{
			if (piece.getLocation().getCoordinate(Coordinate.X_COORDINATE) < 6 &&
				piece.getLocation().getCoordinate(Coordinate.X_COORDINATE) >= 0 &&
				piece.getLocation().getCoordinate(Coordinate.Y_COORDINATE) < 6 &&
				piece.getLocation().getCoordinate(Coordinate.Y_COORDINATE) >= 4)
				{
					if (!blueLocationList.contains(piece.getLocation())) 
					{
						blueLocationList.add(piece.getLocation());
					}
					else return -1;
				}
			else return -2;
		}
		return 0;
	}
	
	/**
	 * Method initializeGammaChokePoints.
	
	 * @return List<PieceLocationDescriptor> */
	public List<PieceLocationDescriptor> initializeGammaChokePoints() 
	{
		
		final List<PieceLocationDescriptor> chokePoints = new ArrayList<PieceLocationDescriptor>();
		final Piece chokePiece = new Piece(PieceType.CHOKE_POINT, null);
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(3, 3)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(2, 3)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(2, 2)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(3, 2)));
		
		return chokePoints;
		
	}
	
	/**
	 * Method initializeDeltaChokePoints.
	
	 * @return List<PieceLocationDescriptor> */
	public List<PieceLocationDescriptor> initializeDeltaChokePoints() 
	{
		
		final List<PieceLocationDescriptor> chokePoints = new ArrayList<PieceLocationDescriptor>();
		final Piece chokePiece = new Piece(PieceType.CHOKE_POINT, null);
		
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(3, 4)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(2, 4)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(2, 5)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(3, 5)));
		
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(6, 4)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(6, 5)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(7, 4)));
		chokePoints.add(new PieceLocationDescriptor(chokePiece, new Location2D(7, 5)));
		
		return chokePoints;
		
	}
	
}
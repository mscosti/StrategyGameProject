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

package strategy.game.version.epsilon;

import java.util.HashMap;
import java.util.Map;

import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.common.BattleResult;
import strategy.game.version.common.StrategyBattleRules;

/**
 * @author Josh, Matt
 * @version $Revision: 1.0 $
 */
public class EpsilonBattleRules implements StrategyBattleRules
{
	
	private static final Map<PieceType, Integer> powerLevels;
    static
    {
        powerLevels = new HashMap<PieceType, Integer>();
        powerLevels.put(PieceType.MARSHAL, 12);
        powerLevels.put(PieceType.GENERAL, 11);
        powerLevels.put(PieceType.COLONEL, 10);
        powerLevels.put(PieceType.MAJOR, 9);
        powerLevels.put(PieceType.CAPTAIN, 8);
        powerLevels.put(PieceType.LIEUTENANT, 7);
        powerLevels.put(PieceType.FIRST_LIEUTENANT, 7);
        powerLevels.put(PieceType.SERGEANT, 6);
        powerLevels.put(PieceType.MINER, 5);
        powerLevels.put(PieceType.SCOUT, 4);
        powerLevels.put(PieceType.SPY, 3);
        powerLevels.put(PieceType.BOMB, 2);
        powerLevels.put(PieceType.FLAG, 1);
    }
    
 
	/* (non-Javadoc)
	 * @see strategy.game.version.common.StrategyBattleRules#determineBattleWinner(strategy.game.common.PieceLocationDescriptor, strategy.game.common.PieceLocationDescriptor)
	 */
	public BattleResult determineBattleWinner(
			PieceLocationDescriptor striker, PieceLocationDescriptor opponent) 
	{
		final int strikerPower = getPowerLevel(striker.getPiece().getType());
		final int opponentPower = getPowerLevel(opponent.getPiece().getType());
		
		BattleResult result = specialBattle(striker, opponent);
		if (result == null)
		{
			if (strikerPower - opponentPower > 0) //striker wins battle
			{
				result = makeWinner(striker, opponent);
			}
			else if(strikerPower - opponentPower < 0) //opponent wins battle
			{
				result = makeWinner(opponent, striker);
			}
			else //if strikerPower-opponentPower is 0 ; nobody wins
			{
				result = new BattleResult(null, null, null);
			}
		}
		return result;
	}
	
	private BattleResult specialBattle
			(PieceLocationDescriptor striker, PieceLocationDescriptor opponent)
	{
		final PieceType strikerPiece = striker.getPiece().getType();
		final PieceType opponentPiece = opponent.getPiece().getType();
		final int strikerPower = getPowerLevel(striker.getPiece().getType());
		final int opponentPower = getPowerLevel(opponent.getPiece().getType());
		
		//if Spy attacks marshal or miner attacks the bomb, striker wins
		if ((strikerPiece == PieceType.SPY && opponentPiece == PieceType.MARSHAL) ||
			(strikerPiece == PieceType.MINER && opponentPiece == PieceType.BOMB))
		{
			return makeWinner(striker, opponent);
		}
		
		//if any piece but the miner attacks the bomb, bomb wins but stays in
		//its current position
		if (strikerPiece != PieceType.MINER && opponentPiece == PieceType.BOMB)
		{
			return new BattleResult(opponent, opponent, striker);
		}
		
		if (strikerPiece == PieceType.FIRST_LIEUTENANT && 
			striker.getLocation().distanceTo(opponent.getLocation()) == 2 &&
			strikerPower - opponentPower < 0 //First LT loses at distance battle
			)
		{
			return new BattleResult(opponent, opponent, striker);
		}
		else if(strikerPiece == PieceType.FIRST_LIEUTENANT && 
				striker.getLocation().distanceTo(opponent.getLocation()) == 2 &&
				strikerPower - opponentPower > 0) //First LT wins at distance battle)
		{
			return makeWinner(striker, opponent);
		}
		
		return null;
	}
	
	
	private BattleResult makeWinner
		(PieceLocationDescriptor winner, PieceLocationDescriptor loser)
	{
		final PieceLocationDescriptor winnerTakeLoserLocation =
				new PieceLocationDescriptor(winner.getPiece(), loser.getLocation());
		
		return new BattleResult(winnerTakeLoserLocation, winner, loser);
	}
	/**
     * Method getPowerLevel.
     * @param type PieceType
    
     * @return int */
    @Override
    public int getPowerLevel(PieceType type)
    {
    	return powerLevels.get(type);
    }

}

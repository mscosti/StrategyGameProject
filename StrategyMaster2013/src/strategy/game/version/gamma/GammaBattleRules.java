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

package strategy.game.version.gamma;

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
public class GammaBattleRules implements StrategyBattleRules
{
	
	private static final Map<PieceType, Integer> powerLevels;
    static
    {
        powerLevels = new HashMap<PieceType, Integer>();
        powerLevels.put(PieceType.MARSHAL, 12);
        powerLevels.put(PieceType.COLONEL, 11);
        powerLevels.put(PieceType.CAPTAIN, 10); 
        powerLevels.put(PieceType.LIEUTENANT, 9);
        powerLevels.put(PieceType.SERGEANT, 8);
        powerLevels.put(PieceType.FLAG, 0);
    }
    
 
	/* (non-Javadoc)
	 * @see strategy.game.version.common.StrategyBattleRules#determineBattleWinner(strategy.game.common.PieceLocationDescriptor, strategy.game.common.PieceLocationDescriptor)
	 */
	public BattleResult determineBattleWinner(
			PieceLocationDescriptor striker, PieceLocationDescriptor opponent) 
	{
		final int strikerPower = getPowerLevel(striker.getPiece().getType());
		final int opponentPower = getPowerLevel(opponent.getPiece().getType());

		PieceLocationDescriptor winnerTakeLoserLocation = null, 
						winner = null, 
						loser = null;
		
		if (strikerPower - opponentPower > 0) //striker wins battle
		{
			winnerTakeLoserLocation = 
					new PieceLocationDescriptor(striker.getPiece(), opponent.getLocation());
			winner = striker;
			loser = opponent;
		}
		else if(strikerPower - opponentPower < 0) //opponent wins battle
		{
			winnerTakeLoserLocation= 
					new PieceLocationDescriptor(opponent.getPiece(), striker.getLocation());
			winner = opponent;
			loser = striker;
		}
		else //if strikerPower-opponentPower is 0 ; nobody wins
		{
			winnerTakeLoserLocation = null;
		}
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

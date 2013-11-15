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

package strategy.game.version.beta;

import java.util.HashMap;
import java.util.Map;

import strategy.game.common.PieceType;

/**
 * @author Josh, Matt
 * @version $Revision: 1.0 $
 */
public class BattleRules {
	
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
    /**
     * Method getPowerLevel.
     * @param type PieceType
    
     * @return int */
    public int getPowerLevel(PieceType type)
    {
    	return powerLevels.get(type);
    }

}

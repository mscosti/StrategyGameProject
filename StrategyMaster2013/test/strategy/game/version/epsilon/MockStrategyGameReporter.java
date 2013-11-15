package strategy.game.version.epsilon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

import strategy.common.StrategyException;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.reporter.StrategyGameReporter;

public class MockStrategyGameReporter extends StrategyGameReporter {

	private String printOut;
	
	@Override
	public void gameStart(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		super.gameStart(redConfiguration,blueConfiguration);
		printOut = outContent.toString().replace("\n", "").replace("\r", "");
		System.setOut(null);
		
		
	}

	@Override
	public void moveHappened(PieceType piece, Location from, Location to,
			MoveResult result, StrategyException fault) {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		super.moveHappened(piece, from, to, result, fault);
		printOut = outContent.toString().replace("\n", "").replace("\r", "");
		System.setOut(null);
		
	}
	
	public String getOutContentString() throws IOException
	{
		return printOut;
	}

}

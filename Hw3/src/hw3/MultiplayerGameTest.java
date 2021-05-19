package hw3;

import static org.junit.Assert.*;


import org.junit.Test;

public class MultiplayerGameTest {

	MultiplayerGame g1 = new MultiplayerGame(3);
	MultiplayerGame g2 = new MultiplayerGame(3);
	MultiplayerGame g3 = new MultiplayerGame(3);
	
	@Test
	public void test() {
		g1.addGamePiece(2, "BluePiece", 5);
		g1.addGamePiece(1, "PurplePiece", 2);
		g1.addGamePiece(1, "OrangePiece", 17);
		assertEquals(g1.toString(), "[Player0; Player1; GamePiece: OrangePiece strength: 17; GamePiece: PurplePiece strength: 2; Player2; GamePiece: BluePiece strength: 5]");
		assertEquals(g1.toStringReverse(), "[GamePiece: BluePiece strength: 5; Player2; GamePiece: PurplePiece strength: 2; GamePiece: OrangePiece strength: 17; Player1; Player0]");
		g1.removePlayer(2);
		assertEquals(g1.toStringReverse(), "[GamePiece: PurplePiece strength: 2; GamePiece: OrangePiece strength: 17; Player1; Player0]");
	
		g2.addGamePiece(2, "BluePiece", 5);
		g2.addGamePiece(1, "PurplePiece", 2);
		g2.addGamePiece(1, "OrangePiece", 17);
		g2.removePlayer(1);
		g2.increaseStrength(2,3);
		assertEquals(g2.toString(), "[Player0; Player2; GamePiece: BluePiece strength: 8]");
		assertEquals(g2.toStringReverse(), "[GamePiece: BluePiece strength: 8; Player2; Player0]");
		
		g3.addGamePiece(2, "BluePiece", 5);
		g3.addGamePiece(1, "PurplePiece", 2);
		g3.addGamePiece(1, "OrangePiece", 17);
		assertEquals(g3.toString(), "[Player0; Player1; GamePiece: OrangePiece strength: 17; GamePiece: PurplePiece strength: 2; Player2; GamePiece: BluePiece strength: 5]");
		g3.swapPieces(1, 2);
		assertEquals(g3.toString(), "[Player0; Player1; GamePiece: BluePiece strength: 5; Player2; GamePiece: OrangePiece strength: 17; GamePiece: PurplePiece strength: 2]");
	}
}

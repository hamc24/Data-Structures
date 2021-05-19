package hw3;

public class MultiplayerGame {
	
	//Data fields
	private GameEntity[] index; 
	private GameEntity turnTraker; 
	private int size = 0;
	
	
/*
 * 
 * Section 1: Basic Operations
 * 
 */
	//Constructor
	/**
	 * Initializes the MultiplayerGame by creating an array "index" of n amount of GamePlayers
	 * @param n the amount of players in the game
	 */
	public MultiplayerGame(int n) {
		index = new GameEntity[n];
		if(n < 0) {
			throw new IllegalArgumentException("MultiplayerGame: Invalid amount of players!");
		} 
		if(n == 1) {
			index[0] = new GamePlayer(null, null, 0);
		} else {
			for(int i = 0; i < n; i++) {
				index[i] = new GamePlayer(null, null, i);
			}
			for(int i = 0; i < n; i++) {
				if(i == 0) {
					index[i].prev = index[n-1];
					index[i].next = index[i+1];
				} else if(i == n-1) {
					index[i].prev = index[i-1];
					index[i].next = index[0];
				} else {
					index[i].prev = index[i-1];
					index[i].next = index[i+1];
				}
			}
		}
 	}
	
	//Methods
	/**
	 * Returns the amount of GamePieces in the MultiplayerGame
	 * @return
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Insert a GamePiece based on piece strength for a given player
	 * @param playerId
	 * @param name Name of the GamePiece
	 * @param strength Strength value of the GamePiece
	 */
	public void addGamePiece(int playerId, String name, int strength) {
		//Checks if the player exists
		if(playerId >= index.length || playerId < 0) {
			throw new IllegalArgumentException("addGamePiece: no such player");
		}
		GameEntity current = index[playerId];
		while(!current.next.isGamePlayer()) {
			if(current.next.getName() == name) {
				throw new IllegalArgumentException("addGamePiece: duplicate entity");
			}
			if(((GamePiece) current.next).getStrength() > strength) {
				current = current.next;
			} else {
				current.next = new GamePiece(current, current.next, name, strength);
				size++;
				current.next.next.prev = current.next;
				break;
			}
		}
		if(current.next.isGamePlayer()) {
			current.next  = new GamePiece(current, current.next, name, strength);
			size++;
			current.next.next.prev = current.next;
		}
	}

	/**
	 * Removes a certain GamePiece from a GamePlayer
	 * @param playerId The GamePlayer's Id
	 * @param name The piece to remove
	 */
	public void removeGamePiece(int playerId, String name) {
		if(index.length <= playerId || playerId < 0) {
			throw new IllegalArgumentException("removeGamePiece: no such player");
		}
		if(!hasGamePiece(name)) {
			throw new IllegalArgumentException("removeGamePiece: entity does not exist");
		}
		GameEntity current = index[playerId];
		while(!current.next.isGamePlayer()) {
			if(current.next.getName() == name) {
				current.next = current.next.next;
				current.next.prev = current;
				size--;
				break;
			} else {
				current = current.next;
			}
		}
	}
	
	/**
	 * Checks if a GamePiece of a given name exists in the MultiplayerGame
	 * @param name The piece that is being looked for
	 * @return True if found, else false
	 */
	public boolean hasGamePiece(String name) {
		GameEntity current = index[0];
		while(current.next != index[0]) {
			if(current.next.getName() == name) {
				return true;
			} else {
				current = current.next;
			}
		}
		return false;
	}
	
	/**
	 * Removes all the GamePieces owned by the specified player
	 * @param playerId Id of the Specified player
	 */
	public void removeAllGamePieces(int playerId) {
		if(playerId >= index.length || playerId < 0) {
			throw new IllegalArgumentException("removeAllGamePieces");
		} else {
			GameEntity current = index[playerId];
			while(!current.next.isGamePlayer()) {
				current.next = current.next.next;
				current.next.prev = current;
				size--;
			}
		}
	}
	
	/**
	 * Increases the strength of all the GamePieces for a specified player
	 * @param playerId 
	 * @param n How much the strength should be increased by
	 */
	public void increaseStrength(int playerId, int n) {
		if(playerId >= index.length || playerId < 0) {
			throw new IllegalArgumentException("increaseStrength: no such player");
		}
		GameEntity current = index[playerId];
		while(!current.next.isGamePlayer()) {
			((GamePiece) current.next).updateStrength(n);
			current = current.next;
		}
	}
	
	/**
	 * Removes the player and all its GamePieces from the MultiplayerGame
	 * @param playerId
	 */
	public void removePlayer(int playerId) {
		if(playerId >= index.length || playerId < 0) {
			throw new IllegalArgumentException("removePlayer: no such player");
		}
		removeAllGamePieces(playerId);
		GameEntity current = index[playerId];
		if(playerId == 0) {
			index[playerId] = current.next;
			current.next.prev = current.prev;
			current.prev.next = current.next;
		}if(playerId == index.length-1) {
			index[playerId] = null;
			current.next.prev = current.prev;
			current.prev.next = current.next;
		} else {
			current.prev.next = current.next;
			current.next.prev = current.prev;
			index[playerId] = current.next;
			
		}
	}
	
	/**
	 * Swaps the GamePieces of one player with another
	 * @param playerId1 specified player 1
	 * @param playerId2 specified player 2
	 */
	public void swapPieces(int playerId1, int playerId2) {
		if(playerId1 >= index.length || playerId1 < 0 || playerId2 >= index.length || playerId2 < 0) {
			throw new IllegalArgumentException("swap: no such pieces");
		}
		GameEntity current1 = index[playerId1];
		GameEntity[] holder1 = new GameEntity[index.length];
		GameEntity current2 = index[playerId2];
		GameEntity[] holder2 = new GameEntity[index.length];
		int i = 0;
		while(!current1.next.isGamePlayer()) {
			holder1[i] = current1.next;
			current1 = current1.next;
			i++;
		}
		removeAllGamePieces(playerId1);
		i = 0;
		while(!current2.next.isGamePlayer()) {
			holder2[i] = current2.next;
			current2 = current2.next;
			i++;
		}
		removeAllGamePieces(playerId2);
		i = 0;
		while(holder1[i] != null) {
			addGamePiece(playerId2, holder1[i].getName(), ((GamePiece) holder1[i]).getStrength());
			i++;
		}
		i = 0;
		while(holder2[i] != null) {
			addGamePiece(playerId1, holder2[i].getName(), ((GamePiece) holder2[i]).getStrength());
			i++;
		}
	}
	
	/**
	 * Reverses the list of the MultiplayerGame
	 * @return reversed list
	 */
	public String toStringReverse() {
		StringBuilder sb = new StringBuilder();
		GameEntity current = null;
		GameEntity start = null; //DLL is cyclic -- so let's keep track of where we start
		
		//Find the last un-removed player 
		for (int i = index.length - 1; i >= 0 ; i--) {
			if (index[i] != null) { // or however you check that the player has been removed
				current = index[i];
				start = index[i];
				break;
			}
		}
		
		//Find that player's last piece
		while (!current.next.isGamePlayer()) {
			current = current.next;
			start = start.next;
		}
		
		sb.append("[");
		sb.append(current.toString());
		current = current.prev;
		while (current != start) { //check that we haven't cycled back to the beginning
			sb.append("; ");
			sb.append(current.toString());
			current = current.prev;
		}
		sb.append("]");
		return sb.toString();	
	}
	
	/**
	 * Returns the string list of the MultiplayerGame
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		GameEntity current = index[0];
		sb.append(current);
		while(current.next != index[0]) {
			sb.append("; " + current.next.toString());
			current = current.next;
		}
		sb.append("]");
		return sb.toString();
		}
		
/*
 * 
 * Section 2: Processing a Turn
 * 
 */
	
	public void initializeTurnTracker() {
		turnTraker = index[0];
	}
	
	public void nextPlayer() {
		while(!turnTraker.next.isGamePlayer()) {
			turnTraker = turnTraker.next;
		}
		turnTraker = turnTraker.next;
	}
	
	public void nextEntity() {
		turnTraker = turnTraker.next;
	}
	
	public void prevPlayer() {
		while(!turnTraker.prev.isGamePlayer()) {
			turnTraker = turnTraker.prev;
		}
		turnTraker = turnTraker.prev;
	}
	
	public String currentEntityToString() {
		return turnTraker.toString();
	}
	
	public static void main(String[] args) {
		//EXAMPLE 1 
		MultiplayerGame g1 = new MultiplayerGame(3);
		g1.addGamePiece(2, "BluePiece", 5);
		g1.addGamePiece(1, "PurplePiece", 2);
		g1.addGamePiece(1, "OrangePiece", 17);
		System.out.println(g1.toString());
		
		//Expected output: 
		//[Player0; Player1; GamePiece: OrangePiece strength: 17; GamePiece: PurplePiece strength: 2; Player2; GamePiece: BluePiece strength: 5]
				
		System.out.println(g1.toStringReverse());
		//Expected output://[GamePiece: BluePiece strength: 5; Player2; GamePiece: PurplePiece strength: 2; GamePiece: OrangePiece strength: 17; Player1; Player0]
				
		g1.removePlayer(2);
		System.out.println(g1.toStringReverse());
		//Expected output://[GamePiece: PurplePiece strength: 2; GamePiece: OrangePiece strength:17; Player1; Player0]
				
		//EXAMPLE 2
		MultiplayerGame g2 = new MultiplayerGame(3);
		g2.addGamePiece(2, "BluePiece", 5);
		g2.addGamePiece(1, "PurplePiece", 2);
		g2.addGamePiece(1, "OrangePiece", 17);
		g2.removePlayer(1);
		g2.increaseStrength(2,3);
		System.out.println(g2.toString());
		//Expected output:
		//[Player0; Player2; GamePiece: BluePiece strength: 8]
		g2.addGamePiece(0, "RedPiece", 3);
		System.out.println(g2.toString());
		
		System.out.println(g2.toStringReverse());
		//Expected output:
		//[GamePiece: BluePiece strength: 8; Player2; Player0]
				
		//EXAMPLE 3
		MultiplayerGame g3 = new MultiplayerGame(3);
		g3.addGamePiece(2, "BluePiece", 5);
		g3.addGamePiece(1, "PurplePiece", 2);
		g3.addGamePiece(1, "OrangePiece", 17);
		g3.removeGamePiece(1, "PurplePiece");
		System.out.println(g3.toString());
		//Expected output:
		//[Player0; Player1; GamePiece: OrangePiece strength: 17; Player2; GamePiece: BluePiece strength: 5]
		
		System.out.println(g3.toStringReverse());
		//Expected output
		//[GamePiece: BluePiece strength: 5; Player2; GamePiece: OrangePiece strength: 17; Player1; Player0]
		
		MultiplayerGame g4 = new MultiplayerGame(3);
		g4.addGamePiece(2, "BluePiece", 5);
		g4.addGamePiece(1, "PurplePiece", 2);
		g4.addGamePiece(1, "OrangePiece", 17);
		g4.swapPieces(1, 2);
		System.out.println(g4.toString());
	}
		
}

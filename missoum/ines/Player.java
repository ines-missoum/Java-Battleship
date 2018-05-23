package missoum.ines;

import java.util.ArrayList;

public class Player {

	private String playerName;

	private ArrayList<Ship> ships;/* list of ships that the opponent has to sink */
	private ArrayList<Coordinate> missedShots;
	private ArrayList<Coordinate> hitShots;

	/**************************/
	/****** CONSTRUCTOR ******/
	/*************************/

	public Player(String playerName) {
		this.playerName = playerName;
		this.ships = new ArrayList<Ship>();
		this.missedShots = new ArrayList<Coordinate>();
		this.hitShots = new ArrayList<Coordinate>();

	}

	/*******************************/
	/****** GETTERS & SETTERS ******/
	/******************************/

	public String getPlayerName() {
		return playerName;
	}

	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}

	public void setMissedShots(ArrayList<Coordinate> missedShots) {
		this.missedShots = missedShots;
	}

	public void setHitShots(ArrayList<Coordinate> hitShots) {
		this.hitShots = hitShots;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public ArrayList<Coordinate> getMissedShots() {
		return missedShots;
	}

	public ArrayList<Coordinate> getHitShots() {
		return hitShots;
	}

	/*************************/
	/********* OTHER *********/
	/*************************/

	/***************************************************************/
	/**
	 * Creates the coordinates list of the ship to place and verifies if each of
	 * these coordinates is not already taken for another ship
	 **/
	public boolean canPlaceShip(Coordinate startCoord, Coordinate endCoord) {

		ArrayList<Coordinate> c;
		c = startCoord.listCoordinate(endCoord); /* c: coordinates list of the ship */

		boolean canPlace = true;
		int i = 0;
		int j = 0;

		while (canPlace & j < c.size()) {
			/*
			 * stop when I found a busy coordinate (canPlace==false) or when I verified all
			 * the coordinates => I can place the ship at this place
			 */
			i = 0;
			while (canPlace & i < this.ships.size()) {
				/*
				 * stop when I found a busy coordinate (canPlace==false) or when I verified for
				 * all the placed ships that THE coordinate is not already busy
				 */
				canPlace = !(c.get(j).coordinateIn(this.ships.get(i).getCoordinates()));
				i++;
			}
			j++;
		}
		return canPlace;
	}

	/*****************************************************************/
	/** Creates a ship at the given coordinates **/
	/** and adds it to the player's ships **/
	public void placeShip(Coordinate startCoord, Coordinate endCoord) {

		Ship s = new Ship(startCoord, endCoord);
		this.ships.add(s);

	}

	/*****************************************************************/
	/** We choose to take off the ships of the list when they are sunk **/
	/** return true if the player lost **/
	public boolean lost() {
		return this.ships.size() == 0;
	}

	/*****************************************************************/
	/**
	 * the current player shot at the coordinate "target" to try to hit the ship of
	 * the "otherPlayer"
	 **/
	/** return if the current player hit the ship and if the ship is then sunk **/
	public boolean[] tryToHit(Coordinate target, Player otherPlayer) {

		boolean isHit = false;
		boolean isSunk = false;
		int i = 0;
		Ship ship;

		while (!isHit & i < otherPlayer.ships.size()) {

			ship = otherPlayer.ships.get(i);
			isHit = ship.isHit(target);

			if (isHit) {
				/* if it's hit I check if it's sunk */
				isSunk = ship.isSunk();
				if (!target.coordinateIn(this.hitShots)) {
					this.hitShots.add(target);
				}

				if (isSunk) {
					otherPlayer.ships.remove(ship);

				}

			} else {
				if (!target.coordinateIn(this.missedShots)) {
					this.missedShots.add(target);
				}
			}
			i++;
		}

		boolean[] shotResult = { isHit, isSunk };
		return shotResult;
	}

	/*****************************************************************/
	/** shows the ships grid of the player to help them place their ships **/
	/** contains ship and the shots of the other player **/
	public void seeShipsGrid(Player otherPlayer) {
		boolean thereIsShip = false;
		boolean shotOtherPlayer = false;
		String s;
		Coordinate c;
		// String[][] grid;
		// grid = new String[Game.lastLetter + 1 - 'A'][Game.lastNumber + 1];
		char ch;
		System.out.print("   ");
		for (int i = 'A'; i <= Game.lastLetter; i++) {
			ch = (char) i;
			System.out.print("  " + ch + "  ");
		}

		for (int j = 1; j <= Game.lastNumber; j++) {
			System.out.println();
			if(j>9) {
				System.out.print(j + " ");
			}else {
				System.out.print(" "+ j + " ");
			}
			for (int i = 'A'; i <= Game.lastLetter; i++) {
				thereIsShip = false;
				ch = (char) i;
				s = ch + "" + j;
				c = new Coordinate(s);
				for (int k = 0; k < this.ships.size(); k++) {
					if (c.coordinateIn(this.ships.get(k).getCoordinates())) {
						thereIsShip = true;
						shotOtherPlayer = false;
					}
				}

				shotOtherPlayer = c.coordinateIn(otherPlayer.getMissedShots())
						| c.coordinateIn(otherPlayer.getHitShots());

				if (shotOtherPlayer) {
					System.out.print("  x  ");
				} else if (thereIsShip) {
					System.out.print("  O  ");
				} else {
					System.out.print("  .  ");
				}
			}
		}
		System.out.println();
	}

	/*****************************************************************/
	/** shows the shots grid of the player to help them place shot **/
	public void seeShotsGrid() {
		boolean thereIsShip = false;
		boolean missedShot = false;
		String s;
		Coordinate c;
		// String[][] grid;
		// grid = new String[Game.lastLetter + 1 - 'A'][Game.lastNumber + 1];
		char ch;
		System.out.print("   ");
		for (int i = 'A'; i <= Game.lastLetter; i++) {
			ch = (char) i;
			System.out.print("  " + ch + "  ");
		}

		for (int j = 1; j <= Game.lastNumber; j++) {
			System.out.println();
			
			if(j>9) {
				System.out.print(j + " ");
			}else {
				System.out.print(" "+ j + " ");
			}
			
			for (int i = 'A'; i <= Game.lastLetter; i++) {
				thereIsShip = false;
				missedShot = false;
				ch = (char) i;
				s = ch + "" + j;
				c = new Coordinate(s);

				thereIsShip = c.coordinateIn(this.hitShots);
				missedShot = c.coordinateIn(this.missedShots);

				if (thereIsShip) {
					System.out.print("  O  ");
				} else if (missedShot) {
					System.out.print("  x  ");
				} else {
					System.out.print("  .  ");
				}
			}
		}
		System.out.println();
	}

	/***************************************************************/
	public void reset() {
		this.setShips(new ArrayList<Ship>());
		this.setMissedShots(new ArrayList<Coordinate>());
		this.setHitShots(new ArrayList<Coordinate>());
	}
}

package missoum.ines;

import java.util.Random;

public abstract class AI extends Player {

	public AI(String playerName) {
		super(playerName);

	}

	/***************************************************************/
	public Coordinate giveACoordinate() {

		Random r = new Random();
		int randomInteger = r.nextInt(Game.lastNumber) + 1;
		char randomLetter = (char) ('A' + Math.random() * (Game.lastLetter - 'A' + 1));
		String randomCoord = randomLetter + "" + randomInteger;
		Coordinate coord = new Coordinate(randomCoord);

		return coord;
	}

	/***************************************************************/
	public Coordinate[] givePairCoordinate(int size) {

		String randomEndCoord;
		Random r = new Random();
		Coordinate startCoord = this.giveACoordinate();
		Coordinate endCoord = this.giveACoordinate();

		/* we choose a random orientation */
		int orientation = r.nextInt(2);
		if (orientation == 0) {
			/* if horizontal */

			char sizeC = (char) size;
			int letter = startCoord.getLetter().charAt(0) + sizeC - 1;
			char endLetter = (char) letter;
			randomEndCoord = endLetter + startCoord.getInteger();

		} else {
			/* if vertical */
			int endInteger = Integer.parseInt(startCoord.getInteger()) + size - 1;
			randomEndCoord = startCoord.getLetter() + endInteger;
		}

		endCoord = new Coordinate(randomEndCoord);
		Coordinate[] rightCoord = { startCoord, endCoord };

		return rightCoord;
	}

	/***************************************************************/
	public Coordinate[] giveRightCoordinates(int size) {

		Coordinate[] rightCoord;
		// Coordinate startCoord, endCoord;

		boolean coordOK;
		boolean freeSpace;

		do {
			do {
				rightCoord = givePairCoordinate(size);
				coordOK = rightCoord[0].PairCoordinatesOK(rightCoord[1]);
			} while (!coordOK);

			freeSpace = this.canPlaceShip(rightCoord[0], rightCoord[1]);
		} while (!freeSpace);

		return rightCoord;

	}

	/***************************************************************/
	public void placeAllShips() {

		int size;
		for (int i = Game.sizeOfShips.length - 1; i >= 0; i--) {
			size = Integer.parseInt(Game.sizeOfShips[i]);
			// System.out.println(size);
			Coordinate[] rightCoord = this.giveRightCoordinates(size);
			this.placeShip(rightCoord[0], rightCoord[1]);
			// this.shipsGrid();
			// System.out.println("Ship of size " + size + " placed
			// "+rightCoord[0].toString()+"-"+rightCoord[1].toString());
		}
	}

	public Coordinate giveATarget() {
		return null;
	}

}

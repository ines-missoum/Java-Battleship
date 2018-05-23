package missoum.ines;

import java.util.ArrayList;

public class HardAI extends AI {

	private ArrayList<Coordinate> lastHit;
	// all the hit coordinates of the ship that I'm trying to sank
	private ArrayList<Coordinate> aroundHit;
	// this help to find the orientation of the ship that I want to sank
	// ie: when I hit a coordinate of a ship (for the first time) I check all the
	// coordinates around

	/**************************/
	/****** CONSTRUCTOR ******/
	/*************************/

	public HardAI() {
		super("Level Hard");
		this.lastHit = new ArrayList<Coordinate>();
		this.aroundHit = new ArrayList<Coordinate>();
	}

	/*******************************/
	/****** GETTERS & SETTERS ******/
	/******************************/

	/* print the list of coordinates ==> just a test ==> to supp */
	public void affiche(ArrayList<Coordinate> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).toString() + " ");

		}
		System.out.println(" ");

	}

	public void setLastHit(ArrayList<Coordinate> lastHit) {
		this.lastHit = lastHit;
	}

	public void setAroundHit(ArrayList<Coordinate> aroundHit) {
		this.aroundHit = aroundHit;
	}

	public ArrayList<Coordinate> getLastHit() {
		return lastHit;
	}

	public ArrayList<Coordinate> getAroundHit() {
		return aroundHit;
	}

	/*************************/
	/********* OTHER *********/
	/*************************/

	public Coordinate giveADifferentCoordinate() {
		Coordinate coord;
		do {
			coord = giveACoordinate();

		} while (coord.coordinateIn(this.getHitShots()) | coord.coordinateIn(this.getMissedShots()));

		return coord;
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

		while (!isHit & i < otherPlayer.getShips().size()) {

			ship = otherPlayer.getShips().get(i);
			isHit = ship.isHit(target);

			if (isHit) {

				if (this.lastHit.size() == 0) {
					// si cest la prem coord du bat que je touche
					this.aroundHit = target.giveAroundCoordinates();
				} else {

					this.addNextCoordinate(target);
					this.aroundHit.remove(target);
					// System.out.println("remove " +target.toString());
				}

				this.lastHit.add(target);

				/* if it's hit I check if it's sunk */
				isSunk = ship.isSunk();

				this.getHitShots().add(target);

				if (isSunk) {
					otherPlayer.getShips().remove(ship);
					this.lastHit.clear();
					// System.out.println("_______________CLEAR");
				}

			} else {// not isHit
				this.getMissedShots().add(target);

			}
			i++;
		}

		boolean[] shotResult = { isHit, isSunk };
		return shotResult;
	}

	/***************************************************************/
	public Coordinate giveATarget() {

		Coordinate coord;
		if (this.lastHit.size() == 0) {
			/*
			 * if I have no idea of where a ship is, it gives a random coordinate that had
			 * not already been shot
			 */
			coord = giveADifferentCoordinate();
		} else {
			/* I'm trying to sunk a ship */
			coord = this.aroundHit.get(0);
			this.aroundHit.remove(0);
			// System.out.println("remove " +aroundHit.get(0).toString());
		}

		return coord;
	}

	/***************************************************************/
	/**
	 * Gives the next coordinate of the coordinate in the same orientation that is
	 * not in the list in parameter
	 **/
	public void addNextCoordinate(Coordinate coord) {
		// System.out.println("next de "+coord.toString());
		Coordinate c;
		int n;
		int l;
		char letter;
		String s;
		boolean add = false;

		int coordNumber = Integer.parseInt(coord.getInteger());
		char coordLetter = coord.getLetter().charAt(0);

		if (coord.getLetter().equals(this.lastHit.get(0).getLetter())) {
			// vertical

			/* first vertical coordinate */
			n = coordNumber + 1;
			s = coordLetter + "" + n;
			c = new Coordinate(s);

			if (c.coordinatesOK() & !c.coordinateIn(getAroundHit()) & !c.coordinateIn(getLastHit())) {
				this.aroundHit.add(c);
				add = true;

			}
			if (!add) {
				/* second vertical coordinate */
				n = coordNumber - 1;
				s = coordLetter + "" + n;
				c = new Coordinate(s);

				if (c.coordinatesOK() & !c.coordinateIn(getAroundHit()) & !c.coordinateIn(getLastHit())) {
					this.aroundHit.add(c);
				}
			}
		} else {
			/* first horizontal coordinate */
			l = coordLetter + 1;
			letter = (char) l;
			s = letter + "" + coordNumber;
			c = new Coordinate(s);

			if (c.coordinatesOK() & !c.coordinateIn(getAroundHit()) & !c.coordinateIn(getLastHit())) {
				this.aroundHit.add(c);
				add = true;
			}
			if (!add) {
				/* second horizontal coordinate */
				l = coordLetter - 1;
				letter = (char) l;
				s = letter + "" + coordNumber;
				c = new Coordinate(s);

				if (c.coordinatesOK() & !c.coordinateIn(getAroundHit()) & !c.coordinateIn(getLastHit())) {
					this.aroundHit.add(c);

				}
			}
			// if(add) {
			// System.out.println("add "+c.toString());
			// }else {
			// System.out.println("rien add");
			// }
		}

	}

	/***************************************************************/
	public void reset() {
		super.reset();
		this.setLastHit(new ArrayList<Coordinate>());
		this.setAroundHit(new ArrayList<Coordinate>());
	}

}

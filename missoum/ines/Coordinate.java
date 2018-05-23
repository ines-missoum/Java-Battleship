package missoum.ines;

import java.util.ArrayList;

public class Coordinate {
	public String integer;
	public String letter;

	/**************************/
	/****** CONSTRUCTOR ******/
	/*************************/

	public Coordinate(String s) {
		/* Creates the coordinate from a String */
		s = s.toUpperCase();

		if (s.length() < 2) {
			this.letter = "incorect";
			this.integer = "incorect";

		} else {

			this.letter = s.substring(0, 1);
			this.integer = s.substring(1);
		}

	}

	/*******************************/
	/****** GETTERS & SETTERS ******/
	/******************************/

	public String getInteger() {
		return integer;
	}

	public void setInteger(String integer) {
		this.integer = integer;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	/*************************/
	/********* OTHER *********/
	/*************************/

	/***************************************************************/
	/**
	 * Verifies if the couple of coordinates is correct : we check that each
	 * coordinate is correct and that they are not in diagonal
	 */
	/** startCoord.PairCoordinatesOK(endCoord) */
	public boolean PairCoordinatesOK(Coordinate endCoord) {

		boolean notDiagonal = (this.getLetter().equals(endCoord.getLetter()))
				| (this.getInteger().equals(endCoord.getInteger()));

		return this.coordinatesOK() & endCoord.coordinatesOK() & notDiagonal;

	}

	/***************************************************************/
	/** Verifies if the format of the coordinates is correct */
	public boolean coordinatesOK() {

		return this.isNumberOK() & this.isLetterOK();
	}

	/***************************************************************/
	/**
	 * Verifies if the first character of the coordinate is a letter and that it's
	 * not out of the grid
	 */
	public boolean isLetterOK() {
		char c = this.getLetter().charAt(0);
		return Character.isLetter(c) & c <= Game.lastLetter & c >= 'A';
	}

	/***************************************************************/
	/**
	 * Verifies if the next part of the coordinate is a number and that it's not out
	 * of the grid
	 */
	public boolean isNumberOK() {

		String maxNumber = "" + Game.lastNumber;
		char[] tab, maxTab;
		tab = this.getInteger().toCharArray();
		maxTab = maxNumber.toCharArray();
		boolean isNumberOK = true;
		int i = 0;

		if (tab.length > maxTab.length) {

			isNumberOK = false;
			/* if the number is too long */
		} else {

			while (isNumberOK & i < tab.length) {
				/* verifies that it's a number */
				isNumberOK = Character.isDigit(tab[i]);
				i++;
			}

			if (isNumberOK) {
				/* if it's a number,verifies that's in the grid */
				int integer = Integer.parseInt(this.getInteger());
				isNumberOK = (integer > 0 & integer <= Game.lastNumber);
			}
		}
		return isNumberOK;
	}

	/***************************************************************/
	/** Verifies that the coordinate is in the list given in parameter **/
	public boolean coordinateIn(ArrayList<Coordinate> c) {

		boolean busy = false;
		int i = 0;

		while (!busy & i < c.size()) {
			busy = this.equals(c.get(i));
			i++;
		}
		return busy;
	}

	/***************************************************************/
	public String toString() {
		return this.letter + this.integer;
	}

	/***************************************************************/
	/** return true if the two coordinates are the same **/
	public boolean equals(Coordinate endCoord) {
		return (endCoord.getLetter().equals(this.getLetter())) & (endCoord.getInteger().equals(this.getInteger()));
	}

	/***************************************************************/
	/**
	 * return true if the start coordinate is bigger that the end coordinate (ie : a
	 * bigger letter or a bigger number)
	 **/
	public boolean biggerThan(Coordinate endCoord) {

		if (this.getLetter().equals(endCoord.getLetter())) {
			/* vertical */
			return Integer.parseInt(this.getInteger()) > Integer.parseInt(endCoord.getInteger());

		} else if (this.getInteger().equals(endCoord.getInteger())) {
			/* horizontal */
			return this.getLetter().charAt(0) > endCoord.getLetter().charAt(0);

		} else {
			return false;
		}
	}

	/***************************************************************/
	/**
	 * Creates a list of coordinates from a start coordinate and an end coordinate
	 **/

	public ArrayList<Coordinate> listCoordinate(Coordinate endCoord) {

		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

		coordinates.add(this);

		if (!this.equals(endCoord)) {

			if (this.getLetter().equals(endCoord.getLetter())) {

				/* if the ship is vertical */
				int x = Integer.parseInt(this.getInteger());
				int y = Integer.parseInt(endCoord.getInteger());

				String s;
				String l = this.getLetter();

				for (int i = x + 1; i < y; i++) {
					s = l + i;
					coordinates.add(new Coordinate(s));
				}

			} else if (this.getInteger().equals(endCoord.getInteger())) {
				/* if the ship is horizontal */

				String s;

				String nb = this.getInteger();

				for (int i = this.getLetter().charAt(0) + 1; i < endCoord.getLetter().charAt(0); i++) {
					s = (char) i + nb;
					coordinates.add(new Coordinate(s));
				}
			}
			coordinates.add(endCoord);
		}
		return coordinates;
	}

	/***************************************************************/
	/**
	 * give the number of coordinates from a start coordinate and an end coordinate
	 **/
	public int lengthCoordinate(Coordinate endCoord) {

		if (this.getLetter().equals(endCoord.getLetter())) {
			/* if the ship is vertical */
			return Integer.parseInt(endCoord.getInteger()) - Integer.parseInt(this.getInteger()) + 1;
		} else {
			/* if the ship is horizontal */
			return endCoord.getLetter().charAt(0) - this.getLetter().charAt(0) + 1;
		}
	}

	/***************************************************************/
	/**
	 * Gives a list of 4 coordinates (au max) that are the coordinates around the
	 * coordinate
	 **/
	public ArrayList<Coordinate> giveAroundCoordinates() {

		ArrayList<Coordinate> aroundCoordinates = new ArrayList<Coordinate>();
		Coordinate c;
		int n;
		int l;
		char letter;
		String s;

		int coordNumber = Integer.parseInt(this.getInteger());
		char coordLetter = this.getLetter().charAt(0);

		/* we add the two horizontal coordinates */

		/* first horizontal coordinate */
		l = coordLetter + 1;
		letter = (char) l;
		s = letter + "" + coordNumber;
		c = new Coordinate(s);

		if (c.coordinatesOK()) {
			aroundCoordinates.add(c);
		}

		/* second horizontal coordinate */
		l = coordLetter - 1;
		letter = (char) l;
		s = letter + "" + coordNumber;
		c = new Coordinate(s);

		if (c.coordinatesOK()) {
			aroundCoordinates.add(c);
		}

		/* we add the two vertical coordinates */

		/* first vertical coordinate */
		n = coordNumber + 1;
		s = coordLetter + "" + n;
		c = new Coordinate(s);

		if (c.coordinatesOK()) {
			aroundCoordinates.add(c);
		}
		/* second vertical coordinate */
		n = coordNumber - 1;
		s = coordLetter + "" + n;
		c = new Coordinate(s);

		if (c.coordinatesOK()) {
			aroundCoordinates.add(c);
		}

		return aroundCoordinates;
	}

}

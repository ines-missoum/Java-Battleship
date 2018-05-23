package missoum.ines;

import java.util.ArrayList;

public class Ship {

	private ArrayList<Coordinate> coordinates;
	private int size;
	private int hit; /* number of times that the ship was hit */

	/* print the list of coordinates ==> just a test ==> to supp */
	public void affiche() {
		for (int i = 0; i < this.coordinates.size(); i++) {
			System.out.println(this.coordinates.get(i).toString());
		}

	}

	/**************************/
	/****** CONSTRUCTOR ******/
	/*************************/

	public Ship(Coordinate startCoord, Coordinate endCoord) {

		this.coordinates = startCoord.listCoordinate(endCoord);
		this.hit = 0;
		this.size = this.coordinates.size();
	}

	/*******************************/
	/****** GETTERS & SETTERS ******/
	/******************************/

	public ArrayList<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(ArrayList<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	/*************************/
	/********* OTHER *********/
	/*************************/

	/***************************************************************/
	/**
	 * return true if the ship is hit by the target(and so registers the hit) ,
	 * false otherwise
	 **/
	public boolean isHit(Coordinate target) {

		boolean isHit = target.coordinateIn(this.coordinates);

		if (isHit) {
			this.hit = this.hit + 1; /* registers the hit */
		}
		return isHit;
	}

	/***************************************************************/
	/** return true if the ship is sunk, false otherwise **/

	public boolean isSunk() {
		return this.hit == this.size;
	}
}

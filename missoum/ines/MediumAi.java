package missoum.ines;

public class MediumAi extends AI {

	public MediumAi() {
		super("Level Medium");
	}

	public MediumAi(String iaName) {
		super(iaName);
	}

	/***************************************************************/
	/** Gives a random coordinate that had not already been shot **/

	public Coordinate giveATarget() {
		Coordinate coord;
		do {
			coord = super.giveACoordinate();

		} while (coord.coordinateIn(this.getHitShots()) | coord.coordinateIn(this.getMissedShots()));

		return coord;
	}

}

package missoum.ines;

public class BeginnerAi extends AI {

	/**************************/
	/****** CONSTRUCTOR ******/
	/*************************/

	public BeginnerAi() {
		super("Level Beginner");
	}

	public BeginnerAi(String iaName) {
		super(iaName);
	}

	/*************************/
	/********* OTHER *********/
	/*************************/

	public Coordinate giveATarget() {
		return this.giveACoordinate();
	}

}

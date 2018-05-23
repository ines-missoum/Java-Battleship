package missoum.ines;

import java.util.Scanner;

public class Battleship {

	static Scanner in = new Scanner(System.in);

	/***************************************************************/
	/** ask if the mode of game **/
	public static int askMode() {

		boolean incoorect = false;
		boolean number = false;
		String modeS;
		int mode = -1;

		System.out.println("2 : two players");
		System.out.println("1 : one player");
		System.out.print("Choose a game mode (2 or 1) : ");

		do {
			do {
				modeS = in.nextLine();
				System.out.println();
				try {
					mode = Integer.parseInt(modeS);
					number = true;
				} catch (NumberFormatException e) {
					number = false;
					System.out.print("I don't understand the answer, enter a NUMBER (0 or 1) :");
				}

			} while (number != true);

			incoorect = mode != 2 & mode != 1;
			if (incoorect) {
				System.out.print("I don't understand the answer, enter a NUMBER (0 or 1) :");
			}
		} while (incoorect);

		return mode;

	}

	public static void main(String[] args) {

		int mode = askMode();

		System.out.println();
		if (mode == 2) {
			System.out.println();
			System.out.println("_____ 2 PLAYERS MODE STARTING _____ ");
			System.out.println();
			BattlePlayer.main(null);
		} else if (mode == 1) {
			System.out.println();
			System.out.println("_____ 1 PLAYER MODE STARTING _____");
			System.out.println();
			BattlePlayerAI.main(null);

		}
		System.out.println();
		in.close();
	}

}

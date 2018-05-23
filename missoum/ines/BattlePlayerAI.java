package missoum.ines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BattlePlayerAI {

	static Scanner in = new Scanner(System.in);

	/***************************************************************/
	public static String askName() {

		String name;

		System.out.print("Player, enter your name : ");
		name = in.nextLine();
		System.out.println("Hello " + name + " ! :)");
		System.out.println("");

		return name;
	}

	/***************************************************************/
	/** Asks coordinates to place a ship **/
	/** verifies the format and if the place is free **/
	/** otherwise asks again **/
	public static Coordinate[] askRightCoordinates(Player p) {

		Coordinate startCoord, endCoord;
		String start, end;
		boolean coordOK;
		boolean freeSpace;
		Coordinate order;

		do {
			do {

				System.out.print(p.getPlayerName());

				System.out.print(" enter your start coordinate : ");
				start = in.nextLine();
				startCoord = new Coordinate(start);

				System.out.print("enter your end coordinate : ");
				end = in.nextLine();
				endCoord = new Coordinate(end);

				coordOK = startCoord.PairCoordinatesOK(endCoord);

				if (coordOK) {
					if (startCoord.biggerThan(endCoord)) {
						/* if the coordinates are not in order, we ordinate them */
						order = startCoord;
						startCoord = endCoord;
						endCoord = order;
					}

				} else {
					System.out.println();
					System.out.println("Coordinates entered not correct, try again.");
					System.out.println();
				}
			} while (!coordOK);

			freeSpace = p.canPlaceShip(startCoord, endCoord);

			if (!freeSpace) {
				System.out.println();
				System.out.println("Already a ship here, try again");
				System.out.println();
			}
		} while (!freeSpace);

		Coordinate[] rightCoord = { startCoord, endCoord };

		return rightCoord;

	}

	/***************************************************************/
	/** ask the coordinate of a shot **/
	/** and verifies that it's a correct format **/
	/** otherwise asks again **/
	public static Coordinate askTarget(Player p) {

		Coordinate targetCoord;
		String target;

		boolean coordOK;

		do {

			System.out.println();
			System.out.print(p.getPlayerName() + " announce your target : ");
			target = in.nextLine();
			targetCoord = new Coordinate(target);
			coordOK = targetCoord.coordinatesOK();

			if (!coordOK) {
				System.out.println();
				System.out.println("Coordinates entered not correct, try again.");
				System.out.println();
			}

		} while (!coordOK);

		return targetCoord;

	}

	/***************************************************************/
	/** Place all the ships of a player from the coordinates he enters **/
	static public void placeAllShips(Player p) {

		List<String> sizeOfShips = new ArrayList<String>(Arrays.asList(Game.sizeOfShips));
		/* sizes of ship to place */

		int size = 0;
		int nbShipPlaced = 0;

		/* we place the ship only if the player has a ship of this size to place */
		while (nbShipPlaced < Game.numberOfShips) {

			Coordinate[] rightCoord = askRightCoordinates(p);
			size = rightCoord[0].lengthCoordinate(rightCoord[1]);
			String sizeS = "" + size;

			if (sizeOfShips.contains(sizeS)) {
				p.placeShip(rightCoord[0], rightCoord[1]);
				p.seeShipsGrid(p);
				nbShipPlaced++;
				sizeOfShips.remove(sizeS);
				System.out.println("Ship placed ");
				System.out.println();
			} else {
				System.out.println();
				System.out.println("You don't have a ship of this size, try again");
				System.out.println();
			}
		}

	}

	/***************************************************************/
	/** print the shot result **/
	public static void printShotResult(boolean[] result) {

		if (result[1]) {
			System.out.println("Sunk ! ");
		} else if (result[0]) {
			System.out.println("Hit ! ");
		} else {
			System.out.println("Missed ! ");
		}
	}

	/***************************************************************/
	public static void printWinner(Player currentPlayer, Player player, Player ia) {

		if (currentPlayer == ia) {
			System.out.println(" CONGRATULATION YOU'RE THE WINNER !!!");
		} else if (currentPlayer == player) {
			System.out.println(" Sorry you lost, better luck next time :) ");
		}
		System.out.println();
		System.out.println(" GAME OVER. ");
	}

	/***************************************************************/
	/** ask the level of an IA **/
	public static int askLevel() {

		boolean incoorectLevel = false;
		boolean number = false;
		String levelIA;
		int level = -1;

		System.out.println();
		System.out.println("level 1 : Easy peasy lemon squeezy");
		System.out.println("level 2 : Medium");
		System.out.println("level 3 : Hardcore");
		System.out.println();
		System.out.print("________ Choose the level of the IA (1,2 or 3) : ");

		do {
			do {
				levelIA = in.nextLine();
				System.out.println();
				try {
					level = Integer.parseInt(levelIA);
					number = true;
				} catch (NumberFormatException e) {
					number = false;
					System.out.print("This level doesn't exist, enter a NUMBER (1,2 or 3) :");
				}

			} while (number != true);

			incoorectLevel = level != 1 & level != 2 & level != 3;
			if (incoorectLevel) {
				System.out.print("This level doesn't exist, enter a NUMBER (1,2 or 3) :");
			}
		} while (incoorectLevel);

		System.out.println();
		if (level == 1) {
			System.out.println("You chose level : " + levelIA + " (EASY)");
		} else if (level == 2) {
			System.out.println("You chose level : " + levelIA + " (MEDIUM)");
		} else {
			System.out.println("You chose level : " + levelIA + " (HARD)");
		}
		System.out.println();

		return level;
	}

	/***************************************************************/
	/** ask if the players want to play again **/
	public static int askPlayAgain() {

		boolean incoorect = false;
		boolean number = false;
		String playAgainS;
		int playAgain = -1;

		System.out.println();
		System.out.println("Do you want to play again ? (yes : 1, no : 0)");
		System.out.println();

		do {
			do {
				playAgainS = in.nextLine();
				System.out.println();
				try {
					playAgain = Integer.parseInt(playAgainS);
					number = true;
				} catch (NumberFormatException e) {
					number = false;
					System.out.print("I don't understand the answer, enter a NUMBER (0 or 1) :");
				}

			} while (number != true);

			incoorect = playAgain != 0 & playAgain != 1;
			if (incoorect) {
				System.out.print("I don't understand the answer, enter a NUMBER (0 or 1) :");
			}
		} while (incoorect);

		System.out.println();
		if (playAgain == 1) {
			System.out.println("OK, we play again !");
		} else {
			System.out.println("OK see you soon !");
		}
		System.out.println();

		return playAgain;
	}

	/*******************************************************/
	public static int changePlayerTurn(int playerTurn) {
		if (playerTurn == 1) {
			return 2;
		} else {
			return 1;
		}
	}

	/*******************************************
	 * BATTLE
	 ****************************************************/
	public static void main(String[] args) {

		/******** CREATION OF IA *********/
		AI ai;

		int playAgain = 1;
		int playerTurn = 1;

		int levelIA = askLevel();
		if (levelIA == 1) {
			ai = new BeginnerAi();
		} else if (levelIA == 2) {
			ai = new MediumAi();
		} else {
			ai = new HardAI();
		}

		/**** CREATION OF THE PLAYER ****/

		/* we ask the name */

		String name1;
		name1 = askName();

		/* Creation of the player */

		Player player = new Player(name1);

		while (playAgain == 1) {

			ai.reset();
			player.reset();

			Player currentPlayer = new Player("");
			Player otherPlayer = new Player("");
			Player changeCurrentPlayer = new Player("");

			if (playerTurn == 2) {

				currentPlayer = player;
				otherPlayer = ai;

			} else {

				currentPlayer = ai;
				otherPlayer = player;
			}

			/* placement of the ships */
			ai.placeAllShips();
			placeAllShips(player);

			Coordinate target;

			System.out.println(currentPlayer.getPlayerName() + " begins ");

			while (!currentPlayer.lost()) {

				if (currentPlayer.getPlayerName().equals(player.getPlayerName())) {
					System.out.println("Your ships : ");
					System.out.println();
					currentPlayer.seeShipsGrid(ai);
					System.out.println("Your previous shots : ");
					System.out.println();
					currentPlayer.seeShotsGrid();
					target = askTarget(player);
					printShotResult(player.tryToHit(target, ai));
					/* turn to ai */

				} else {

					target = ai.giveATarget();
					System.out.println();
					System.out.print(" IA shot on " + target.toString() + " and ");
					System.out.println();
					printShotResult(ai.tryToHit(target, player));
					/* turn to player */

				}

				changeCurrentPlayer = otherPlayer;
				otherPlayer = currentPlayer;
				currentPlayer = changeCurrentPlayer;

			}
			printWinner(currentPlayer, player, ai);
			playAgain = askPlayAgain();
			playerTurn = changePlayerTurn(playerTurn);
		}

		in.close();
	}

}

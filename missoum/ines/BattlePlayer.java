package missoum.ines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BattlePlayer {

	static Scanner in = new Scanner(System.in);

	/***************************************************************/
	public static String askName(int nb) {

		String name;

		System.out.print("Player " + nb + ", enter your name : ");
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
	/** currentPlayer=winner, otherPlayer=looser **/
	public static void printWinner(Player currentPlayer, Player otherPlayer) {

		System.out.println();
		System.out.println(" CONGRATULATION " + currentPlayer.getPlayerName() + " YOU'RE THE WINNER !!!");
		System.out.println(" Sorry " + otherPlayer.getPlayerName() + " better luck next time :) ");
		System.out.println();
		System.out.println(" GAME OVER. ");
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

		int playAgain = 1;
		int playerTurn = 1;

		/******** CREATION OF THE PLAYERS *********/

		/* we ask the names */

		String name1, name2;
		name1 = askName(1);
		name2 = askName(2);

		/* Creation of the players */

		Player player1 = new Player(name1);
		Player player2 = new Player(name2);
		Player currentPlayer = new Player("");
		Player otherPlayer = new Player("");

		/* the battle begins */

		while (playAgain == 1) {
			player1.reset();
			player2.reset();

			if (playerTurn == 2) {

				currentPlayer = player1;
				otherPlayer = player2;
				placeAllShips(otherPlayer);
				placeAllShips(currentPlayer);

			} else {

				currentPlayer = player2;
				otherPlayer = player1;
				placeAllShips(currentPlayer);
				placeAllShips(otherPlayer);
			}

			Player changeCurrentPlayer;
			Coordinate target;

			while (!otherPlayer.lost()) {

				/* c'est au tour de l'autre joueur */
				changeCurrentPlayer = otherPlayer;
				otherPlayer = currentPlayer;
				currentPlayer = changeCurrentPlayer;

				System.out.println();
				System.out.println(currentPlayer.getPlayerName() + " it's your turn :) ");
				System.out.println();

				System.out.println("Your ships : ");
				System.out.println();
				currentPlayer.seeShipsGrid(otherPlayer);
				System.out.println();
				System.out.println("Your previous shots : ");
				System.out.println();
				currentPlayer.seeShotsGrid();
				target = askTarget(currentPlayer);
				printShotResult(currentPlayer.tryToHit(target, otherPlayer));

			}

			printWinner(currentPlayer, otherPlayer);
			playAgain = askPlayAgain();
			playerTurn = changePlayerTurn(playerTurn);
		}
		in.close();

	}

}

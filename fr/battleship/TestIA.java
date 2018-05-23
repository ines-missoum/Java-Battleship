package fr.battleship;

import java.io.FileWriter;
import java.io.IOException;

import missoum.ines.*;

public class TestIA {
	/******************************************************************/
	public static int changePlayerTurn(int playerTurn) {
		if (playerTurn == 1) {
			return 2;
		} else {
			return 1;
		}
	}

	/**************************************************************************/
	public static int[] battleAI(int levelFirstIA, int levelSecondIA) {
		AI ia1, ia2;
		int ia1Wins = 0;
		int ia2Wins = 0;
		int playerTurn = 1;

		if (levelFirstIA == 1) {
			ia1 = new BeginnerAi();
		} else if (levelFirstIA == 2) {
			ia1 = new MediumAi();
		} else {
			ia1 = new HardAI();
		}

		if (levelSecondIA == 1) {
			ia2 = new BeginnerAi();
		} else if (levelSecondIA == 2) {
			ia2 = new MediumAi();
		} else {
			ia2 = new HardAI();
		}

		for (int i = 0; i < 100; i++) {

			ia1.reset();
			ia1.placeAllShips();
			ia2.reset();
			ia2.placeAllShips();

			/******** BATTLE *********/

			Coordinate target;
			AI currentIA;

			if (playerTurn == 2) {
				currentIA = ia2;
			} else {
				currentIA = ia1;
			}

			while (!currentIA.lost()) {

				if (currentIA == ia1) {

					target = ia1.giveATarget();
					ia1.tryToHit(target, ia2);

					/* turn to ia2 */
					currentIA = ia2;

				} else {

					target = ia2.giveATarget();
					ia2.tryToHit(target, ia1);

					/* turn to ia1 */
					currentIA = ia1;
				}

			}

			if (currentIA == ia1) {
				ia2Wins++;

			} else {
				ia1Wins++;

			}

			playerTurn = changePlayerTurn(playerTurn);

		}

		if (ia1Wins > ia2Wins) {
			System.out.println("_____________________________" + ia1.getPlayerName()
					+ " WON THE WAR _____________________________");
		} else if (ia1Wins < ia2Wins) {
			System.out.println("_____________________________ " + ia2.getPlayerName()
					+ " WON THE WAR _____________________________");

		} else {
			System.out.println("EQUALITY");
		}

		System.out.println();
		System.out.println("Indeed " + ia1.getPlayerName() + " won : " + ia1Wins + " battles and  "
				+ ia2.getPlayerName() + " won : " + ia2Wins + " battles");
		System.out.println();

		int[] scores = { ia1Wins, ia2Wins };
		return scores;

	}

	public static void main(String[] args) {

		System.out.println();
		System.out.println("_____________________________  THE WAR BEGIN  _____________________________ ");
		System.out.println();

		int[] scoreWar1, scoreWar2, scoreWar3;

		scoreWar1 = battleAI(1, 2);
		scoreWar2 = battleAI(1, 3);
		scoreWar3 = battleAI(2, 3);

		try {
			FileWriter file = new FileWriter("ai_proof.csv");
			file.append("AI Name; score; AI Name2; score2 \n");
			file.append("AI Level Beginner;" + scoreWar1[0] + "; Level Medium;" + scoreWar1[1] + "\n");
			file.append("AI Level Beginner;" + scoreWar2[0] + ";Level Hard;" + scoreWar2[1] + "\n");
			file.append("AI Medium;" + scoreWar3[0] + ";Level Hard;" + scoreWar3[1] + "\n");
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
